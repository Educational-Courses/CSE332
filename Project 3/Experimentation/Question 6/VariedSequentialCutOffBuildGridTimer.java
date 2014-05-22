import java.util.concurrent.ForkJoinPool;

/**
 * Compare the performance of build grid in parallel with different sequential cut-off.
 *  
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class VariedSequentialCutOffBuildGridTimer {
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	public static final CensusData cData = PopulationQuery.parse("CenPop2010.txt");
	public static final int gridX = 100;
	public static final int gridY = 500;
	
	/**
	 * Build the grid with population after first step of building grid for a SmarterParallel object. 
	 * This is only used for finding optimal cut-off for parallel build grid.
	 * 
	 * @param sp a SmarterParallel object
	 * @param c sequential cut-off
	 */
	private static void variedSequentialCutOffBuildGrid(SmarterParallel sp, int c) {
		ValuesForParallelBuildGrid values = new ValuesForParallelBuildGrid(sp.cData.data, sp.gridX, sp.gridY, 
				sp.corners[0], sp.corners[1], sp.corners[2], sp.corners[3]);
		ParallelBuildGridVariedSequentialCutOff tasks = new ParallelBuildGridVariedSequentialCutOff(values, 0, sp.cData.data_size, c);
		fjPool.invoke(tasks);
		sp.grid = tasks.grid;
		sp.isGridBuilt = true;
	}
	
	public static void main(String[] args) {
		long startTime, endTime;
		
		// warm up
		for (int i = 0; i < 5; i++) {
			SmarterParallel sp = new SmarterParallel(cData, 100, 500);
			startTime = System.currentTimeMillis();
			variedSequentialCutOffBuildGrid(sp, 50);
			endTime = System.currentTimeMillis();
			System.out.println("Parallel build grid with sequential cut-off = 50: " + (endTime - startTime));
		}
		
		for (int i = 2; i < 220000; i *= 2) {
			SmarterParallel sp = new SmarterParallel(cData, 100, 500);
			startTime = System.currentTimeMillis();
			variedSequentialCutOffBuildGrid(sp, i);
			endTime = System.currentTimeMillis();
			System.out.println("Parallel build grid with sequential cut-off = " + i + ": " + (endTime - startTime));
		}
		
		SmarterParallel sp = new SmarterParallel(cData, 100, 500);
		startTime = System.currentTimeMillis();
		variedSequentialCutOffBuildGrid(sp, 220000);
		endTime = System.currentTimeMillis();
		System.out.println("Parallel build grid with sequential cut-off = 220000: " + (endTime - startTime));
	}
}
