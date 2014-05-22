import java.util.concurrent.*;

/**
 * SmarterParallelBuildGrid uses ForkJoin to build the grid which holds population 
 * in order to answer the query in O(1) time.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class SmarterParallelBuildGrid extends RecursiveAction {
	public static final int SEQUENTIAL_CUTOFF = 57500;
	public ValuesForParallelBuildGrid values;  // values that remain the same for all tasks, including census data, grid size and four corners
	public int lo, hi;  // beginning of the sub-array (included), end of the sub-array (excluded)
	public int[][] grid;  // the grid which holds the population
	
	/**
	 * Construct a SmarterParallelBuildGrid object with census data, grid size 
	 * and four corners to build the grid.
	 * 
	 * @param v an object holds values that remain the same for all tasks
	 * @param l beginning of the sub-array (included)
	 * @param h end of the sub-array (excluded)
	 */
	public SmarterParallelBuildGrid(ValuesForParallelBuildGrid v, int l, int h) {
		values = v;
		lo = l;
		hi = h;
		grid = new int[v.gridX][v.gridY];
	}
	
	/**
	 * Use ForkJoin to build the grid.
	 */
	public void compute() {
		if (hi - lo < SEQUENTIAL_CUTOFF) {
			float col_dis = (values.east - values.west) / values.gridX; // interval of the column of the grids
			float row_dis = (values.north - values.south) / values.gridY; // interval of the row of the grids
			int x, y;
			for(int i = lo; i < hi; i++) {
				x = (int) Math.floor((values.cGroup[i].longitude - values.west) / col_dis);
				y = (int) Math.floor((values.cGroup[i].latitude - values.south) / row_dis);
				if (x == values.gridX && y == values.gridY)
					grid[x - 1][y - 1] += values.cGroup[i].population;
				else if (x == values.gridX)
					grid[x - 1][y] += values.cGroup[i].population;
				else if (y == values.gridY)
					grid[x][y - 1] += values.cGroup[i].population;
				else
					grid[x][y] += values.cGroup[i].population;
			}
		} else {
			SmarterParallelBuildGrid left = new SmarterParallelBuildGrid(values, lo, (lo + hi) / 2);
			SmarterParallelBuildGrid right = new SmarterParallelBuildGrid(values, (lo + hi) / 2, hi);
			left.fork();
			right.compute();
			left.join();
			grid = SmarterParallelCombineGrid.combine(left.grid, right.grid);
		}
	}
}