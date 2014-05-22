import java.util.EmptyStackException;

/**
 * This class implements generic stacks by using LinkedList.
 * @author Chun-Wei Chen
 * @version CSE 322 AC 01/19/13
 */
public class GListStack<T> implements GStack<T> {
	private Node<T> stack;
	
	/**
	 * Create an empty Stack.
	 */
	public GListStack() {
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
	 * Stores a a value of type T into stack.
	 * @param d a value of type T
	 */
	public void push(T d) {
		if (isEmpty()) {
			stack = new Node<T>(d, null);
		} else {
			Node<T> temp = new Node<T>(d, stack);
			stack = temp;
		}
	}

	/**
	 * Returns the last value stored into the stack and 
	 * removes it from the stack.
	 * @return the last value stored into the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public T pop() {
		if (isEmpty())
			throw new EmptyStackException();
		T value = stack.value;
		stack = stack.next;
		return value;
	}

	/**
	 * Returns the last value stored into the stack and 
	 * without modifying the stack.
	 * @return the last value stored into the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public T peek() {
		if (isEmpty())
			throw new EmptyStackException();
		return stack.value;
	}
	
	/**
	 * Inner 'Node' class.
	 */
	@SuppressWarnings("hiding")
	class Node<T> {
		T value;
		Node<T> next;
		
		/**
		 * Constructs a Node which holds a double and a Node
		 * @param d a double value
		 * @param n a Node
		 */
		Node(T d, Node<T> n) {
			value = d;
			next = n;
		}
	}
}