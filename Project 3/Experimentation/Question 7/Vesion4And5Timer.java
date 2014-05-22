/**
 * Compare the performance of version 4 to version 5 as the size of the grid changes.
 *  
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class Vesion4And5Timer {
	public static final CensusData cData = PopulationQuery.parse("CenPop2010.txt");
	
	public static void main(String[] args) {
		SmarterParallel sp;
		SmarterLockBased sl;
		long startTime, endTime;
		
		// warm up
		for (int i = 0; i < 5; i++) {
			startTime = System.currentTimeMillis();
			sp = new SmarterParallel(cData, 100, 100);
			sp.buildGrid();
			endTime = System.currentTimeMillis();
			System.out.println("Version 4 with grid size 100 x 100: " + (endTime - startTime));
			startTime = System.currentTimeMillis();
			sl = new SmarterLockBased(cData, 100, 100);
			sl.buildGrid();
			endTime = System.currentTimeMillis();
			System.out.println("Version 5 with grid size 100 x 100: " + (endTime - startTime));
		}
		
		for(int i = 1; i <= 2048; i *= 2) {
			startTime = System.currentTimeMillis();
			sp = new SmarterParallel(cData, i, i);
			sp.buildGrid();
			endTime = System.currentTimeMillis();
			System.out.println("Version 4 with grid size " + i + " x " + i + ": " + (endTime - startTime));
			startTime = System.currentTimeMillis();
			sl = new SmarterLockBased(cData, i, i);
			sl.buildGrid();
			endTime = System.currentTimeMillis();
			System.out.println("Version 5 with grid size " + i + " x " + i + ": " + (endTime - startTime));
		}
	}
}