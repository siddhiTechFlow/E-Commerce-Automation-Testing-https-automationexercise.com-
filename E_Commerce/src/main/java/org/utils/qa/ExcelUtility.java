package org.utils.qa;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

	 Workbook book;
	 Sheet sheet;

	public Object[][] getExcelData(String fileName, String sheetName) throws EncryptedDocumentException, IOException {

		String path = System.getProperty("user.dir") + "/Test Data/" + fileName;

		FileInputStream fis = new FileInputStream(path);
		book = WorkbookFactory.create(fis);

		sheet = book.getSheet(sheetName);

		if (sheet == null) {
			throw new RuntimeException("Sheet not found: " + sheetName);
		}

		int totalRows = sheet.getLastRowNum(); 
		int totalCols = sheet.getRow(0).getLastCellNum();

		Object[][] obj = new Object[totalRows][totalCols];

		for (int i = 0; i < totalRows; i++) {
		    for (int j = 0; j < totalCols; j++) {
		        obj[i][j] = sheet.getRow(i + 1).getCell(j).toString();
		    }
		}

		return obj;

	}

}
