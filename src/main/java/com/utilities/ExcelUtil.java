package com.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.common.Constants;
import com.framework.Logs;

/*
 * 
 */

public class ExcelUtil {

	public static Workbook wb;
	public static Sheet xs;
	public static Row row;
	public static Cell cell;

	public static List<String> getWorkSheets(String fileName) {
		List<String> list = null;
		FileInputStream fis = null;
		try {
			// Logs.logger.info("Starting Reading file: " + fileName);
			fis = new FileInputStream(fileName);
			Workbook workbook = getWorkbookObj(fis, fileName);
			int numberOfSheets = workbook.getNumberOfSheets();
			list = new ArrayList<String>();
			for (int i = 0; i < numberOfSheets; i++) {
				list.add(workbook.getSheetAt(i).getSheetName());
			}
			fis.close();
			// Logs.logger.info("List of sheets : " + list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Gets the data header.
	 *
	 * @param fileName
	 *            the file name
	 * @param sheetName
	 *            the sheet name
	 * @return the data header
	 */
	public static List<String> getDataHeader(String fileName, String sheetName) {
		List<String> header = null;
		FileInputStream fis = null;
		try {
			// Logs.logger.info("Starting Reading file for header : " + fileName);
			fis = new FileInputStream(fileName);
			Workbook workbook = getWorkbookObj(fis, fileName);
			Sheet sheet = workbook.getSheet(sheetName);
			header = new ArrayList<String>();
			Row row = sheet.getRow(0);
			if (null != row) {
				for (int j = 0; j < row.getLastCellNum(); j++) {
					header.add(getCellDataToString(row.getCell(j)));
				}
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Logs.logger.info("Header list : " + header);
		return header;
	}

	/**
	 * Gets the entire data.
	 *
	 * @return the entire data
	 */
	public static String[][] getEntireData(String fileName, String sheetName) {
		Logs.log.info("Starting Reading file: " + fileName);
		String[][] data = null;
		Workbook workbook = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			workbook = getWorkbookObj(fis, fileName);
			Sheet sheet = workbook.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			int colCount = sheet.getRow(0).getLastCellNum();
			data = new String[rowCount][colCount + 1];
			Logs.log.info("Total # of Data rows: " + rowCount);
			for (int i = 1, x = 0; i <= rowCount; i++, x++) {
				Row row = sheet.getRow(i);

				if (null != row) {
					for (int j = 0; j < colCount + 1; j++) {
						if (j == 0) {
							data[x][j] = row.getRowNum() + 1 + "";
						} else {
							data[x][j] = getCellDataToString(row.getCell(j - 1,
									org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
						}
					}
				}
			}
			fis.close();
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("ISSUE heree");

		}
		System.out.println(data);
		return data;
		
	}

	/**
	 * Gets the entire data.
	 *
	 * @return the entire data
	 */
	public static String[][] getEntireData() {
		// Logs.logger.info("Starting Reading file: " + Constants.DATAFILE);
		String[][] data = null;
		Workbook workbook = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(Constants.DATAFILE);
			workbook = getWorkbookObj(fis, Constants.DATAFILE);
			Sheet sheet = workbook.getSheet(Constants.DATASHEET);
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			int colCount = sheet.getRow(0).getLastCellNum();
			data = new String[rowCount][colCount + 1];
			// Logs.logger.info("Total # of Data rows: " + rowCount);
			for (int i = 1, x = 0; i <= rowCount; i++, x++) {
				Row row = sheet.getRow(i);
				if (null != row) {
					for (int j = 0; j < colCount + 1; j++) {
						if (j == 0) {
							data[x][j] = row.getRowNum() + 1 + "";
						} else {
							data[x][j] = getCellDataToString(row.getCell(j - 1,
									org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
						}
					}
				}
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Gets the valid test data.
	 *
	 * @param data
	 *            the data
	 * @return the valid test data
	 */
	public static String[][] getValidTestData(String[][] data) {
		// Logs.logger.info("Reading excel data rows for tests ...");
		int row = 0;
		String[][] tmp = null;
		try {
			for (int x = 0; x < data.length; x++) {
				if ((null != data[x][1]) && (null != data[x][2])) {
					if (data[x][1].equalsIgnoreCase("yes") || data[x][1].equalsIgnoreCase("y")) {
						row++;
					}
				}
			}
			// Logs.logger.info("Total # of valid Test Rows: " + row);
			tmp = new String[row][data[0].length];
			int i = 0;
			for (int x = 0; x < data.length; x++) {
				if (null != data[x][1]) {
					if (data[x][1].equalsIgnoreCase("yes") || data[x][1].equalsIgnoreCase("y")) {
						for (int y = 0; y < data[0].length; y++) {
							tmp[i][y] = data[x][y];
						}
						i++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(tmp);
		return tmp;
	}

	// Get the object based on the excel files
	public static Workbook getWorkbookObj(FileInputStream fis, String fileName) {
		try {
			String fileExtensionName = fileName.substring(fileName.indexOf("."));
			if (fileExtensionName.equalsIgnoreCase(".xlsx")) {
				return WorkbookFactory.create(fis);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the cell data to string.
	 *
	 * @param cell
	 *            the cell
	 * @return the cell data to string
	 */
	public static String getCellDataToString(Cell cell) {
		if (null != cell) {
			switch (cell.getCellType()) {
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue()).trim();

			case STRING:
				return cell.getStringCellValue().trim();

			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					if (cell.getDateCellValue().toString() == "") {
						return "";
					} else {
						return cell.toString() + "";
					}
				} else {
					Double doubleValue = cell.getNumericCellValue();
					DecimalFormat format = new DecimalFormat("#.##");
					format.setDecimalSeparatorAlwaysShown(false);
					return new String(format.format(doubleValue).toString().trim());
				}
			default:
				return "";
			}
		} else {
			return "";
		}
	}

	/*
	 * public static void readExcel(String f, String sheetname) throws IOException {
	 * 
	 * File excelfile= new File (f);
	 * 
	 * FileInputStream fis = new FileInputStream(excelfile); wb = new
	 * XSSFWorkbook(fis); xs = wb.getSheet(sheetname); } public static void
	 * saveExcel(File pqr, String sheetname) throws IOException { FileOutputStream
	 * fos = new FileOutputStream(pqr); xs = wb.getSheet(sheetname); wb.write(fos);
	 * wb.close(); }
	 */

	public static String ReadExcelColumn(String f, String sheetname, String colName, String data[][])
			throws IOException {

		FileInputStream fis = null;
		Workbook workbook = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		fis = new FileInputStream(Constants.DATAFILE);
		workbook = (Workbook) getWorkbookObj(fis, Constants.DATAFILE);
		sheet = workbook.getSheet(sheetname);
		try {
			int col_Num = -1;
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
				System.out.println("Col Num" + col_Num);
			}

			row = sheet.getRow(1);
			int i = 0;
			for (int x = 0; x < data.length; x++) {
				if (null != data[x][3]) {
					row = sheet.getRow(i);

					cell = row.getCell(col_Num);

					System.out.println("cell" + cell);
				}

			}
			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
				String cellValue = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					DateFormat df = new SimpleDateFormat("dd/MM/yy");
					Date date = cell.getDateCellValue();
					cellValue = df.format(date);
				}
				return cellValue;
			} else if (cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "rowl";
		}

	}

	public static void ReadexcleData(String f, String sheetname, String RowName) {

		FileInputStream fis = null;
		Workbook workbook = null;
		Sheet sheet = null;
		Row row = null;
		try {
			fis = new FileInputStream(Constants.DATAFILE);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		workbook = (Workbook) getWorkbookObj(fis, Constants.DATAFILE);
		sheet = workbook.getSheet(sheetname);
		try {
			int row_Num = -1;
			row = sheet.getRow(0);

			// These for loop for getting particular row
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(RowName.trim()))
					row_Num = i;

				for (i = 1; i <= row.getLastCellNum(); i++)

				{
					Row row1 = sheet.getRow(row_Num);
					Cell cell2 = row1.createCell(i);
					cell2.getStringCellValue();
				}

				row = sheet.getRow(1);
			}
		} catch (Exception e) {

		}

	}

	@SuppressWarnings("deprecation")
	public static void updateExcelData(String fileName, String sheetName, String content, int row, int col) {
		// Logs.logger.info("Writing data to cell: " + row + "," + col);
		Workbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		Cell cell = null;
		try {
			fis = new FileInputStream(fileName);
			workbook = getWorkbookObj(fis, fileName);
			Sheet sheet = workbook.getSheet(sheetName);
			cell = sheet.getRow(row).getCell(col);
			if (null == cell) {
				cell = sheet.getRow(row).createCell(col);
				cell.setCellType(CellType.STRING);
			}
			cell.removeCellComment();
			cell.setCellValue(content);
			fis.close();
			fos = new FileOutputStream(fileName);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// update the excel file based on 2d array
	/**
	 * Update excel data.
	 *
	 * @param data
	 *            the data
	 * @param fileName
	 *            the file name
	 * @param sheetName
	 *            the sheet name
	 */
	public static void updateExcelData(String[][] data, String fileName, String sheetName) {
		// Logs.logger.info("Writing data ....");
		Workbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		Cell cell = null;
		Row row = null;
		try {

			for (int i = 0; i < data.length; i++) {
				fis = new FileInputStream(fileName);
				workbook = getWorkbookObj(fis, fileName);
				Sheet sheet = workbook.getSheet(sheetName);
				int rowNumber = i; // sheet.getLastRowNum()+1 ;
									// Integer.parseInt(data[i][0]);
				row = sheet.getRow(rowNumber);
				if (null == row) {
					row = sheet.createRow(rowNumber);
				}
				for (int j = 0; j < data[i].length; j++) {

					cell = sheet.getRow(rowNumber).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					if (null == cell) {
						cell = sheet.getRow(rowNumber).createCell(j);
					}
					cell.removeCellComment();
					cell.setCellValue(data[i][j]);
				}
				fis.close();
				fos = new FileOutputStream(fileName);
				workbook.write(fos);
				fos.close();

			}

			System.out.print(fileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Create new excel work sheet
	/**
	 * Creates the work sheet.
	 *
	 * @param fileName
	 *            the file name
	 * @param sheetName
	 *            the sheet name
	 */
	public static void createWorkSheet(String fileName, String sheetName) {
		Workbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		boolean found = false;
		try {
			fis = new FileInputStream(fileName);
			workbook = getWorkbookObj(fis, fileName);

			// First check is sheet exists ?
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				if (workbook.getSheetAt(i).getSheetName().equalsIgnoreCase(sheetName)) {
					found = true;
					break;
				}
			}
			fis.close();
			if (!found) {
				workbook.createSheet(sheetName);
				fos = new FileOutputStream(fileName);
				workbook.write(fos);
				fos.close();
			} else {
				// Logs.logger.info("Sheet already present.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void updateExcelDataColor(String fileName, String sheetName, String content, int row, int col,
			String color) {
		// Logs.logger.info("Writing data to cell: " + row + "," + col);
		Workbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		Cell cell = null;
		try {
			fis = new FileInputStream(fileName);
			workbook = getWorkbookObj(fis, fileName);
			Sheet sheet = workbook.getSheet(sheetName);
			sheet.autoSizeColumn(col);
			if (null == sheet.getRow(row)) {
				sheet.createRow(row);
			}
			cell = sheet.getRow(row).getCell(col);
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			if (color.equalsIgnoreCase("Pass")) {
				font.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
			} else if (color.equalsIgnoreCase("Fail")) {
				font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
			} else if (color.equalsIgnoreCase("Title")) {
				font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
				font.setBold(true);
			} else {
				font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
			}
			style.setFont(font);
			if (null == cell) {
				cell = sheet.getRow(row).createCell(col);
				cell.setCellType(CellType.STRING);
			}
			cell.removeCellComment();
			cell.setCellValue(content);
			cell.setCellStyle(style);
			fis.close();
			fos = new FileOutputStream(fileName);
			workbook.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copyExcelFile(String inputXLFilename, String outputXLFilename) {
		Workbook book1 = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;

		// XSSFWorkbook book1 = null;

		try {
			fis = new FileInputStream(inputXLFilename);
			book1 = getWorkbookObj(fis, inputXLFilename);

			// book1 = new XSSFWorkbook(new FileInputStream("Enrollment.xlsx"));

			for (int i = 4; i > 0; i--) {
				book1.removeSheetAt(i);
			}

			fos = new FileOutputStream(outputXLFilename, false);
			book1.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void setData(String sheetname, int row, int cell, String content, String file)
			throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("./Import_Export/" + file + ".xlsx");
		wb = WorkbookFactory.create(fis);
		wb.getSheet(sheetname).getRow(row).getCell(cell).setCellValue(content);
		FileOutputStream fos = new FileOutputStream("./Import_Export/" + file + ".xlsx");
		wb.write(fos);
		wb.close();
	}

	public static String getData(String sheetname, int row, int cell, String file)
			throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("./Import_Export/" + file + ".xlsx");
		Workbook w = WorkbookFactory.create(fis);
		Cell cellValue = w.getSheet(sheetname).getRow(row).getCell(cell);
		DataFormatter fmt = new DataFormatter();
		String valueAsSeenInExcel = fmt.formatCellValue(cellValue);
		return valueAsSeenInExcel;
	}

	public static ArrayList getColumnNames(String sheet, String file) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("./Import_Export/" + file + ".xlsx");
		wb = WorkbookFactory.create(fis);
		Sheet sheetName = wb.getSheet(sheet);
		Iterator<Row> i = sheetName.iterator();
		Row firstRow = i.next();
		Iterator<Cell> ce = firstRow.cellIterator();
		ArrayList a = new ArrayList();
		while (ce.hasNext()) {
			Cell data = ce.next();
			a.add(data);
		}
		return a;
	}

	public static HashMap<String, List<String>> getDatasFromCSVFile(String sheetName, String file)
			throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("./Import_Export/" + file + ".xlsx");
		Workbook w = WorkbookFactory.create(fis);
		int count = 0;
		HashMap<String, List<String>> h = new LinkedHashMap();
		// Set skuSet= new LinkedHashSet();
		List skuList1 = new ArrayList();
		List skuList2 = new ArrayList();
		List nameList1 = new ArrayList();
		List nameList2 = new ArrayList();
		Sheet sheet = w.getSheet(sheetName);
		Iterator<Row> r = sheet.iterator();
		while (r.hasNext()) {
			Row rows = r.next();
			
		//	String data = rows.getCell(0).getStringCellValue();
			
			DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
			 Cell cell =  rows.getCell(0);
			 String data = formatter.formatCellValue(cell); 
			
			 Cell cell1 =  rows.getCell(1);
			 String data2 = formatter.formatCellValue(cell1); 
			
		//	String data2 = rows.getCell(1).getStringCellValue();
			
			if (!skuList1.contains(data))  {
				skuList1.add(data);
				count++;
			}
			if (!nameList1.contains(data2)) {
				nameList1.add(data2);
			}
			if (count == 8) {
				break;
			}
		}
		for (int i = 1; i <= 7; i++) {
			skuList2.add(skuList1.get(i));
			nameList2.add(nameList1.get(i));
		}
		h.put("SKU", skuList2);
		h.put("name", nameList2);
		return h;
	}
	
	
	 
	
}
