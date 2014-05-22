import static org.junit.Assert.*;
import org.junit.*;
import java.util.EmptyStackException;

public class GListStackTest {
	private GListStack<Double> testGListStack;
	
	@Test
	public void testIsEmpty() {
		testGListStack = new GListStack<Double>();
		assertTrue(testGListStack.isEmpty());
	}

	@Test
	public void testPush() {
		testGListStack = new GListStack<Double>();
		testGListStack.push(0.23);
		assertFalse(testGListStack.isEmpty());
	}

	@Test
	public void testPop() {
		testGListStack = new GListStack<Double>();
		testGListStack.push(0.999);
		assertEquals(0.999, testGListStack.pop(), 10^-6);
		assertTrue(testGListStack.isEmpty());
		testGListStack.push(0.123);
		testGListStack.push(0.456);
		assertEquals(0.456, testGListStack.pop(), 10^-6);
		assertFalse(testGListStack.isEmpty());
		testGListStack.push(0.321);
		assertEquals(0.321, testGListStack.pop(), 10^-6);
		assertEquals(0.123, testGListStack.pop(), 10^-6);
		try {
			testGListStack.pop();
			fail("Should have thrown EmptyStackException");
		} catch (EmptyStackException e) {
			
		}
	}

	@Test
	public void testPeek() {
		testGListStack = new GListStack<Double>();
		testGListStack.push(0.234);
		assertEquals(0.234, testGListStack.peek(), 10^-6);
		assertFalse(testGListStack.isEmpty());
		assertEquals(0.234, testGListStack.pop(), 10^-6);
		assertTrue(testGListStack.isEmpty());
		testGListStack.push(0.777);
		testGListStack.push(0.111);
		assertEquals(0.111, testGListStack.peek(), 10^-6);
		assertEquals(0.111, testGListStack.pop(), 10^-6);
		assertEquals(0.777, testGListStack.peek(), 10^-6);
		assertFalse(testGListStack.isEmpty());
		assertEquals(0.777, testGListStack.pop(), 10^-6);
		try {
			testGListStack.peek();
			fail("Should have thrown EmptyStackException");
		} catch (EmptyStackException e) {
			
		}
	}
}