package mca.communication;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;


public class ExcelFileReader extends AbstractTransformer {

	@Override
	protected Object doTransform(Object src, String enc)
			throws TransformerException {
		// TODO Auto-generated method stub
		Map<Integer,String> messageMap = new HashMap<Integer,String>();
		try {
		FileInputStream inputStream = (FileInputStream)src;
		
		Workbook workbook;
		
		if (inputStream != null) {
			workbook = new XSSFWorkbook(inputStream);
			
			int numberOfSheets = workbook.getNumberOfSheets();
			//looping over each workbook sheet
	        for (int i = 0; i < numberOfSheets; i++) {
	            Sheet sheet = workbook.getSheetAt(i);
	            Iterator rowIterator = sheet.iterator();
	            int j = 0;
	            while (rowIterator.hasNext()) {
	            	if (j > 1) {
	            	Row row = (Row)rowIterator.next();
	            	Iterator cellIterator = row.cellIterator();
	            	Integer number = null;
	            	String msg= null;
	            	while (cellIterator.hasNext()) {
	            			Cell cell = (Cell)cellIterator.next();
		            		if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
		            			String cellString = cell.getStringCellValue();
		            			System.out.println("Reading a cell and value is : " + cellString);
		            			msg = cellString;
		            		} else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
		            			number = (int) cell.getNumericCellValue();
		            		}
		            		
	            		}
	            	messageMap.put(number, msg);
	            	}
	            	j++;
	            }
	        }
		}


        //Read the CSV file and add the row to the appropriate List(s)
        /*String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            //Push your data into the database through your JDBC connection
        }*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Source : " + src);
		System.out.println("Source : " + enc);
		return messageMap;
	}

}
