package utility;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	protected static FileInputStream fis = null;
	protected static XSSFWorkbook wb = null;
	protected static XSSFSheet ws = null;
	protected static XSSFRow row = null;
	protected static XSSFCell cell = null;
	String excelPath = null;
	
	public ExcelUtils (String excelPath) throws Exception
	{
		this.excelPath = excelPath;
		fis = new FileInputStream(excelPath);
		wb = new XSSFWorkbook(fis);
		fis.close();
	}
	
	public int getRowCount(String sheetName)
	{
		ws = wb.getSheet(sheetName);
		int rowCount = ws.getLastRowNum()+1;
		return rowCount;
	}
	
	public int getColumnCount(String sheetName)
	{
		ws = wb.getSheet(sheetName);
		row = ws.getRow(0);
		int colCount = row.getLastCellNum();
		return colCount;
	}
	
	public String getCellData (String sheetName, int getColumnCount, int getRowCount)
	{
		ws = wb.getSheet(sheetName);
		row = ws.getRow(getRowCount);
		cell = row.getCell(getColumnCount);
		
		return cell.getStringCellValue();
	}

}
