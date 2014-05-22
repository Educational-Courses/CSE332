import java.util.concurrent.*;

/**
 * SimpleParallelPopInGrid uses ForkJoin to sum up the number of population in the grid and the total number of population.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class SimpleParallelPopInGrid extends RecursiveAction {
	public CensusData cData; // a census data object
	public float[] corners; // an array which contains four corners of the rectangle, west, south, east, north respectively
	public int gridX, gridY, lo, hi; // number of columns, number of rows, beginning of the sub-array (included), end of the sub-array (excluded)
	public int[] range; // an array which contains four value the user input
	public int[] pop; // an array which contains the total population in the grid and the total population
	
	/**
	 * Construct a SimpleParallelPopInGrid object with census data, range of the query grid, number of rows/columns in order to answer the query.
	 * 
	 * @param cd a census data object
	 * @param c an array contains four corners of the rectangle
	 * @param r range of the query grid
	 * @param x number of columns
	 * @param y number of rows
	 * @param l beginning of the sub-array (included)
	 * @param h end of the sub-array (excluded)
	 */
	public SimpleParallelPopInGrid(CensusData cd, float[] c, int[] r, int x, int y, int l, int h) {
		cData = cd;
		corners = c;
		range = r;
		gridX = x;
		gridY = y;
		lo = l;
		hi = h;
		pop = new int[2];
	}
	
	/**
	 * Return true if (x, y) is in the query grid.
	 * 
	 * @param x x position of the census-block group respect to number of columns
	 * @param y y position of the census-block group respect to number of rows
	 * @return true if (x, y) is in the query grid
	 */
	public boolean isInGrid(float x, float y) {
		return ((x >= range[0] && x < range[2] + 1 && y >= range[1] && y < range[3] + 1) || 
				(x == range[2] + 1 && x == gridX + 1 && y >= range[1] && y < range[3] + 1) ||
				(y == range[3] + 1 && y == gridY + 1 && x >= range[0] && x < range[2] + 1) || 
				(x == range[2] + 1 && x == gridX + 1 && y == range[3] + 1 && y == gridY + 1));
	}
	
	/**
	 * Use ForkJoin to sum up the number of population in the grid and the total number of population.
	 */
	public void compute() {
		if (hi - lo == 1) {
			float col_dis = (corners[2] - corners[0]) / gridX; // interval of the column of the grids
			float row_dis = (corners[3] - corners[1]) / gridY; // interval of the row of the grids
			float x = (cData.data[lo].longitude - corners[0]) / col_dis + 1;
			float y = (cData.data[lo].latitude - corners[1]) / row_dis + 1;
			if (isInGrid(x, y))
				pop[0] += cData.data[lo].population;
			pop[1] += cData.data[lo].population;
		} else {
			SimpleParallelPopInGrid left = new SimpleParallelPopInGrid(cData, corners, range, gridX, gridY, lo, (lo + hi) / 2);
			SimpleParallelPopInGrid right = new SimpleParallelPopInGrid(cData, corners, range, gridX, gridY, (lo + hi) / 2, hi);
			left.fork();
			right.compute();
			left.join();
			pop[0] = left.pop[0] + right.pop[0];
			pop[1] = left.pop[1] + right.pop[1];
		}
	}
}