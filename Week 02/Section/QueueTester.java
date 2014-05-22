import static org.junit.Assert.*;

import org.junit.*;
import java.util.NoSuchElementException;
import java.util.*;

/*
This is a sample junit test file for the AwesomeQueue class.
It exists only to verify that AwesomeQueue passes a series of tests designed to spot bugs.

Running a JUnit test:
Run it in Eclipse by going to the 'Run' menu, selecting 'Run Configurations', and double clicking 'JUnit test'.  If there are multiple junit files laying around, you may have to select which one you want.
Running it will execute all the methods marked with the '@Test' annotation, then give you a report of whether they passed or not.
For the most part, tests pass as long as they don't fail an assertion or throw an exception.
*/

public class QueueTester {

	private AwesomeQueue queue;
	
	@Before
	//this method will be run before each '@Test' due to the '@Before' annotation.
	//use it to initialize data structures, and other things you need to do before each test 
	public void init()
	{
		queue=new AwesomeQueue();
	}
	
	@Test
	//the '@Test' annotation marks it as, surprisingly enough, a test.
	//each test will be run independently 
	public void testIsEmptyOnEmpty() {
		assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testIsEmptyOnNotEmpty() {
		queue.enqueue(5);
		assertTrue(!queue.isEmpty());
	}
	
	@Test
	public void testOneEnqueue() {
		queue.enqueue(5);
	}
	
	@Test
	public void testOneDequeue() {
		queue.enqueue(5);
		assertTrue(5==queue.dequeue());
	}
	
	@Test
	public void testOrdering() {
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		assertEquals(1,queue.dequeue());
		assertEquals(2,queue.dequeue());
		assertEquals(3,queue.dequeue());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testDequeueOnEmpty() {
		queue.dequeue();
	}
	
	@Test
	//this checks to make sure that enqueueing then dequeueing doesn't break isEmpty()
	public void testEmptyAfterDequeue() {
		queue.enqueue(5);
		queue.dequeue();
		assertTrue(queue.isEmpty());
	}
	
	@Ignore
	//you can use the '@Ignore' annotation to effectively hide a test
	public void ignoreMe()
	{
		throw new RuntimeException("Error");
	}
	
	//you can, of course, declare other methods besides tests; just exclude the '@Test'.
	//This one gets used by several tests that call it with different arguments.
	//this performs n random operations on an AwesomeQueue and java's LinkedList, and verifies that they end up the same.
	//this is useful for testing because 1) it can test for very large examples and 2) it tests enqueues and dequeues in all sorts of interleaved orderings, possibly picking out obscure bugs
	//Note: this uses random values, which means that running tests different times could have different results (if, say, there is a bug in your code but hitting it is rare)
	private void testNOperations(int n)
	{
		Random r=new Random();
		LinkedList<Integer> goodQueue=new LinkedList<Integer>();
		int num;
		for(int i=0;i<n;i++)
		{
			//enqueue element if queue is empty, or on a 2/3 chance
			if(queue.isEmpty() || r.nextInt(3)<2)
			{
				num=r.nextInt(100);
				queue.enqueue(num);
				goodQueue.add(num);
			}
			else //dequeue
			{
				assertTrue("Queues differ on dequeue",goodQueue.remove()==queue.dequeue());
			}
			//using different asserts, such as assertEquals, can clarify your intent to people reading your code 
			//technically, when using assertEquals, you're supposed to put the expected value first, then the actual value
			assertEquals("goodQueue and queue do not match on isEmpty()",goodQueue.isEmpty(),queue.isEmpty());
		}
		
		//now that we're done going through n operations, dequeue until empty and compare results
		while(!queue.isEmpty())
		{
			assertTrue("goodQueue is empty but queue isn't",!goodQueue.isEmpty());
			assertTrue("End dequeues do not match",goodQueue.remove()==queue.dequeue());
		}
		assertTrue("queue is empty but goodQueue isn't",goodQueue.isEmpty());
	}
	
	@Test
	public void test10()
	{
		testNOperations(10);
	}
	
	@Test
	public void test100()
	{
		testNOperations(100);
	}
	
	@Test
	public void test10000()
	{
		testNOperations(10000);
	}
	
	@Test
	public void testMillion()
	{
		testNOperations(1000000);
	}
}
