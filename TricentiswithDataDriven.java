package tricentis;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.base.Verify;

public class TricentiswithDataDriven {
	WebDriver driver;
	WebDriverWait wait;
	static ExcelReader reader;

	/*
	 * 1.Navigate to URL : http://demowebshop.tricentis.com/
	 */
	@BeforeTest

	public void setUp() {
		System.setProperty("webdriver.chrome.driver", Testdata.ChromeDriverPath);
		driver = new ChromeDriver();
		driver.get(Testdata.Website_path);
	}
	/* 2.Click on Login button */

	@Test(priority = 0)

	public void loginLink() {
		driver.findElement(Locators.Login).click();
	}
	/* 3.Validate “Welcome, Please Sign In!” message */

	@Test(priority = 1)
	public void validateLoginMessage() {

		String WelcomeMessage = driver.findElement(Locators.WelcomeMessage).getText();
		Assert.assertEquals(WelcomeMessage, Testdata.Welcome_Message);
	}
	/* 4.Log in with given credentials */

	@Test(priority = 2)
	public void login() {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(Locators.Emailid)).isDisplayed();
		driver.findElement(Locators.Emailid).sendKeys(Testdata.Email_id);
		driver.findElement(Locators.Password).sendKeys(Testdata.Password);
		driver.findElement(Locators.Loginbutton).click();
	}
	/* 5.Validate the user account ID on top right */

	@Test(priority = 3)
	public void userAccountIDValidation() {

		String UserAccountID = driver.findElement(Locators.UseraccountID).getText();
		Assert.assertEquals(UserAccountID, Testdata.Email_id);
	}
	/* 6.Clear the shopping cart. */

	@Test(priority = 4)
	public void clearingShoppingCart() {
		driver.findElement(Locators.Shoppingcart_link).click();
		String Shoppingcartdata = driver.findElement(Locators.ShoppingcartContains).getAttribute("innerHTML");
		if (Shoppingcartdata.contains("form")) {
			driver.findElement(Locators.Remove_From_cart_checkbox).click();
			driver.findElement(Locators.UpdateShoppingCart_button).click();
		} else {
			// System.out.println("Your cart is empty");
		}
	}
	/* 7.Select “Books” from Categories */

	@Test(priority = 5)
	public void selectionOfBooks() {
		driver.findElement(Locators.Books_Topmenu).click();
		driver.findElement(Locators.Books_categories).click();
		/* 8.Select a book from the list displayed */
		driver.findElement(Locators.Book_selection).click();
	}

	/* 9,10,11,12 points are covered in this method */
	@Test(priority = 6)
	public void validationOfBooks() {
		wait = new WebDriverWait(driver, 30);

		String Price = driver.findElement(Locators.Book_price).getText();
		wait.until(ExpectedConditions.presenceOfElementLocated(Locators.Book_quantity));
		driver.findElement(Locators.Book_quantity).clear();

		/* 9.Get the price details and enter the quantity (more than one) */

		driver.findElement(Locators.Book_quantity).sendKeys(Testdata.quantity);
		/* 10.Click on “Add to cart” */
		driver.findElement(Locators.Book_AddtoCart_button).click();
		/* 11.Validate “The product has been added to shopping cart” message */
		wait.until(ExpectedConditions.presenceOfElementLocated(Locators.bar_notification));
		String bartext = driver.findElement(Locators.bar_notification).getText();
		if(bartext==Testdata.Bartext)
		{
			System.out.println("Same text is present,Actual text:"+bartext+"and Expected text:"+Testdata.Bartext);
		}
		else
		{
			System.out.println("Same text is not present,Actual text:"+bartext+"and Expected text:"+Testdata.Bartext);

		}
		
		//Assert.assertEquals(bartext, Testdata.Bartext);
		/*
		 * 12.Click on “shopping cart” on top right and validate the “Sub-Total”
		 * Price for selected book.
		 */
		driver.findElement(Locators.Shoppingcart_link).click();

		String Sub_total_Actual = driver.findElement(Locators.SubTotal).getText();
		double price_D = Double.parseDouble(Price);
		int price = (int) price_D;

		double quantity_D = Double.parseDouble(Testdata.quantity);
		int quantity = (int) quantity_D;

		double Sub_total_actual_D = Double.parseDouble(Sub_total_Actual);

		int Sub_total_actual = (int) Sub_total_actual_D;

		int Sub_total_Expected = price * quantity;
		Assert.assertEquals(Sub_total_actual, Sub_total_Expected);

	}
	/* 13.Click on “Check-out” */

	@Test(priority = 7)
	public void checkOut() {
		driver.findElement(Locators.I_Agree_checkbox).click();
		driver.findElement(Locators.Checkout_button).click();
	}

	/* 14 and 15 points are covered in this method */
	@Test(dataProvider = "Input data for Billing Address", dataProviderClass = DataProviderforTricentis.class, priority = 8)
	public void billingAddress(String rowNum, String FirstName, String LastName, String Email, String Country,
			String City, String Address1, String Address2, String ZIP, String Phonenumber, String FaxNumber) {
		/* 14.Select “New Address” From “Billing Address” drop down */

		new Select(driver.findElement(Locators.Billing_Address)).selectByVisibleText("New Address");
		/*
		 * 15.Fill all mandatory fields in “Billing Address” and click
		 * “Continue”
		 */
		driver.findElement(Locators.FirstName).clear();
		driver.findElement(Locators.FirstName).sendKeys(FirstName);
		driver.findElement(Locators.LastName).clear();
		driver.findElement(Locators.LastName).sendKeys(LastName);
		driver.findElement(Locators.Email).clear();
		driver.findElement(Locators.Email).sendKeys(Email);
		new Select(driver.findElement(Locators.Country)).selectByVisibleText(Country);
		driver.findElement(Locators.City).sendKeys(City);
		driver.findElement(Locators.Address1).sendKeys(Address1);
		driver.findElement(Locators.Address2).sendKeys(Address2);
		driver.findElement(Locators.ZIP).sendKeys(ZIP);
		driver.findElement(Locators.Phonenumber).sendKeys(Phonenumber);
		driver.findElement(Locators.Continuebutton_BillingAddress).click();

	}

	/*
	 * 16.Select the “Shipping Address” as same as “Billing Address” from
	 * “Shipping Address” drop down and click on “Continue”.
	 */
	@Test(dataProvider = "Input data for Shipping Address", dataProviderClass = DataProviderforTricentis.class, priority = 9)
	public void shippingAddress(String FirstName, String LastName, String address1, String City, String ZIP,
			String Country) {
		String Shipping_Address = FirstName + " " + LastName + ", " + address1 + ", " + City + " " + ZIP + ", "
				+ Country;
		// System.out.println("Shipping Address:" + Testdata.Shipping_Address);
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(Locators.Shipping_Address));
		new Select(driver.findElement(Locators.Shipping_Address)).selectByVisibleText(Shipping_Address);
		driver.findElement(Locators.Continuebutton_ShippingAddress).click();
	}

	/*
	 * 17.Select the shipping method as “Next Day Air” and click on “Continue”
	 */
	@Test(priority = 10)
	public void shippingMethod() {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(Locators.Next_Day_Air));
		driver.findElement(Locators.Next_Day_Air).click();
		driver.findElement(Locators.Continue_ShippingMethod).click();

	}

	/*
	 * 18.Choose the payment method as COD (Cash on delivery) and click on
	 * “Continue”
	 */
	@Test(priority = 11)
	public void paymentMethod() {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(Locators.COD));
		driver.findElement(Locators.COD).click();
		driver.findElement(Locators.Continue_Paymentmethod).click();

	}

	/* 19.Validate the message “You will pay by COD” and click on “Continue” */
	@Test(priority = 12)
	public void PaymentInformationValidation() {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(Locators.Validate_COD_Msg));
		String COD_Msg = driver.findElement(Locators.Validate_COD_Msg).getText();
		Assert.assertEquals(COD_Msg, Testdata.COD_Msg);
		driver.findElement(Locators.Continue_PaymentInformation).click();
	}

	/* 20 and 21 points are covered in this method */
	@Test(priority = 13)
	public void confirmOrder() {
		wait = new WebDriverWait(driver, 30);
		/* 20.Click on “Confirm Order” */

		wait.until(ExpectedConditions.elementToBeClickable(Locators.Confirm_button));
		driver.findElement(Locators.Confirm_button).click();
		/*
		 * 21.Validate the message “Your order has been successfully processed!”
		 * and print the Order number
		 */
		wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.Confirmation_Message));
		String ConfirmationMessage = driver.findElement(Locators.Confirmation_Message).getText();
		Assert.assertEquals(ConfirmationMessage, Testdata.ConfirmationMessage);
		String Orderno = driver.findElement(Locators.OrderNumber).getText();
		System.out.println("Orderno:" + Orderno);
	}

	/* 22.Click on “Continue” and log out from the application */
	@Test(priority = 14)
	public void logOut() {
		driver.findElement(Locators.ContinueOrderConfirmation).click();

		driver.findElement(Locators.LogoutButton).click();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
