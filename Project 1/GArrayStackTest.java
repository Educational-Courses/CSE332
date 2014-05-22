import static org.junit.Assert.*;
import org.junit.*;
import java.util.EmptyStackException;

public class GArrayStackTest {
	private GArrayStack<Double> testGArrayStack;
	
	@Test
	public void testIsEmpty() {
		testGArrayStack = new GArrayStack<Double>();
		assertTrue(testGArrayStack.isEmpty());
	}

	@Test
	public void testPush() {
		testGArrayStack = new GArrayStack<Double>();
		testGArrayStack.push(0.23);
		assertFalse(testGArrayStack.isEmpty());
	}

	@Test
	public void testPop() {
		testGArrayStack = new GArrayStack<Double>();
		testGArrayStack.push(0.999);
		assertEquals(0.999, testGArrayStack.pop(), 10^-6);
		assertTrue(testGArrayStack.isEmpty());
		testGArrayStack.push(0.123);
		testGArrayStack.push(0.456);
		assertEquals(0.456, testGArrayStack.pop(), 10^-6);
		assertFalse(testGArrayStack.isEmpty());
		testGArrayStack.push(0.321);
		assertEquals(0.321, testGArrayStack.pop(), 10^-6);
		assertEquals(0.123, testGArrayStack.pop(), 10^-6);
		try {
			testGArrayStack.pop();
			fail("Should have thrown EmptyStackException");
		} catch (EmptyStackException e) {
			
		}
	}

	@Test
	public void testPeek() {
		testGArrayStack = new GArrayStack<Double>();
		testGArrayStack.push(0.234);
		assertEquals(0.234, testGArrayStack.peek(), 10^-6);
		assertFalse(testGArrayStack.isEmpty());
		assertEquals(0.234, testGArrayStack.pop(), 10^-6);
		assertTrue(testGArrayStack.isEmpty());
		testGArrayStack.push(0.777);
		testGArrayStack.push(0.111);
		assertEquals(0.111, testGArrayStack.peek(), 10^-6);
		assertEquals(0.111, testGArrayStack.pop(), 10^-6);
		assertEquals(0.777, testGArrayStack.peek(), 10^-6);
		assertFalse(testGArrayStack.isEmpty());
		assertEquals(0.777, testGArrayStack.pop(), 10^-6);
		try {
			testGArrayStack.peek();
			fail("Should have thrown EmptyStackException");
		} catch (EmptyStackException e) {
			
		}
	}
}