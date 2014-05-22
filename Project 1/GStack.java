/**
 * Interface for a type-generic stack.
 * @version CSE 332 13wi
 * 
 * NOTE: The comments for this interface are horrible! You will
 *       need to write something better for your implementations.
 */
public interface GStack<T> {
    /** 
     * is empty?
     */
    public boolean isEmpty();

    /**
     * push
     */
    public void push(T d);

    /**
     * pop
     * @return the deleted value
     * @throws EmptyStackException if stack is empty
     */
    public T pop();

    /**
     * peek
     * @throws EmptyStackException if stack is empty
     */
    public T peek();
}
