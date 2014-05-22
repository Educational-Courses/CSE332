public class Bag<E>
{
	private E item;
	public void setItem(E x) { item = x; }
	public E getItem() { return item; }
	
	public static void main(String[] args)
	{
		Bag<Integer> bagInt=new Bag<Integer>();
		bagInt.setItem(new Integer(42));
		Bag<String> bagStr=new Bag<String>();
		bagStr.setItem("A very fine string");
		
		//which of the following (if any) are legal?
		//bagInt.setItem("A very fine string");
		//bagInt.setItem("42");
		//bagStr.setItem(new Integer(42));
		//bagStr.setItem(new Object());
		//bagInt=bagStr;
		//bagInt.setItem(42);
		//Bag<Bag<String>> bagBag=new Bag<Bag<String>>();
		
		//this is apparently legal; what type is 'E'?
		Bag someBag=new Bag();
		
		/*
		//what's wrong with the following code (once uncommented)?
		String s="x";
		someBag.setItem(s);
		s=someBag.getItem();
		*/
	}
}

