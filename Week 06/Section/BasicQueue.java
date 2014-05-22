import java.util.NoSuchElementException;

public interface BasicQueue
{
	public boolean isEmpty();
	public void enqueue(int v);
	public int dequeue();
}
