import java.util.concurrent.locks.*;
public class BankAccountLocked extends BankAccount
{
	ReentrantLock lock;
	BankAccountLocked(String n)
	{
		super(n);
		lock=new ReentrantLock();
	}
	
	void withdraw(long amt) throws Exception
	{
		lock.lock();
		try
		{
			if(amt>balance) throw new Exception("Insufficient Funds");
			balance=balance-amt;
		}
		finally //this guarantees that it always unlocks, even if an exception is thrown or we return
		{
			lock.unlock();
		}
	}
	
	void deposit(long amt)
	{
		lock.lock();
		balance=balance+amt;
		lock.unlock();
	}
}
