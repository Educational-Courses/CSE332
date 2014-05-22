import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.Random;

/*
this class demonstrates how to time segments of your code
some general tips:
-if the thing you're timing is fast, as many are, get the time for a large batch of them (say, 100000); 
	if you only time 1, the noise of the timer will probably dominate
-start and stop the timer precisely around the code you're interested in timing, and avoid other stuff (constructors, printlns);
	these will contribute to the time values, making them less accurate for what you're interested in measuring
	-in reality, extra bits of code will often run so fast that it won't impact the time, though some things, like input/output 
		are quite slow and may well mess up the time
-timing information is approximate and depends on numerous factors: machine used, program parameters, background processes, etc.
-timing is no substitute for asymptotic analysis of an algorithm, though both have their places
-'warm up' the java virtual machine before starting your timing tests.  do this by running similar tests first, but disregarding their 
	timing results.  essentially, java will optimize as it runs, so it will be slower at first until it hits its stride
*/
public class QueueTimer
{
	public static void main(String[] args)
	{
		int n=200000;
		
		System.out.println("Warming up....");
		testNOperations(n,new BadQueue(),new Random());
		
		decentTimingExample(n);
		//badTimingExample(n);
	}
	
	public static void decentTimingExample(int n)
	{
		long startTime,endTime;
		BasicQueue queue;
		
		System.out.println("Running both queues on test of "+n+" operations");
		
		queue=new AwesomeQueue();
		startTime=System.currentTimeMillis();
		QueueTimer.testNOperations(n,queue,new Random(17));
		endTime=System.currentTimeMillis();
		System.out.println("AwesomeQueue time: "+(endTime-startTime)+" ms");
		
		queue=new BadQueue();
		startTime=System.currentTimeMillis();
		QueueTimer.testNOperations(n,queue,new Random(17));
		endTime=System.currentTimeMillis();
		System.out.println("BadQueue time: "+(endTime-startTime)+" ms");
	}
	
	private static void testNOperations(int n,BasicQueue queue,Random r)
	{
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
				num=queue.dequeue();
				assertTrue("Queues differ on dequeue",goodQueue.remove()==num);
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

	//this version of the timing code does a few things wrong (all fairly minor); what are they?
	//honestly, these are unlikely to effect the time much here, but they're still bad practice, and *can* result in far off results in some situations
	public static void badTimingExample(int n)
	{
		long startTime,endTime;
		System.out.println("Running both queues on test of "+n+" operations");
		
		startTime=System.currentTimeMillis();
		System.out.println("Running....");
		QueueTimer.testNOperations(n,new AwesomeQueue(),new Random());
		System.out.print("AwesomeQueue time: ");
		endTime=System.currentTimeMillis();
		System.out.println((endTime-startTime)+" ms");
		
		startTime=System.currentTimeMillis();
		System.out.println("Running....");
		QueueTimer.testNOperations(n,new BadQueue(),new Random());
		System.out.print("BadQueue time: ");
		endTime=System.currentTimeMillis();
		System.out.println((endTime-startTime)+" ms");
	}
}
