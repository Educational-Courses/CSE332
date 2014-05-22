
public class BankAccountSynched extends BankAccount 
{
	BankAccountSynched(String n)
	{
		super(n);
	}
	
	void withdraw(long amt) throws Exception
	{
		synchronized(this)
		{
			if(amt>balance) throw new Exception("Insufficient Funds");
			balance=balance-amt;
		}
	}
	
	void deposit(long amt)
	{
		synchronized(this)
		{
			balance=balance+amt;
		}
	}
}
