/*
This class has a main method for testing the different BankAccounts for parallel performance.
It will take a given bank account and enter a loop where it loops some large # of times and 
deposits then immediately withdrawals some moderate amount of money.

If concurrency is well handled in that BankAccount class, it should have $0 at the end, and never 
throw an insufficient funds exception.
*/
import java.util.*;
public class BankRunner implements Runnable
{
	BankAccount acc;
	Thread[] threads;
	final static int TIMES=100000;
	
	public BankRunner(BankAccount account,int threadNum)
	{
		acc=account;
		threads=new Thread[threadNum];
	}
	
	public void initThreads()
	{
		for(int i=0;i<threads.length;i++)
		{
			threads[i]=new Thread(this);
			threads[i].start();
		}
	}
	
	public void waitForThreads()
	{
		for(int i=0;i<threads.length;i++) try{threads[i].join();}catch(Exception e){}
	}
	
	//go through and deposit then withdrawal some amount over and over again
	public void run()
	{
		Random r=new Random();
		try
		{
			for(int i=0;i<TIMES;i++)
			{
				int x=r.nextInt(1000);
				acc.deposit((long)x);
				acc.withdraw((long)x);
			}
		}
		catch(Exception e){System.err.println("Exception occurred: "+e.getMessage());}
	}
	
	public static void main(String[] args)
	{
		//Note: uncomment the bank account you want to test
		BankAccount acc=new BankAccountUnlocked("Tyler");
		//BankAccount acc=new BankAccountLocked("Tyler");
		//BankAccount acc=new BankAccountSynched("Tyler");
		System.out.println("New account: "+acc+"\nRunning deposits followed by withdrawls that should work out to 0... (each thread runs "+BankRunner.TIMES+" times)");
		BankRunner runner=new BankRunner(acc,4);
		runner.initThreads();
		
		runner.waitForThreads();
		System.out.println("Exiting with account: "+acc);
	}
}
