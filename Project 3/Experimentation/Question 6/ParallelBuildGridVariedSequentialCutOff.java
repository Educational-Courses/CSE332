import java.util.concurrent.*;

/**
 * Use the same code as SmarterParallelBuildGrid but make sequential cut-off varied.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class ParallelBuildGridVariedSequentialCutOff extends RecursiveAction {
	public int cutOff; // sequential cut-off
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
	 * @param c sequential cut-off
	 */
	public ParallelBuildGridVariedSequentialCutOff(ValuesForParallelBuildGrid v, int l, int h, int c) {
		values = v;
		lo = l;
		hi = h;
		cutOff = c;
		grid = new int[v.gridX][v.gridY];
	}
	
	/**
	 * Use ForkJoin to build the grid.
	 */
	public void compute() {
		if (hi - lo < cutOff) {
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
			ParallelBuildGridVariedSequentialCutOff left = new ParallelBuildGridVariedSequentialCutOff(values, lo, (lo + hi) / 2, cutOff);
			ParallelBuildGridVariedSequentialCutOff right = new ParallelBuildGridVariedSequentialCutOff(values, (lo + hi) / 2, hi, cutOff);
			left.fork();
			right.compute();
			left.join();
			for (int i = 0; i < values.gridY; i++) {
				for (int j = 0; j < values.gridX; j++)
					grid[j][i] += left.grid[j][i] + right.grid[j][i];
			}
		}
	}
}