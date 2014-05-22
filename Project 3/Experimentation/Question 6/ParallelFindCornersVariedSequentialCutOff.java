import java.util.concurrent.*;

/**
 * Use the same code as SimpleParallelFindCorners but make sequential cut-off varied.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class ParallelFindCornersVariedSequentialCutOff extends RecursiveAction {
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	public int cutOff; // sequential cut-off
	public CensusData cData; // a census data object
	public int lo, hi; // beginning of the sub-array (included), end of the sub-array (excluded)
	public float[] corners; // an array which contains four corners of the rectangle, west, south, east, north respectively
	
	/**
	 * Construct a SimpleParallelFindCorners object with census data to find four corners of the rectangle.
	 * 
	 * @param cd a census data object
	 * @param l beginning of the sub-array (included)
	 * @param h end of the sub-array (excluded)
	 * @param c sequential cut-off
	 */
	public ParallelFindCornersVariedSequentialCutOff(CensusData cd, int l, int h, int c) {
		cData = cd;
		lo = l;
		hi = h;
		cutOff = c;
		corners = new float[4];
	}
	
	/**
	 * Use ForkJoin to find four corners of the rectangle.
	 */
	public void compute() {
		if (hi - lo < cutOff) {
			float west = cData.data[lo].longitude;
			float south = cData.data[lo].latitude;
			float east = west;
			float north = south;
			for (int i = lo + 1; i < hi; i++) {
				if (cData.data[i].longitude < west)
					west = cData.data[i].longitude;
				if (cData.data[i].longitude > east)
					east = cData.data[i].longitude;
				if (cData.data[i].latitude < south)
					south = cData.data[i].latitude;
				if (cData.data[i].latitude > north)
					north = cData.data[i].latitude;
			}
			corners = new float[]{west, south, east, north};
		} else {
			ParallelFindCornersVariedSequentialCutOff left = new ParallelFindCornersVariedSequentialCutOff(cData, lo, (lo + hi) / 2, cutOff);
			ParallelFindCornersVariedSequentialCutOff right = new ParallelFindCornersVariedSequentialCutOff(cData, (lo + hi) / 2, hi, cutOff);
			left.fork();
			right.compute();
			left.join();
			for(int i = 0; i < 4; i++) {
				if (i < 2)
					corners[i] = Math.min(left.corners[i], right.corners[i]);
				else
					corners[i] = Math.max(left.corners[i], right.corners[i]);
			}
		}
	}
	
	/**
	 * Returns an array which contains four corners of the rectangle
	 * 
	 * @param cd a census data object
	 * @param c sequential cut-off
	 * @return a float array which contains four corners of the rectangle
	 */
	public static float[] variedSequentialCutOffFindCorners(CensusData cd, int c) {
		ParallelFindCornersVariedSequentialCutOff fc = new ParallelFindCornersVariedSequentialCutOff(cd, 0, cd.data_size, c);
		fjPool.invoke(fc);
		return fc.corners;
	}
}