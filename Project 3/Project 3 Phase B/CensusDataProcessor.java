/**
 * Interface for different version of implementation.
 * 
 * @author Chun-Wei Chen
 * @version 03/04/12
 */
public interface CensusDataProcessor {
	/**
	 * Return an array which contains the total population in the grid and the total population
	 * 
	 * @param w western-most column of the grid
	 * @param s southern-most row of the grid
	 * @param e eastern-most column of the grid
	 * @param n northern-most row of the grid
	 * @return an array which contains the total population in the grid and the total population
	 */
	public int[] populationInGrid(int w, int s, int e, int n);
}