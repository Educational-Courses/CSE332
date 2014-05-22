/*
 * A simple class showing how to use arguments to the program, and used to show how to create a run configuration.
 */

public class Runner
{
	//when your java class file is run - either through Eclipse's run configuration, or 'java Runner' - it just calls main()
	public static void main(String[] args)
	{
		System.out.println("Here are the arguments supplied to this program:");
		for(int i=0;i<args.length;i++) System.out.println("Argument "+i+": "+args[i]);
		if(args.length==0) System.out.println("No arguments :(");
	}
}
