import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Another JUnit test for version 5 -- SmarterLockBased. 
 * This uses different census data from TestSimpleParallel.
 * Use the same census data as JUnit test for version 1 (TestSimpleSequential2) 
 * to make sure they get the same result for the same tests.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/13
 */
public class TestSmarterLockBased2 {
	private static final int TIMEOUT = 2000;
	public SmarterLockBased sl;
		
	@Before
	public void setUp() throws Exception {
		CensusData cd = new CensusData();
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				if ((i + j) % 2 == 0 && i >= 3 && j >= 3)
					cd.add(i + j, i, j);
				else
					cd.add(0, i, j);
			}
		}
		sl = new SmarterLockBased(cd, 18, 18);
		sl.buildGrid();
	}

	@Test(timeout = TIMEOUT)
	public void testFindCorners() {
		float[] expectedCorners = new float[]{1, 1, 9, 9};
		expectedCorners[1] = TestSimpleSequential.mercatorConversion(expectedCorners[1]);
		expectedCorners[3] = TestSimpleSequential.mercatorConversion(expectedCorners[3]);
		String expected = TestSimpleSequential.cornersArrayToString(expectedCorners);
		String actual = TestSimpleSequential.cornersArrayToString(sl.corners);
		assertEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridWithEntireGrid() {
		int[] expected = new int[]{300,300};
		int[] actual = sl.populationInGrid(1, 1, 18, 18);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForSomeGrid() {
		int[] expected = new int[]{6,300};
		int[] actual = sl.populationInGrid(1, 1, 8, 6);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForSingleGrid1() {
		int[] expected = new int[]{6,300};
		int[] actual = sl.populationInGrid(5, 5, 5, 5);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForSingleGrid2() {
		int[] expected = new int[]{0,300};
		int[] actual = sl.populationInGrid(1, 1, 1, 1);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForOneColumn() {
		int[] expected = new int[]{52,300};
		int[] actual = sl.populationInGrid(14, 1, 14, 18);
		assertArrayEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testPopulationInGridForOneRow() {
		int[] expected = new int[]{0,300};
		int[] actual = sl.populationInGrid(1, 4, 18, 4);
		assertArrayEquals(expected, actual);
	}
}