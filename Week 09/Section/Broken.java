public class Broken implements Runnable
{
	//we'll try to use this as a lock, but it (likely) won't work due to a data-race
	boolean stop=false;

	public void run()
	{
		System.out.println("Entering run()");
		while(!stop) {}
		System.out.println("Finished run()");
	}
	
	public static void main(String[] args)
	{
		Broken broke=new Broken();
		Thread thread=new Thread(broke);
		thread.start();
		
		System.out.println("Waiting 2 seconds");
		long startTime=System.currentTimeMillis();
		while(System.currentTimeMillis()-startTime<2000) ; //not the most efficient way to wait
		System.out.println("Time's up; stop the thread");
		broke.stop=true;
		System.out.println("Finished main(); now waiting for run() to finish....");
	}
}
