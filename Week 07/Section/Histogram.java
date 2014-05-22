import java.util.concurrent.*;
import java.util.*;

public class Histogram extends RecursiveTask<int[]>
{
	static final ForkJoinPool fjPool=new ForkJoinPool();
	int[] array; //the input data; NOT the output data - that gets returned each time
	int lo,hi;
	static final int SEQUENTIAL_CUTOFF=1000;
	
	public Histogram(int[] arr,int l,int h)
	{ array=arr; lo=l; hi=h; }
	
	public int[] compute()
	{
		if(hi-lo<=SEQUENTIAL_CUTOFF)
		{
			int[] results=new int[10]; //these will initialize to 0
			for(int i=lo;i<hi;i++) results[array[i]/10]++;
			return results;
		}
		else
		{
			Histogram left=new Histogram(array,lo,(hi+lo)/2);
			Histogram right=new Histogram(array,(hi+lo)/2,hi);
			left.fork();
			int[] rightResults=right.compute();
			int[] leftResults=left.join();
			//here we'll merge by putting the final values in the leftResults array
			for(int i=0;i<leftResults.length;i++) leftResults[i]+=rightResults[i];
			return leftResults;
		}
	}
	public static void main(String[] args)
	{
		Random r=new Random();
		int num=10000000;
		int[] a=new int[num];
		for(int i=0;i<a.length;i++) a[i]=r.nextInt(100);
		int[] results=fjPool.invoke(new Histogram(a,0,num));
		
		System.out.println("Results:");
		for(int i=0;i<results.length;i++) System.out.println("In bin "+i+": "+results[i]);
	}
}
