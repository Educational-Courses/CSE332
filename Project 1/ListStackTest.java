import static org.junit.Assert.*;
import org.junit.*;
import java.util.EmptyStackException;

public class ListStackTest {
	private ListStack testListStack;
	
	@Test
	public void testIsEmpty() {
		testListStack = new ListStack();
		assertTrue(testListStack.isEmpty());
	}

	@Test
	public void testPush() {
		testListStack = new ListStack();
		testListStack.push(0.23);
		assertFalse(testListStack.isEmpty());
	}

	@Test
	public void testPop() {
		testListStack = new ListStack();
		testListStack.push(0.999);
		assertEquals(0.999, testListStack.pop(), 10^-6);
		assertTrue(testListStack.isEmpty());
		testListStack.push(0.123);
		testListStack.push(0.456);
		assertEquals(0.456, testListStack.pop(), 10^-6);
		assertFalse(testListStack.isEmpty());
		testListStack.push(0.321);
		assertEquals(0.321, testListStack.pop(), 10^-6);
		assertEquals(0.123, testListStack.pop(), 10^-6);
		try {
			testListStack.pop();
			fail("Should have thrown EmptyStackException");
		} catch (EmptyStackException e) {
			
		}
	}

	@Test
	public void testPeek() {
		testListStack = new ListStack();
		testListStack.push(0.234);
		assertEquals(0.234, testListStack.peek(), 10^-6);
		assertFalse(testListStack.isEmpty());
		assertEquals(0.234, testListStack.pop(), 10^-6);
		assertTrue(testListStack.isEmpty());
		testListStack.push(0.777);
		testListStack.push(0.111);
		assertEquals(0.111, testListStack.peek(), 10^-6);
		assertEquals(0.111, testListStack.pop(), 10^-6);
		assertEquals(0.777, testListStack.peek(), 10^-6);
		assertFalse(testListStack.isEmpty());
		assertEquals(0.777, testListStack.pop(), 10^-6);
		try {
			testListStack.peek();
			fail("Should have thrown EmptyStackException");
		} catch (EmptyStackException e) {
			
		}
	}
}
