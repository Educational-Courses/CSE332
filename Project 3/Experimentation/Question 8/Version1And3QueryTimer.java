import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Compare the performance of version 1 to version 3 as the number of queries changes.
 * 
 * @author Chun-Wei Chen
 * @version 03/12/12
 */
public class Version1And3QueryTimer {
	public static final CensusData cData = PopulationQuery.parse("CenPop2010.txt");
	
	public static void main(String[] args) {
		long startTime, endTime;
		SimpleSequential v1 = new SimpleSequential(cData, 100, 500);
		SmarterSequential v3 = new SmarterSequential(cData, 100, 500);
		
		// warm up
		for (int i = 0; i < 5; i++)
			v1.populationInGrid(1, 1, 100, 500);
		
		int[] coordinates = new int [4];
		int[] result = new int[2];
		int count = 1;
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new File("input.txt"));
		} catch (FileNotFoundException e1) {
			System.exit(1);
		}
		
		while (scanner.hasNextLine()) {
			System.out.println("Please give west, south, east, north coordinates of your query rectangle:");
			String input = scanner.nextLine();
			String[] tokens = input.split(" ");
			if (tokens.length != 4) {
				scanner.close();
				System.exit(1);
			}
			for (int i = 0; i < tokens.length; i++) {
				try {
					coordinates[i] = Integer.parseInt(tokens[i]);
				} catch(NumberFormatException e) {
					scanner.close();
					System.exit(1);
				}
			}
			
			startTime = System.currentTimeMillis();
			try {
				result = v1.populationInGrid(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
			} catch(IllegalArgumentException e) {
				scanner.close();
				System.exit(1);
			}
			endTime = System.currentTimeMillis();
			System.out.println("Version 1, " + count + " time: " + (endTime - startTime));
			
			System.out.println("population of rectangle: " + result[0]);
			System.out.format("percent of total population: %.2f", Math.round(10000.0 * result[0] / result[1]) / 100.0);
			System.out.println();
			
			startTime = System.currentTimeMillis();
			try {
				result = v3.populationInGrid(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
			} catch(IllegalArgumentException e) {
				scanner.close();
				System.exit(1);
			}
			endTime = System.currentTimeMillis();
			System.out.println("Version 3, " + count + " time: " + (endTime - startTime));
			
			System.out.println("population of rectangle: " + result[0]);
			System.out.format("percent of total population: %.2f", Math.round(10000.0 * result[0] / result[1]) / 100.0);
			System.out.println();
			
			count++;
		}
	}
}