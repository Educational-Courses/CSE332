import java.util.concurrent.*;

/**
 * SimpleParallelFindCorners uses ForkJoin to find four corners of the rectangle.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class SimpleParallelFindCorners extends RecursiveAction {
	public static final int SEQUENTIAL_CUTOFF = 1500;
	public CensusData cData; // a census data object
	public int lo, hi; // beginning of the sub-array (included), end of the sub-array (excluded)
	public float[] corners; // an array which contains four corners of the rectangle, west, south, east, north respectively
	
	/**
	 * Construct a SimpleParallelFindCorners object with census data to find four corners of the rectangle.
	 * 
	 * @param cd a census data object
	 * @param l beginning of the sub-array (included)
	 * @param h end of the sub-array (excluded)
	 */
	public SimpleParallelFindCorners(CensusData cd, int l, int h) {
		cData = cd;
		lo = l;
		hi = h;
		corners = new float[4];
	}
	
	/**
	 * Use ForkJoin to find four corners of the rectangle.
	 */
	public void compute() {
		if (hi - lo < SEQUENTIAL_CUTOFF) {
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
			SimpleParallelFindCorners left = new SimpleParallelFindCorners(cData, lo, (lo + hi) / 2);
			SimpleParallelFindCorners right = new SimpleParallelFindCorners(cData, (lo + hi) / 2, hi);
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
}