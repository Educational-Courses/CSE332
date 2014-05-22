
public class BankAccountUnlocked extends BankAccount
{
	BankAccountUnlocked(String n)
	{
		super(n);
	}
	
	void withdraw(long amt) throws Exception
	{
		if(amt>balance) throw new Exception("Insufficient Funds");
		balance=balance-amt;
	}
	
	void deposit(long amt)
	{
		balance=balance+amt;
	}
}
