import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * A user interaction class which processes the census data to find four corners of the rectangle, 
 * and then answer the population in the query rectangle based on the query input 
 * and its percentage of total population.
 * 
 * @author Chun-Wei Chen
 * @version 03/05/12
 */
public class PopulationQuery {
	// next four constants are relevant to parsing
	public static final int TOKENS_PER_LINE  = 7;
	public static final int POPULATION_INDEX = 4; // zero-based indices
	public static final int LATITUDE_INDEX   = 5;
	public static final int LONGITUDE_INDEX  = 6;
	
	// parse the input file into a large array held in a CensusData object
	@SuppressWarnings("resource")
	public static CensusData parse(String filename) {
		CensusData result = new CensusData();
		
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(filename));
            
            // Skip the first line of the file
            // After that each line has 7 comma-separated numbers (see constants above)
            // We want to skip the first 4, the 5th is the population (an int)
            // and the 6th and 7th are latitude and longitude (floats)
            // If the population is 0, then the line has latitude and longitude of +.,-.
            // which cannot be parsed as floats, so that's a special case
            //   (we could fix this, but noisy data is a fact of life, more fun
            //    to process the real data as provided by the government)
            
            String oneLine = fileIn.readLine(); // skip the first line

            // read each subsequent line and add relevant data to a big array
            while ((oneLine = fileIn.readLine()) != null) {
                String[] tokens = oneLine.split(",");
                if(tokens.length != TOKENS_PER_LINE)
                	throw new NumberFormatException();
                int population = Integer.parseInt(tokens[POPULATION_INDEX]);
                if(population != 0)
                	result.add(population,
                			   Float.parseFloat(tokens[LATITUDE_INDEX]),
                		       Float.parseFloat(tokens[LONGITUDE_INDEX]));
            }

            fileIn.close();
        } catch(IOException ioe) {
            System.err.println("Error opening/reading/writing input or output file.");
            System.exit(1);
        } catch(NumberFormatException nfe) {
            System.err.println(nfe.toString());
            System.err.println("Error in file format");
            System.exit(1);
        }
        return result;
	}

	// argument 1: file name for input data: pass this to parse
	// argument 2: number of x-dimension buckets
	// argument 3: number of y-dimension buckets
	// argument 4: -v1, -v2, -v3, -v4, or -v5
	public static void main(String[] args) {
		if (args.length != 4) {
			 System.err.println("Invalid number of arguments");
	         System.exit(1);
		}

		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		
		CensusData cd = parse(args[0]);
		CensusDataProcessor cdp = null;
		
		if (args[3].equals("-v1"))
			cdp = new SimpleSequential(cd, x, y);
		else if (args[3].equals("-v2"))
			cdp = new SimpleParallel(cd, x, y);
		else
			throw new UnsupportedOperationException();
		
		int[] coordinates = new int [4];
		int[] result = new int[2];
		Scanner reader = new Scanner(System.in);
		
		while (true) {
			System.out.println("Please give west, south, east, north coordinates of your query rectangle:");
			String input = reader.nextLine();
			String[] tokens = input.split(" ");
			if (tokens.length != 4) {
				reader.close();
				System.exit(1);
			}
			for (int i = 0; i < tokens.length; i++) {
				try {
					coordinates[i] = Integer.parseInt(tokens[i]);
				} catch(NumberFormatException e) {
					reader.close();
					System.exit(1);
				}
			}

			if (args[3].equals("-v1"))
				result = cdp.populationInGrid(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
			else if (args[3].equals("-v2"))
				result = cdp.populationInGrid(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
			
			System.out.println("population of rectangle: " + result[0]);
			System.out.format("percent of total population: %.2f", Math.round(10000.0 * result[0] / result[1]) / 100.0);
			System.out.println();
		}
	}
}