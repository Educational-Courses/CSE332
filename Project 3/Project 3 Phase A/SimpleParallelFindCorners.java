import java.util.concurrent.*;

/**
 * SimpleParallelFindCorners uses ForkJoin to find four corners of the rectangle.
 * 
 * @author Chun-Wei Chen
 * @version 03/05/12
 */
public class SimpleParallelFindCorners extends RecursiveAction {
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	public CensusData cData; // a census data object
	public int lo, hi; // begin of the sub-array (included), end of the sub-array (excluded)
	public float[] corners; // an array which contains four corners of the rectangle, west, south, east, north respectively
	
	/**
	 * Construct a SimpleParallelFindCorners object with census data to find four corners of the rectangle.
	 * 
	 * @param cd a census data object
	 * @param l begin of the sub-array (included)
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
		if (hi - lo == 1) {
			float lat = cData.data[lo].latitude;
			float lon = cData.data[lo].longitude;
			corners = new float[]{lon, lat, lon, lat};
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
	
	/**
	 * Returns an array which contains four corners of the rectangle
	 * 
	 * @param cd a census data object
	 * @return a float array which contains four corners of the rectangle
	 */
	public static float[] findCorners(CensusData cd) {
		SimpleParallelFindCorners fc = new SimpleParallelFindCorners(cd, 0, cd.data_size);
		fjPool.invoke(fc);
		return fc.corners;
	}
}