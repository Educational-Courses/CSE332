/**
 * Compare the performance of find corners in parallel with different sequential cut-off.
 *  
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class VariedSequentialCutOffFindCornersTimer {
	public static final CensusData cData = PopulationQuery.parse("CenPop2010.txt");
	
	public static void main(String[] args) {
		long startTime, endTime;
		float[] corners;
		
		// warm up
		for (int i = 0; i < 5; i++) {
			startTime = System.currentTimeMillis();
			corners = ParallelFindCornersVariedSequentialCutOff.variedSequentialCutOffFindCorners(cData, 50);
			endTime = System.currentTimeMillis();
			System.out.println("Parallel find corners with sequential cut-off = 50: " + (endTime - startTime));
		}
		
		for (int i = 2; i < 220000; i *= 2) {
			startTime = System.currentTimeMillis();
			corners = ParallelFindCornersVariedSequentialCutOff.variedSequentialCutOffFindCorners(cData, i);
			endTime = System.currentTimeMillis();
			System.out.println("Parallel find corners with sequential cut-off = " + i + ": " + (endTime - startTime));
			// print out the corners to make sure all of them get the same results 
			// since the results I get are kind of different than I expected.
			System.out.println(corners[0] + "," + corners[1] + "," + corners[2] + "," + corners[3]);
			
		}
		
		startTime = System.currentTimeMillis();
		corners = ParallelFindCornersVariedSequentialCutOff.variedSequentialCutOffFindCorners(cData, 220000);
		endTime = System.currentTimeMillis();
		System.out.println("Parallel find corners with sequential cut-off = 220000: " + (endTime - startTime));
		System.out.println(corners[0] + "," + corners[1] + "," + corners[2] + "," + corners[3]);
	}
}