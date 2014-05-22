/**
 * Version 5 -- Use ForkJoin process the data to find corners of the U.S. rectangle and 
 * to find the population in the certain grid to answer the query. Use a grid to hold the 
 * population in order to answer the query in O(1) time. Update the data in the grid with lock 
 * to protect the data, so that only one thread can update the data of the census-block-group 
 * at a time if two or more threads trying to update the same census-block-group.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class SmarterLockBased extends SmarterParallel {
	
	/**
	 * Construct a SmarterLockBased object with census data, number of rows/columns in order to answer the query.
	 * 
	 * @param cd a census data object
	 * @param x number of columns
	 * @param y number of rows
	 */
	public SmarterLockBased(CensusData cd, int x, int y) {
		super(cd, x, y);
	}
	
	/**
	 * Build the grid with population by processing the census data.
	 * 
	 * @throws RuntimeException if it detects an InterruptedException when joining the threads
	 */
	public void buildGrid() {
		// create an object holds values that remain the same for all tasks, including census data, grid size and four corners
		ValuesForParallelBuildGrid values = new ValuesForParallelBuildGrid(cData.data, gridX, gridY, corners[0], corners[1], corners[2], corners[3]);
		int[][] tempGrid = new int[gridX][gridY];  // the grid to hold the population in the census-block-group
		Integer[][] gridLocks = new Integer[gridX][gridY];  // locks for each census-block-group
		
		// assign the value to the locks in order to be synchronized
		for (int i = 0; i < gridLocks.length; i++) {
			for (int j = 0; j < gridLocks[0].length; j++)
				gridLocks[i][j] = 0;
		}
		
		final int num_threads = 4;  // number of threads
		SmarterLockBasedBuildGrid tasks[] = new SmarterLockBasedBuildGrid[num_threads];
		
		// break the buildGrid task into 4 pieces
		for (int i = 0; i < num_threads - 1; i++) {
            tasks[i] = new SmarterLockBasedBuildGrid(values, i * (cData.data_size /  num_threads),  
            		(i + 1) * (cData.data_size / num_threads), tempGrid, gridLocks);
            tasks[i].start();
        }
		
        tasks[num_threads - 1] = new SmarterLockBasedBuildGrid(values, (num_threads - 1) * (cData.data_size / num_threads),  
        		cData.data_size, tempGrid, gridLocks);
        tasks[num_threads-1].start();
        
        // join 4 threads
        for (int i = 0; i < num_threads; i++) {
            try {
				tasks[i].join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
        }
        
		SmarterSequential.buildGridSecondStep(tempGrid);  // call the method to modify the grid
		grid = tempGrid;
		isGridBuilt = true;
	}
}