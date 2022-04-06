package com.common;

import java.util.ArrayList;
import java.util.List;

import com.utilities.CsvToExcel;
import com.utilities.ExcelUtil;

public class DataProvider {

	public void getData() {
		ExcelUtil.ReadexcleData(Constants.DATAFILE, Constants.DATASHEET, "1");
	}


	@org.testng.annotations.DataProvider(name = "EmployeeInfo")
	public static Object[][] getBuyerData() {
		return ExcelUtil.getValidTestData(ExcelUtil.getEntireData(Constants.EmployeeDetails, "negativeLogin"));
	}

	@org.testng.annotations.DataProvider(name = "EmployeeInformation")
	public static Object[][] getEmpData() {
		return ExcelUtil.getValidTestData(ExcelUtil.getEntireData(Constants.EmployeeDetails, "EmployeeDetails"));
	}
	@org.testng.annotations.DataProvider(name = "EmployeeID")
	public static Object[][] getEmpID() {
		return ExcelUtil.getValidTestData(ExcelUtil.getEntireData(Constants.EmployeeDetails, "EmployeeId"));
	}
	
//	@org.testng.annotations.DataProvider(name = "LoginData")
//	public static Object[][] getLoginData() {
//		String dataFile = "";
//		if (Constants.prop.getProperty("remote").equalsIgnoreCase("true")) {
//			dataFile = System.getProperty("user.dir") + Constants.automationDetailsRemote;
//		} else {
//			dataFile = System.getProperty("user.dir") + Constants.automationDetails;
//		}
//		return ExcelUtil.getValidTestData(ExcelUtil.getEntireData(dataFile, "LoginData"));
//	}
//
//	@org.testng.annotations.DataProvider(name = "VendorData")
//	public static Object[][] getVendorDetails() {
//		String dataFile = "";
//		if (Constants.prop.getProperty("remote").equalsIgnoreCase("true")) {
//			dataFile = Constants.automationDetailsRemote;
//		} else {
//			dataFile = Constants.automationDetails;
//		}
//		return ExcelUtil.getValidTestData(ExcelUtil.getEntireData(dataFile, "VendorData"));
//	}


}
