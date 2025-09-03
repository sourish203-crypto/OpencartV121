package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders 
{
	//DataProvider 1
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx"; //taking excel file from testData
		ExcelUtility xlutil= new ExcelUtility(path); //creating an object for ExcelUtility
		int totalRows= xlutil.getRowCount("Sheet1");
		int totalCols= xlutil.getCellCount("Sheet1", 1);
		
		String loginData[][]= new String[totalRows][totalCols]; //created 2-D array which can store the data user and password
		for(int i=1; i<=totalRows;i++) //i=1 because zero row is header part
		{
			for(int j=0; j<totalCols; j++)
			{
				loginData[i-1][j]= xlutil.getCellData("Sheet1", i, j);
			}
		}
		
		return loginData; //returning two dimension array
	}
	
	//DataProvider 2
	//In future if we need more data providers for different test cases
	
	//DataProvider 3
	
	//DataProvider 4

}
