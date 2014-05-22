import java.util.EmptyStackException;

/**
 * This class implements stacks by using LinkedList.
 * @author Chun-Wei Chen
 * @version 1/13/13
 */
public class ListStack implements DStack {
	private Node stack;
	
	/**
	 * Create an empty Stack.
	 */
	public ListStack() {
		stack = null;
	}

	/**
	 * Returns true if the stack is empty.
	 * @return true if the stack is empty
	 */
	public boolean isEmpty() {
		if (stack == null)
			return true;
		return false;
	}

	/**
	 * Stores a double value into stack.
	 * @param d a double value
	 */
	public void push(double d) {
		if (isEmpty()) {
			stack = new Node(d, null);
		} else {
			Node temp = new Node(d, stack);
			stack = temp;
		}
	}

	/**
	 * Returns the last value stored into the stack and 
	 * removes it from the stack.
	 * @return the last value stored into the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public double pop() {
		if (isEmpty())
			throw new EmptyStackException();
		double value = stack.value;
		stack = stack.next;
		return value;
	}

	/**
	 * Returns the last value stored into the stack and 
	 * without modifying the stack.
	 * @return the last value stored into the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public double peek() {
		if (isEmpty())
			throw new EmptyStackException();
		return stack.value;
	}
	
	/**
	 * Inner 'Node' class.
	 */
	class Node {
		double value;
		Node next;
		
		/**
		 * Constructs a Node which holds a double and a Node
		 * @param d a double value
		 * @param n a Node
		 */
		Node(double d, Node n) {
			value = d;
			next = n;
		}
	}
}
