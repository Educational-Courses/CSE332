import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for version 1 -- SimpleSequential
 * 
 * @author Chun-Wei Chen
 * @version 03/05/13
 */
public class TestSimpleSequential {
	private static final int TIMEOUT = 2000;
	public SimpleSequential ss;
	
	@Before
	public void setUp() throws Exception {
		CensusData cd = new CensusData();
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++)
				cd.add(i + j, i, j);
		}
		ss = new SimpleSequential(cd, 5, 5);
	}

	@Test(timeout = TIMEOUT)
	public void testFindCorners() {
		float[] expectedCorners = new float[]{1, 1, 10, 10};
		expectedCorners[1] = mercatorConversion(expectedCorners[1]);
		expectedCorners[3] = mercatorConversion(expectedCorners[3]);
		String expected = cornersArrayToString(expectedCorners);
		String actual = cornersArrayToString(ss.corners);
		assertEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridWithEntireGrid() {
		int[] expected = new int[]{1100,1100};
		int[] actual = ss.populationInGrid(1, 1, 5, 5);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForSingleGrid1() {
		int[] expected = new int[]{12, 1100};
		int[] actual = ss.populationInGrid(1, 1, 1, 1);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForSingleGrid2() {
		int[] expected = new int[]{44, 1100};
		int[] actual = ss.populationInGrid(3, 3, 3, 3);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForOneRow() {
		int[] expected = new int[]{180, 1100};
		int[] actual = ss.populationInGrid(2, 1, 2, 5);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForOneColumn() {
		int[] expected = new int[]{260, 1100};
		int[] actual = ss.populationInGrid(1, 4, 5, 4);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
	public void testPopulationInGridWithInvliadInput() {
		int[] expected = new int[]{260, 1100};
		int[] actual = ss.populationInGrid(1, 4, 5, 6);
		assertArrayEquals(expected, actual);
	}
	
	public static String cornersArrayToString(float[] fa) {
		String result = "(";
		for (int i = 0; i < fa.length - 1; i++)
			result += fa[i] + ", ";
		result += fa[fa.length - 1] + ")";
		return result;
	}
	
	public static float mercatorConversion(float lat) {
		float latpi = (float)(lat * Math.PI / 180);
		float x = (float)Math.log(Math.tan(latpi) + 1 / Math.cos(latpi));
		return x;
	}
}