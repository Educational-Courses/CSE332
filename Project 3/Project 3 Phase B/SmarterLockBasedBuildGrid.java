/**
 * SmarterLockBasedBuildGrid uses Java Thread to build the grid which holds population 
 * in order to answer the query in O(1) time.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class SmarterLockBasedBuildGrid extends java.lang.Thread {
	public ValuesForParallelBuildGrid values;  // values that remain the same for all tasks, including census data, grid size and four corners
	public int lo, hi;  // begin of the sub-array (included), end of the sub-array (excluded)
	public int[][] grid;  // the grid which holds the population
	public Integer[][] gridLocks;  // the locks for grid
	
	/**
	 * Construct a SmarterLockBasedBuildGrid object with census data, grid size 
	 * and four corners to build the grid.
	 * 
	 * @param v an object holds values that remain the same for all tasks
	 * @param l begin of the sub-array (included)
	 * @param h end of the sub-array (excluded)
	 * @param g the grid
	 * @param the grid locks
	 */
	public SmarterLockBasedBuildGrid(ValuesForParallelBuildGrid v, int l, int h, int[][] g, Integer[][] gl) {
		values = v;
		lo = l;
		hi = h;
		grid = g;
		gridLocks = gl;
	}
	
	/**
	 * Build the grid in parallel.
	 */
	public void run() {
		float col_dis = (values.east - values.west) / values.gridX;  // interval of the column of the grids
		float row_dis = (values.north - values.south) / values.gridY;  // interval of the row of the grids
		int x, y;
		for(int i = lo; i < hi; i++) {
			x = (int) Math.floor((values.cGroup[i].longitude - values.west) / col_dis);
			y = (int) Math.floor((values.cGroup[i].latitude - values.south) / row_dis);
			if (x == values.gridX && y == values.gridY) {
				synchronized (gridLocks[x - 1][y - 1]) {
					grid[x - 1][y - 1] += values.cGroup[i].population;
				}
			} else if (x == values.gridX) {
				synchronized (gridLocks[x - 1][y]) {
					grid[x - 1][y] += values.cGroup[i].population;
				}
			} else if (y == values.gridY) {
				synchronized (gridLocks[x][y - 1]) {
					grid[x][y - 1] += values.cGroup[i].population;
				}
			} else {
				synchronized (gridLocks[x][y]) {
					grid[x][y] += values.cGroup[i].population;
				}
			}
		}
	}
}