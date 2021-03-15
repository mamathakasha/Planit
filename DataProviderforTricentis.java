package tricentis;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.DataProvider;

public class DataProviderforTricentis {

	static ExcelReader reader;

	@DataProvider(name = "Input data for Billing Address")
	public static Iterator<Object[]> getDataForBillingAddress(Method methodname) {
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		try {
			reader = new ExcelReader(Testdata.Excelpath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (int rowNum = 2; rowNum <= reader.getRowCount(Testdata.sheetNameBillingAddress); rowNum++) {
			String sheetName = Testdata.sheetNameBillingAddress;
			/*
			 * Here in the If condition we need to pass exact Methodname to
			 * which the data from the excel is passed ,which is available in
			 * the executionclass (TricentiswithDataDriven).
			 */
			if (methodname.getName().equalsIgnoreCase("billingAddress")) {
				/*
				 * here in the below code , string name we are providing should
				 * exactly match with the column name in Excel sheet
				 */
				String rownum = reader.getCellData(sheetName, "SI.no", rowNum);
				String FirstName = reader.getCellData(sheetName, "FirstName", rowNum);
				String LastName = reader.getCellData(sheetName, "LastName", rowNum);
				String Email = reader.getCellData(sheetName, "Email", rowNum);
				String Country = reader.getCellData(sheetName, "Country", rowNum);
				String City = reader.getCellData(sheetName, "City", rowNum);

				String Address1 = reader.getCellData(sheetName, "Address1", rowNum);
				String Address2 = reader.getCellData(sheetName, "Address2", rowNum);
				String ZIP = reader.getCellData(sheetName, "ZIP", rowNum);
				String Phonenumber = reader.getCellData(sheetName, "Phonenumber", rowNum);
				String FaxNumber = reader.getCellData(sheetName, "FaxNumber", rowNum);

				Object ob[] = { rownum, FirstName, LastName, Email, Country, City, Address1, Address2, ZIP, Phonenumber,
						FaxNumber };
				data.add(ob);

			}
		}
		return data.iterator();
	}

	@DataProvider(name = "Input data for Shipping Address")
	public static Iterator<Object[]> getDataForShippingAddress(Method methodname) {
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		try {
			reader = new ExcelReader(Testdata.Excelpath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (int rowNum = 2; rowNum <= reader.getRowCount(Testdata.sheetNameBillingAddress); rowNum++) {
			String sheetName = Testdata.sheetNameBillingAddress;
			if (methodname.getName().equalsIgnoreCase("shippingAddress")) {
				String FirstName = reader.getCellData(sheetName, "FirstName", rowNum);
				String LastName = reader.getCellData(sheetName, "LastName", rowNum);
				String Address1 = reader.getCellData(sheetName, "Address1", rowNum);
				String City = reader.getCellData(sheetName, "City", rowNum);
				String ZIP = reader.getCellData(sheetName, "ZIP", rowNum);

				String Country = reader.getCellData(sheetName, "Country", rowNum);

				Object ob[] = { FirstName, LastName, Address1, City, ZIP, Country };
				data.add(ob);

			}
		}
		return data.iterator();
	}
	
}
