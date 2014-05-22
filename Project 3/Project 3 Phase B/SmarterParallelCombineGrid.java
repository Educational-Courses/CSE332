import java.util.concurrent.*;

/**
 * Use ForkJoin to combine two grids.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class SmarterParallelCombineGrid extends RecursiveAction {
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	public static final int SEQUENTIAL_CUTOFF = 25;
	int[][] leftG, rightG, result;  // left grid, right grid, the combined grid
	int xlo, xhi;  // beginning of sub-problem of column, end of sub-problem of column, 
	int ylo, yhi;  // beginning of sub-problem of row, end of sub-problem of row
	
	/**
	 * Construct a SmarterParallelCombineGrid object. 
	 * 
	 * @param lg left grid
	 * @param rg right grid
	 * @param xl beginning of sub-problem of column 
	 * @param xh end of sub-problem of column
	 * @param yl beginning of sub-problem of row
	 * @param yh end of sub-problem of row
	 * @param r result of combination of two grids
	 */
	public SmarterParallelCombineGrid(int[][] lg, int[][] rg, int xl, int xh, int yl, int yh, int[][] r){
		leftG = lg;
		rightG = rg;
		xlo = xl;
		xhi = xh;
		ylo = yl;
		yhi = yh;
		result = r;
	}
	
	/**
	 * Use ForkJoin to combine two grids.
	 */
	public void compute() {
		if (xhi - xlo < SEQUENTIAL_CUTOFF && yhi - ylo < SEQUENTIAL_CUTOFF) {
			for (int i = ylo; i < yhi; i++) {
				for (int j = xlo; j < xhi; j++)
					result[j][i] += leftG[j][i] + rightG[j][i];
			}
		} else {
			SmarterParallelCombineGrid lowerLeft = new SmarterParallelCombineGrid(leftG, rightG, xlo, (xlo + xhi) / 2, 
					ylo, (ylo + yhi) / 2, result);
			SmarterParallelCombineGrid lowerRight = new SmarterParallelCombineGrid(leftG, rightG, xlo, (xlo + xhi) / 2, 
					(ylo + yhi) / 2, yhi, result);
			SmarterParallelCombineGrid upperLeft = new SmarterParallelCombineGrid(leftG, rightG, (xlo + xhi) / 2, xhi, 
					ylo, (ylo + yhi) / 2, result);
			SmarterParallelCombineGrid upperRight = new SmarterParallelCombineGrid(leftG, rightG, (xlo + xhi) / 2, xhi, 
					(ylo + yhi) / 2, yhi, result);
			lowerLeft.fork();
			lowerRight.compute();
			upperLeft.compute();
			upperRight.compute();
			lowerLeft.join();
		}
	}
	
	/**
	 * Combine two grids.
	 * 
	 * @param lg left grid
	 * @param rg right grid
	 * @return result of combination of two grids.
	 */
	public static int[][] combine(int[][] lg, int[][] rg) {
		int[][] r = new int[lg.length][lg[0].length];
		SmarterParallelCombineGrid tasks = new SmarterParallelCombineGrid(lg, rg, 0, lg.length, 0, lg[0].length, r);
		fjPool.invoke(tasks);
		return tasks.result;
	}
}