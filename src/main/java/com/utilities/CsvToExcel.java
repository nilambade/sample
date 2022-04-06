package com.utilities;
import java.io.FileOutputStream;
import java.io.FileReader;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import com.opencsv.CSVReader;
public class CsvToExcel {
	public static final char FILE_DELIMITER = ',';
    public static final String FILE_EXTN = ".xlsx";
    public static String convertCsvToExcel(String xlsLoc,String csvLoc, String name) 
	{
		SXSSFSheet sheet = null;
        CSVReader reader = null;
        Workbook workBook = null;
        String generatedXlsFilePath = "";
        FileOutputStream fileOutputStream = null;
        try {
        	String[] nextLine;
        	FileReader f = new FileReader(csvLoc);
        	reader = new CSVReader(f);
        	 workBook = new SXSSFWorkbook();
             sheet = (SXSSFSheet) workBook.createSheet("Sheet");
             int rowNum = 0;
             while((nextLine = reader.readNext()) != null) {
                 Row currentRow = sheet.createRow(rowNum++);
                 for(int i=0; i < nextLine.length; i++) {
                     if(NumberUtils.isDigits(nextLine[i])) {
                         currentRow.createCell(i).setCellValue(Integer.parseInt(nextLine[i]));
                     } else if (NumberUtils.isNumber(nextLine[i])) {
                         currentRow.createCell(i).setCellValue(Double.parseDouble(nextLine[i]));
                     } else {
                         currentRow.createCell(i).setCellValue(nextLine[i]);
                     }
                 }
             }
             generatedXlsFilePath = xlsLoc + name + FILE_EXTN;
             fileOutputStream = new FileOutputStream(generatedXlsFilePath.trim());
             workBook.write(fileOutputStream);
        }
        catch(Exception e) 
        {
        }finally 
        {
        	try 
        	{
        		workBook.close();
        		fileOutputStream.close();
        		reader.close();
        	}
        	catch(Exception ex) 
        	{
        		
        	}
        }
        return generatedXlsFilePath;
	}
}
