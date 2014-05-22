import java.util.concurrent.*;
import java.util.*;

public class FindMinAlternative extends RecursiveAction
{
	static final ForkJoinPool fjPool=new ForkJoinPool();
	int[] array;
	int lo,hi;
	int min;
	static final int SEQUENTIAL_CUTOFF=1000;
	public FindMinAlternative(int[] arr,int l,int h)
	{
		array=arr;
		lo=l;
		hi=h;
		min=Integer.MAX_VALUE;
	}
	
	public void compute()
	{
		if(hi-lo<=SEQUENTIAL_CUTOFF)
		{
			min=Integer.MAX_VALUE;
			for(int i=lo;i<hi;i++) min=Math.min(min,array[i]);
		}
		else
		{
			FindMinAlternative left=new FindMinAlternative(array,lo,(hi+lo)/2);
			FindMinAlternative right=new FindMinAlternative(array,(hi+lo)/2,hi);
			left.fork();
			right.compute();
			left.join();
			min=Math.min(left.min,right.min);
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println("Creating the list of numbers...");
		Random r=new Random();
		int num=100000000;
		int[] a=new int[num];
		for(int i=0;i<a.length;i++) a[i]=r.nextInt(num);
		long start,end;
		
		//  note: in reality this library takes awhile to 'warm up', so a more accurate measurement would 
		//	involve running the test a number of times, tossing out the first few, and averaging the later ones
		start=System.currentTimeMillis();
		FindMinAlternative fma=new FindMinAlternative(a,0,a.length);
		fjPool.invoke(fma);
		int min=fma.min;
		end=System.currentTimeMillis();
		System.out.println("The min is: "+min);
		System.out.println("Took "+(end-start)+" ms in parallel");
	}
}
