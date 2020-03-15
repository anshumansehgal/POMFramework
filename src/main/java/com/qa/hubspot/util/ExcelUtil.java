package com.qa.hubspot.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	public static Workbook book;
	public static Sheet sheet;
	
	public static String TESTDATA_SHEET_PATH = "/Users/anshumansehgal/eclipse-workspace/NaveenPOMSeries/src/main/java/com/qa/hubspot/testdata/HubSpotTestData.xlsx";
	
	public static Object[][] getTestData(String sheetName) {
		
		try {
			FileInputStream fis = new FileInputStream(TESTDATA_SHEET_PATH);
			try {
			book= WorkbookFactory.create(fis);  //entire excelbook
			sheet = book.getSheet(sheetName);	//get sheet like contacts
			
			Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
//2 for loops- one for rows, one for column			
			for(int i=0; i<sheet.getLastRowNum(); i++) { //rows
				
				for(int k=0; k<sheet.getRow(0).getLastCellNum(); k++) {//column
					
					data[i][k] = sheet.getRow(i+1).getCell(k).toString(); //i+1 because data will start from 2
				}
			}
			return data;	
				
				
			} catch (EncryptedDocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
