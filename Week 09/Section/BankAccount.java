
abstract public class BankAccount
{
	protected long balance; //in dollars
	String name;
	
	public BankAccount(String n)
	{
		name=n;
		balance=0;
	}
	
	public String toString()
	{
		return name+"'s balance is $"+balance+" "+(balance>0?":)":":(");
	}
	
	abstract void withdraw(long amt) throws Exception;
	abstract void deposit(long amt);
}
