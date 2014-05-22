import java.util.concurrent.*;
import java.util.*;

public class FindMin extends RecursiveTask<Integer>
{
	static final ForkJoinPool fjPool=new ForkJoinPool();
	int[] array;
	int lo,hi;
	
	static final int SEQUENTIAL_CUTOFF=1000;
	public FindMin(int[] arr,int l,int h)
	{
		array=arr;
		lo=l;
		hi=h;
	}
	
	public Integer compute()
	{
		if(hi-lo<=SEQUENTIAL_CUTOFF)
		{
			int min=Integer.MAX_VALUE;
			for(int i=lo;i<hi;i++) min=Math.min(min,array[i]);
			return min;
		}
		else
		{
			FindMin left=new FindMin(array,lo,(hi+lo)/2);
			FindMin right=new FindMin(array,(hi+lo)/2,hi);
			left.fork();
			int rightAns=right.compute();
			int leftAns=left.join();
			return Math.min(rightAns,leftAns);
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println("Creating the list of numbers...");
		Random r=new Random();
		int num=100000000;
		int[] a=new int[num];
		for(int i=0;i<a.length;i++) a[i]=r.nextInt(num);
		
		System.out.println("Finding the min...");
		int min=fjPool.invoke(new FindMin(a,0,a.length));
		System.out.println("The min is: "+min);
	}
}
