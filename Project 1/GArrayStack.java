import java.util.EmptyStackException;
@SuppressWarnings("unchecked")
/**
 * This class implements generic stacks by using array.
 * @author Chun-Wei Chen
 * @version CSE 322 AC 01/19/13
 */
public class GArrayStack<T> implements GStack<T> {

	private int capacity;
	private T[] stack;
	private int idx;
	
	/**
	 * Create an empty Stack.
	 */
	public GArrayStack() {
		capacity = 10;
		stack = (T[]) new Object[capacity];
		idx = -1;
	}
	
	/**
	 * Returns true if the stack is empty.
	 * @return true if the stack is empty
	 */
	public boolean isEmpty() {
		if (idx == -1)
			return true;
		return false;
	}

	/**
	 * Stores a value of type T into stack.
	 * @param d a value of type T
	 */
	public void push(T d) {
		ensureCapacity();
		idx++;
		stack[idx] = d;
	}
	
	/**
	 * Ensures the stack has space to store at least one more value.
	 * If the space is not enough to store one more value, create 
	 * a new one with twice as the old capacity and 
	 * copy the old values stored in the stack into the new one.
	 */
	private void ensureCapacity() {
		if (idx + 2 <= capacity)
			return;
		capacity *= 2;
		T[] tempStack = (T[]) new Object[capacity];
		for (int i = 0; i <= idx; i++) {
			tempStack[i] = stack[i];
		}
		stack = tempStack;
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
		T value = stack[idx];
		idx--;
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
		return stack[idx];
	}
}
