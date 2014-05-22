import java.util.NoSuchElementException;
/*
this class implements a fairly standard int queue; it's mainly meant for showing how to test.
it's a linked list implementation, maintaining a front and back.
things get enqueued at the back and dequeued from the front.
the 'next' pointer of each node points backward (that is, toward 'back')
*/

public class AwesomeQueue implements BasicQueue
{
	// enqueue at back, dequeue from front
	private LLNode front, back; //pointers point to back of list

	//initialize front and back to null
	public AwesomeQueue() {
		front = null;
		back = null;
	}

	public boolean isEmpty() {
		return front == null;
	}

	//add to back
	public void enqueue(int v) {
		if (isEmpty()) {
			back = front = new LLNode(v);
		} else {
			back.next = new LLNode(v);
			back=back.next;
		}
	}

	//remove from front
	//throw an exception if its empty
	public int dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();
		int value = front.value;
		if (front == back) // only 1 node; set pointers to null
		{
			front = back = null;
		} else {
			front=front.next;
		}
		return value;
	}
	
	//our linked list node; just has next and value fields
	//it is static because it doesn't need to access its outer class's values
	private static class LLNode {
		private LLNode next;
		private int value;

		public LLNode(int v) {
			value = v;
			next=null;
		}
	}
}
