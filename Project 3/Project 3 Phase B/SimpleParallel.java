import java.util.concurrent.ForkJoinPool;

/**
 * Version 2 -- Use ForkJoin process the data to find corners of the U.S. rectangle and 
 * to find the population in the certain grid to answer the query. 
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class SimpleParallel implements CensusDataProcessor {
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	public CensusData cData; // a census data object
	public int gridX; // number of columns
	public int gridY; // number of rows
	float[] corners; // an array which contains four corners of the rectangle, west, south, east, north respectively
	
	/**
	 * Construct a SimpleParallel object with census data, number of rows/columns in order to answer the query.
	 * 
	 * @param cd a census data object
	 * @param x number of columns
	 * @param y number of rows
	 */
	public SimpleParallel(CensusData cd, int x, int y) {
		cData = cd;
		gridX = x;
		gridY = y;
		corners = findCorners(cd);
	}
	
	/**
	 * Return an array which contains the total population in the grid and the total population.
	 * 
	 * @param w western-most column of the grid
	 * @param s southern-most row of the grid
	 * @param e eastern-most column of the grid
	 * @param n northern-most row of the grid
	 * @return an array which contains the total population in the grid and the total population
	 * @throws IllegalArgumentException if w <= 0, s <= 0, e < w, e > gridX,n < s or n > gridY
	 */
	public int[] populationInGrid(int w, int s, int e, int n) {
		if (!isValidRectangle(w, s, e, n))
			throw new IllegalArgumentException("Invalid input");
		int[] range = new int[]{w, s, e, n};
		SimpleParallelPopInGrid pig = new SimpleParallelPopInGrid(cData, corners, range, gridX, gridY, 0, cData.data_size);
		fjPool.invoke(pig);
		return pig.pop;
	}
	
	/**
	 * Return true if the query input is valid.
	 * 
	 * @param w western-most column of the grid
	 * @param s southern-most row of the grid
	 * @param e eastern-most column of the grid
	 * @param n northern-most row of the grid
	 * @return true if w > 0, s > 0, e >= w, e <= gridX, n >= s and n <= gridY
	 */
	protected boolean isValidRectangle(int w, int s, int e, int n) {
		return (w > 0 && s > 0 && (e >= w && e <= gridX) && (n >= s && n <= gridY));
	}
	
	/**
	 * Returns an array which contains four corners of the rectangle
	 * 
	 * @param cd a census data object
	 * @return a float array which contains four corners of the rectangle
	 */
	protected static float[] findCorners(CensusData cd) {
		SimpleParallelFindCorners fc = new SimpleParallelFindCorners(cd, 0, cd.data_size);
		fjPool.invoke(fc);
		return fc.corners;
	}
}