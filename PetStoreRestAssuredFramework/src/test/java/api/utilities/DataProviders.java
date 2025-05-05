package api.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DataProviders {
	
	
	@DataProvider(name="Data")
	public String[][] getAllData() throws IOException {
		
		String path = System.getProperty("user.dir")+"//testdata//UsersData.xlsx";
		ExcelUtility xlUtility = new ExcelUtility(path);
		
		int rownum=xlUtility.getRowCount("Sheet1");
		int colcount= xlUtility.getCellCount("Sheet1", 1);
		
		String apidata[][] = new String[rownum][colcount];
		for(int i=1; i<=rownum; i++) {
			for(int j=0; j<colcount;j++) {
				apidata[i-1][j]=xlUtility.getCellData("Sheet1", i, j);
			}
		}				
		return apidata;
		
	}
	@DataProvider(name="userName")
	public String[] getUserNames() throws IOException{
		
		String path = System.getProperty("user.dir")+"//testdata//UsersData.xlsx";
		
		ExcelUtility xlUtility = new ExcelUtility(path);
		int rownum=xlUtility.getRowCount("Sheet1");
		String[] apidata = new String[rownum];
		
		for(int i=1; i<=rownum;i++) {
			apidata[i-1]= xlUtility.getCellData("Sheet1", i, 1);
		}
				
		return apidata;
		
	}

}
