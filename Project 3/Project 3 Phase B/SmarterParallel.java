/**
 * Version 4 -- Use ForkJoin process the data to find corners of the U.S. rectangle and 
 * to find the population in the certain grid to answer the query. Use a grid to hold the 
 * population in order to answer the query in O(1) time.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class SmarterParallel extends SimpleParallel {
	public int[][] grid;  // the grid which holds the population in order to answer the query in O(1) time
	protected boolean isGridBuilt;  // boolean flag for SmarterParallel to check if the grid is built before the query
	
	/**
	 * Construct a SmarterParallel object with census data, number of rows/columns in order to answer the query.
	 * 
	 * @param cd a census data object
	 * @param x number of columns
	 * @param y number of rows
	 */
	public SmarterParallel(CensusData cd, int x, int y) {
		super(cd, x, y);
		isGridBuilt = false;
		grid = null;
	}
	
	/**
	 * Build the grid with population by processing the census data.
	 */
	public void buildGrid() {
		SmarterParallelBuildGrid bg = new SmarterParallelBuildGrid(
				new ValuesForParallelBuildGrid(cData.data, gridX, gridY, corners[0], corners[1], corners[2], corners[3]), 0, cData.data_size);
		fjPool.invoke(bg);
		int[][] temp = bg.grid;
		SmarterSequential.buildGridSecondStep(temp);
		grid = temp;
		isGridBuilt = true;
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
		if (!isGridBuilt)
			buildGrid();
		if (!isValidRectangle(w, s, e, n))
			throw new IllegalArgumentException("Invalid input");
		int[] result = new int[2];
		int tl, br, ll; // top left, bottom left, lower left corner
		if (s - 2 < 0)
			tl = 0;
		else
			tl = grid[e - 1][s - 2];
		if (w - 2 < 0)
			br = 0;
		else
			br = grid[w - 2][n - 1];
		if (s - 2 < 0 || w - 2 < 0)
			ll = 0;
		else
			ll = grid[w - 2][s - 2];

		result[0] = grid[e - 1][n - 1] - tl - br + ll;
		result[1] = grid[gridX - 1][gridY - 1];
		return result;
	}
}