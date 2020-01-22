package main;

import org.testng.annotations.Test;

import utility.ExcelUtils;
import utility.Service;

import org.testng.annotations.BeforeMethod;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

public class GoogleSearch implements Service{
	
	ExcelUtils eu = null;
	
	WebDriver driver;
	
  @Test(dataProvider = "data")
  public void Test (String sKeys) {
	  WebElement searchBox = driver.findElement(By.xpath("//*[@name = 'q']"));
	  System.out.println("searchBox found");
	  
	  searchBox.sendKeys(sKeys);
	  searchBox.submit();
	  
	  int pageNos = driver.findElements(By.cssSelector("[valign='top'] > td")).size();
	  System.out.println("No. of page : " +pageNos);
	  
	  outer : for (int i=1; i < pageNos; i++)
	  {
		  if (i > 1)
		  {
			  driver.findElement(By.cssSelector("[aria-label='Page " + i + "']")).click(); //navigate to page number i
			  System.out.println("\nPage no. " +i);
		  }
		  
		  List<WebElement> allSearchResult = driver.findElements(By.tagName("cite"));
		  System.out.println("Number of searches in page: " +allSearchResult.size());
		  String sKeyword = "finzy";
		  
		  for (int j=0; j < allSearchResult.size(); j++)
		  {
			  String str = allSearchResult.get(j).getText();
			  System.out.println(str);
			  
			  if (str.contains(sKeyword))
			  {
				  System.out.println("Match found !!!");
				  System.out.println("\nSearch found on page no. " +i);
				  break outer;
			  }
		  }
		  
	  }
	  
	  
  }


@DataProvider(name = "data")
public Object[][] keywords() throws Exception
{
	Object[][] getExcelData = getData(excelPath, sheetName);
	return getExcelData;
}
	
public Object[][] getData (String excelPath, String sheetName) throws Exception
{
   Object[][] excelData = null;
   eu = new ExcelUtils(excelPath);
   int rows = eu.getRowCount(sheetName);
   int cols = eu.getColumnCount(sheetName);
   
   excelData = new Object[rows-1][cols];
   
   for (int i=1; i<rows; i++)
   {
	   for (int j=0; j<cols; j++)
	   {
		   excelData[i-1][j] = eu.getCellData(sheetName, j, i);
	   }
   }
   return excelData;
}


@BeforeMethod
public void setup()
{	
	System.setProperty("webdriver.chrome.driver", service);
	driver = new ChromeDriver();
  
  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
  driver.manage().deleteAllCookies();
  driver.manage().window().maximize();
  
  driver.get(baseUrl);
  System.out.println("Navigation successful");
}  

@AfterMethod
public void tearDown() {
	driver.quit();
}
}
