import static org.junit.Assert.*;
import org.junit.*;
import java.util.EmptyStackException;

public class ArrayStackTest {
	private ArrayStack testArrayStack;
	
	@Test
	public void testIsEmpty() {
		testArrayStack = new ArrayStack();
		assertTrue(testArrayStack.isEmpty());
	}

	@Test
	public void testPush() {
		testArrayStack = new ArrayStack();
		testArrayStack.push(0.23);
		assertFalse(testArrayStack.isEmpty());
	}

	@Test
	public void testPop() {
		testArrayStack = new ArrayStack();
		testArrayStack.push(0.999);
		assertEquals(0.999, testArrayStack.pop(), 10^-6);
		assertTrue(testArrayStack.isEmpty());
		testArrayStack.push(0.123);
		testArrayStack.push(0.456);
		assertEquals(0.456, testArrayStack.pop(), 10^-6);
		assertFalse(testArrayStack.isEmpty());
		testArrayStack.push(0.321);
		assertEquals(0.321, testArrayStack.pop(), 10^-6);
		assertEquals(0.123, testArrayStack.pop(), 10^-6);
		try {
			testArrayStack.pop();
			fail("Should have thrown EmptyStackException");
		} catch (EmptyStackException e) {
			
		}
	}

	@Test
	public void testPeek() {
		testArrayStack = new ArrayStack();
		testArrayStack.push(0.234);
		assertEquals(0.234, testArrayStack.peek(), 10^-6);
		assertFalse(testArrayStack.isEmpty());
		assertEquals(0.234, testArrayStack.pop(), 10^-6);
		assertTrue(testArrayStack.isEmpty());
		testArrayStack.push(0.777);
		testArrayStack.push(0.111);
		assertEquals(0.111, testArrayStack.peek(), 10^-6);
		assertEquals(0.111, testArrayStack.pop(), 10^-6);
		assertEquals(0.777, testArrayStack.peek(), 10^-6);
		assertFalse(testArrayStack.isEmpty());
		assertEquals(0.777, testArrayStack.pop(), 10^-6);
		try {
			testArrayStack.peek();
			fail("Should have thrown EmptyStackException");
		} catch (EmptyStackException e) {
			
		}
	}
}
