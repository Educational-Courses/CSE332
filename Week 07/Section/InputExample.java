import java.io.*;
public class InputExample
{
	public static void main(String[] args)
	{
		System.out.println("Enter 4 numbers, separated by spaces");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); //sets us up to take input from the user
		try
		{
			//we're going to loop, reading a line of input each time until the user is done
			String s=br.readLine();
			while(!s.equals("")) //keep going until they enter a blank line
			{
				String[] parts=s.split(" "); //'splits' the string into parts, where each part is separated by a space
				if(parts.length!=4)
				{
					System.err.println("Wrong number of parts!");
					break;
				}
				System.out.println("First: "+parts[0]+", second: "+parts[1]+", third: "+parts[2]+" & fourth: "+parts[3]);
				s=br.readLine(); //get the next string
			}
		}
		catch(Exception e)
		{
			System.err.println("An exception has occurred while handling input: "+e.getMessage());
		}
		finally //a 'finally' block will always run after try or catch finishes, even if they try to 'return' or throw an exception
		{
			//make sure that the BufferedReader gets closed, no matter how we left the previous block
			//technically, it's not a big deal for this BufferedReader, but for many readers/writers, it's important to make sure they are closed
			try
			{
				br.close();
			}
			catch(Exception e)
			{
				System.err.println("Error closing BufferedReader: "+e.getMessage());
			}
		}
	}
}
