import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for version 4 -- SmarterParallel.
 * Use the same census data as JUnit test for version 1 (TestSimpleSequential) 
 * to make sure they get the same result for the same tests.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/13
 */
public class TestSmarterParallel {
	private static final int TIMEOUT = 2000;
	public SmarterParallel sp;
	
	@Before
	public void setUp() throws Exception {
		CensusData cd = new CensusData();
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++)
				cd.add(i + j, i, j);
		}
		sp = new SmarterParallel(cd, 5, 5);
		sp.buildGrid();
	}

	@Test(timeout = TIMEOUT)
	public void testFindCorners() {
		float[] expectedCorners = new float[]{1, 1, 10, 10};
		expectedCorners[1] = TestSimpleSequential.mercatorConversion(expectedCorners[1]);
		expectedCorners[3] = TestSimpleSequential.mercatorConversion(expectedCorners[3]);
		String expected = TestSimpleSequential.cornersArrayToString(expectedCorners);
		String actual = TestSimpleSequential.cornersArrayToString(sp.corners);
		assertEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testBuildGrid() {
		int[][] expected = new int[][]{{12, 32, 60, 96, 140}, {32, 80, 144, 224, 320}, 
				{60, 144, 252, 384, 540}, {96, 224, 384, 576, 800}, {140, 320, 540, 800, 1100}};
		assertArrayEquals(expected, sp.grid);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridWithEntireGrid() {
		int[] expected = new int[]{1100,1100};
		int[] actual = sp.populationInGrid(1, 1, 5, 5);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForSingleGrid1() {
		int[] expected = new int[]{12, 1100};
		int[] actual = sp.populationInGrid(1, 1, 1, 1);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForSingleGrid2() {
		int[] expected = new int[]{44, 1100};
		int[] actual = sp.populationInGrid(3, 3, 3, 3);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForOneRow() {
		int[] expected = new int[]{180, 1100};
		int[] actual = sp.populationInGrid(2, 1, 2, 5);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForOneColumn() {
		int[] expected = new int[]{260, 1100};
		int[] actual = sp.populationInGrid(1, 4, 5, 4);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
	public void testPopulationInGridWithInvliadInput() {
		int[] expected = new int[]{260, 1100};
		int[] actual = sp.populationInGrid(1, 4, 5, 6);
		assertArrayEquals(expected, actual);
	}
}