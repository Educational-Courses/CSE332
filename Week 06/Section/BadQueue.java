import java.util.NoSuchElementException;

//this queue uses an unsorted array to store values, and is in general quite slow; O(n) operations
public class BadQueue implements BasicQueue
{
	private int[] arr;
	private int size;
	private static final int INITIAL_CAPACITY=8;
	
	public BadQueue()
	{
		arr=new int[INITIAL_CAPACITY];
		size=0;
	}
	
	//doubles the size of the array and copies over
	private void expand()
	{
		int[] narr=new int[arr.length*2];
		for(int i=0;i<size;i++) narr[i]=arr[i];
		arr=narr;
	}
	
	//removes the element from the list by shifting everything higher than it over 1, and decrementing size
	private void remove(int index)
	{
		if(index<0 || index>=size) return;
		for(;index<size-1;index++) arr[index]=arr[index+1];
		size--;
	}
	
	//just place at end (may have to expand)
	public void enqueue(int v)
	{
		if(size==arr.length) expand();
		arr[size]=v;
		size++;
	}
	
	public int dequeue()
	{
		if(isEmpty()) throw new NoSuchElementException();
		int value=arr[0];
		remove(0);
		return value;
	}
	
	public boolean isEmpty()
	{ return size==0; 	}
}
