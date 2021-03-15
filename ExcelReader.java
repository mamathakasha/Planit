package tricentis;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ExcelReader {
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	/*
	 * here we are defining constructor of ExcelReader to check whether the
	 * document is present in the specified path.
	 */
	public ExcelReader(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// To get the rowcount
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}

	public boolean isSheetExist(String sheetName)
	{
		int index=workbook.getSheetIndex(sheetName);
	
	if(index==-1){
		index=workbook.getSheetIndex(sheetName.toUpperCase());
		if(index==-1)
			return false;
		else
			return true;
	}else
		return true;
	
	}
	
	public int getColumnCount(String sheetName)
	{
		if(isSheetExist(sheetName))
			return -1;
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(0);
		if(row==null)
			return -1;
		return row.getLastCellNum();
	}
	
	public String getCellData(String sheetName,String colName,int rowNum)
	{
		try{
			if(rowNum<=0)
			
				return "";
				
				int index=workbook.getSheetIndex(sheetName);
				int col_Num=-1;
				if(index==-1)
				{
					return "";
				}
				sheet=workbook.getSheetAt(index);
				row=sheet.getRow(0);
				for(int i=0;i<row.getLastCellNum();i++)
				{
					row.getRowNum();
					if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName.trim()))
						col_Num=i;
				}
				if(col_Num==-1){
					return "";
					
				}
				sheet=workbook.getSheetAt(index);
				row=sheet.getRow(rowNum-1);
				if(row==null)
				{
					return "";
				}
				cell=row.getCell(col_Num);
				if(cell==null)
				{
					return "";
				}
				if(cell.getCellType()==CellType.STRING)
				{
					DataFormatter formatter=new DataFormatter();
					return cell.getStringCellValue();
				}
				else if(cell.getCellType()==CellType.NUMERIC ||cell.getCellType()==CellType.FORMULA){
					double d1=cell.getNumericCellValue();
				String cellText=String.valueOf(cell.getNumericCellValue());
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					double d=cell.getNumericCellValue();
					Calendar cal=Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText=(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText=cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+1+"/"+cellText;
					
				}
				return cellText;
				}else if(cell.getCellType()==CellType.BLANK){
					return "";
					
				}else{
					return String.valueOf(cell.getBooleanCellValue());
				}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return "row"+ rowNum + "or column" +colName +"does not exist in xls";
		}
		
	}

}
