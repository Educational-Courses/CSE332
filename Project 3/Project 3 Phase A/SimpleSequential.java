/**
 * Version 1 -- Sequentially process the data to find corners of the U.S. rectangle and 
 * to find the population in the certain grid to answer the query. 
 * 
 * @author Chun-Wei Chen
 * @version 03/05/12
 */
public class SimpleSequential implements CensusDataProcessor {
	CensusData cData; // a census data object
	int gridX; // number of columns
	int gridY; // number of rows
	float[] corners; // an array which contains four corners of the rectangle, west, south, east, north respectively
	
	/**
	 * Construct a SimpleSequential object with census data, number of rows/columns in order to answer the query.
	 * 
	 * @param cd a census data object
	 * @param x number of columns
	 * @param y number of rows
	 */
	public SimpleSequential(CensusData cd, int x, int y) {
		cData = cd;
		gridX = x;
		gridY = y;
		corners = findCorners(cd);
	}
	
	/**
	 * Find the four corners of the U.S. rectangle.
	 * 
	 * @param cd a census data object which contains 2010 U.S. census data
	 * @return a float array which contains four corners of the U.S. rectangle
	 */
	private float[] findCorners(CensusData cd) {
		float west = 181;
		float south = 91;
		float east = -181;
		float north = -91;
		for (int i = 0; i < cd.data_size; i++) {
			float lat = cd.data[i].latitude;
			float lon = cd.data[i].longitude;
			if (lat < south)
				south = lat;
			if (lat > north)
				north = lat;
			if (lon < west)
				west = lon;
			if (lon > east)
				east = lon;
		}
		return new float[]{west, south, east, north};
	}
	
	/**
	 * Return an array which contains the total population in the grid and the total population.
	 * 
	 * @param w western-most column of the grid
	 * @param s southern-most row of the grid
	 * @param e eastern-most column of the grid
	 * @param n northern-most row of the grid
	 * @return an array which contains the total population in the grid and the total population
	 * @throws IllegalArgumentException if w <= 0, s <= 0, e < w, e > gridX,n < s or n > gridY
	 */
	public int[] populationInGrid(int w, int s, int e, int n) {
		if (!isValidRectangle(w, s, e, n))
			throw new IllegalArgumentException("Invalid input");
		int[] result = new int[2];
		float col_dis = (corners[2] - corners[0]) / gridX; // interval of the column of the grids
		float row_dis = (corners[3] - corners[1]) / gridY; // interval of the row of the grids
		for (int i = 0; i < cData.data_size; i++) {
			float x = (cData.data[i].longitude - corners[0]) / col_dis + 1;
			float y = (cData.data[i].latitude - corners[1]) / row_dis + 1;
			if (isInGrid(x, y, w, s, e, n))
				result[0] += cData.data[i].population;
			result[1] += cData.data[i].population;
		}
		return result;
	}
	
	/**
	 * Return true if the query input is valid.
	 * 
	 * @param w western-most column of the grid
	 * @param s southern-most row of the grid
	 * @param e eastern-most column of the grid
	 * @param n northern-most row of the grid
	 * @return true if w > 0, s > 0, e >= w, e <= gridX, n >= s and n <= gridY
	 */
	public boolean isValidRectangle(int w, int s, int e, int n) {
		return (w > 0 && s > 0 && (e >= w && e <= gridX) && (n >= s && n <= gridY));
	}
	
	/**
	 * Return true if (x, y) is in the query grid.
	 * 
	 * @param x x position of the census-block group respect to number of columns
	 * @param y y position of the census-block group respect to number of rows
	 * @param w western-most column of the grid
	 * @param s southern-most row of the grid
	 * @param e eastern-most column of the grid
	 * @param n northern-most row of the grid
	 * @return true if (x, y) is in the query grid
	 */
	public boolean isInGrid(float x, float y, int w, int s, int e, int n) {
		return ((x >= w && x < e + 1 && y >= s && y < n + 1) || 
				(x == e + 1 && x == gridX + 1 && y >= s && y < n + 1) ||
				(y == n + 1 && y == gridY + 1 && x >= w && x < e + 1) || 
				(x == e + 1 && x == gridX + 1 && y == n + 1 && y == gridY + 1));
	}
}