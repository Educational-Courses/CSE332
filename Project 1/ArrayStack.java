import java.util.EmptyStackException;

/**
 * This class implements stacks by using array.
 * @author Chun-Wei Chen
 * @version 1/13/13
 */
public class ArrayStack implements DStack {
	private int capacity;
	private double[] stack;
	private int idx;
	
	/**
	 * Create an empty Stack.
	 */
	public ArrayStack() {
		capacity = 10;
		stack = new double[capacity];
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
	 * Stores a double value into stack.
	 * @param d a double value
	 */
	public void push(double d) {
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
		double[] tempStack = new double[capacity];
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
	public double pop() {
		if (isEmpty())
			throw new EmptyStackException();
		double value = stack[idx];
		idx--;
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
		return stack[idx];
	}
}
