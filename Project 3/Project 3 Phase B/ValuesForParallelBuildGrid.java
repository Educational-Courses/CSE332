/**
 * An object to hold those unchanging arguments when building grid.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class ValuesForParallelBuildGrid {
	public CensusGroup[] cGroup;  // input array
	public int gridX, gridY;  // number of columns/rows of the grid
	public float west, south, east, north;  // four corners
	
	/**
	 * Construct an object which holds the values SmarterParallelBuildGrid needs.
	 * 
	 * @param cg input array
	 * @param x number of columns of the grid
	 * @param y number of rows of the grid
	 * @param w western-most column of the grid
	 * @param s southern-most row of the grid
	 * @param e eastern-most column of the grid
	 * @param n northern-most row of the grid
	 */
	public ValuesForParallelBuildGrid(CensusGroup[] cg, int x, int y, float w, float s, float e, float n) {
		cGroup = cg;
		gridX = x;
		gridY = y;
		west = w;
		south = s;
		east = e;
		north = n;
	}
}