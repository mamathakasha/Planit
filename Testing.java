package tricentis;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Testing {
	
	@Test
	public void Test2()
	{String Price="10.00";
		double price_D = Double.parseDouble(Price);
	int price = (int)price_D;
	System.out.println("Price after splitting:" + price);
		
	}

}
