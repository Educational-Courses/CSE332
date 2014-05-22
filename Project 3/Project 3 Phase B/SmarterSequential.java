/**
 * Version 3 -- Sequentially process the data to find corners of the U.S. rectangle and 
 * to find the population in the certain grid to answer the query. Use a grid to hold the 
 * population in order to answer the query in O(1) time.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class SmarterSequential extends SimpleSequential {
	public int[][] grid;  // the grid which holds the population in order to answer the query in O(1) time
	private boolean isGridBuilt;  // boolean flag for SmarterSequential to check if the grid is built before the query
	
	/**
	 * Construct a SmarterSequential object with census data, number of rows/columns in order to answer the query.
	 * 
	 * @param cd a census data object
	 * @param x number of columns
	 * @param y number of rows
	 */
	public SmarterSequential(CensusData cd, int x, int y) {
		super(cd, x, y);
		isGridBuilt = false;
		grid = null;
	}
	
	/**
	 * Build the grid with population by processing the census data.
	 */
	public void buildGrid() {
		grid = buildGridFirstStep(cData);
		isGridBuilt = true;
	}
	
	/**
	 * Build the grid with population by processing the census data.
	 * 
	 * @param cd a census data object
	 * @return the grid with population
	 */
	private int[][] buildGridFirstStep(CensusData cd) {
		int[][] tempGrid = new int[gridX][gridY];
		float col_dis = (corners[2] - corners[0]) / gridX;  // interval of the column of the grids
		float row_dis = (corners[3] - corners[1]) / gridY;  // interval of the row of the grids
		int x, y;
		for(int i = 0; i < cd.data_size; i++) {
			x = (int) Math.floor((cd.data[i].longitude - corners[0]) / col_dis);
			y = (int) Math.floor((cd.data[i].latitude - corners[1]) / row_dis);
			if (x == gridX && y == gridY)
				tempGrid[x - 1][y - 1] += cd.data[i].population;
			else if (x == gridX)
				tempGrid[x - 1][y] += cd.data[i].population;
			else if (y == gridY)
				tempGrid[x][y - 1] += cd.data[i].population;
			else
				tempGrid[x][y] += cd.data[i].population;
		}
		buildGridSecondStep(tempGrid);
		return tempGrid;
	}
	
	/**
	 * Second step of building grid.
	 * 
	 * @param fstGrid grid after the first step
	 */
	public static void buildGridSecondStep(int[][] fstGrid) {
		for (int i = 1; i < fstGrid[0].length; i++)
			fstGrid[0][i] += fstGrid[0][i - 1];
		for (int j = 1; j < fstGrid.length; j++)
			fstGrid[j][0] += fstGrid[j - 1][0];
		for (int k = 1; k < fstGrid[0].length; k++) {
			for (int l = 1; l < fstGrid.length; l++)
				fstGrid[l][k] += fstGrid[l - 1][k] + fstGrid[l][k - 1] - fstGrid[l - 1][k - 1];
		}
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
		int tl, br, ll;  // top left, bottom left, lower left corner
		
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