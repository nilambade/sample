package com.common;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Constants {

	public static final String TESTNG = "testng.xml";
	public static String CLIENT = "Spentra";
	public static String APPURL = "";
	public static String DATAFILE = "";
	public static String DATASHEET = "";
	public static String REPORTPATH = "/Reports/";
	public static String ReportFileName = "";
	public static String LOG4J = "src/main/resources/log4j.properties";
	public static String CONFIGPROPERTIES = "src/main/resources/Global.properties";
	public static long DEFAULTWAIT = 30;
	public static String DATAFILEFORAPI = "";
	public static Properties prop;
	public static String totalTestCasesCount;
	public static String passedTestCasesCount;
	public static String failedTestCasesCount;
	public static String skippedTestCasesCount;
	public static String downloadedfilepath;
	public static String automationDetailsRemote;
	public static String automationDetails;
	public static String URL; 
	public static String baseURI;
	public static String inviteLink;
	public static String importExportDatas;
	public static String regEmail;
	public static String regPass;
	public static String EmployeeDetails;
	public static String EmployeeDetailsInfo;
	
	
	public static void initConfig() {
		prop= BaseTest.getPropertisData();
		downloadedfilepath=System.getProperty("user.dir")+File.separator+"Import_Export";
		automationDetails=System.getProperty("user.dir")+File.separator+"Data"+File.separator+"Automation.xlsx";
		automationDetailsRemote = System.getProperty("user.dir")+File.separator+"Data"+File.separator+"Automation.xlsx";
		EmployeeDetails=System.getProperty("user.dir")+File.separator+"data"+File.separator+"EmpInfo.xlsx";
		//EmployeeDetailsInfo=System.getProperty("user.dir")+File.separator+"data"+File.separator+"EmpDetails.xlsx";
		
		URL = prop.getProperty("URL");
		baseURI = prop.getProperty("method")+prop.getProperty("client")+".ribbon"+prop.getProperty("baseURI");
		inviteLink =  prop.getProperty("method")+prop.getProperty("client")+".ribbon"+ prop.getProperty("inviteLink");
		
		importExportDatas= System.getProperty("user.dir") + File.separator + "Data" + File.separator
				+ "EmpInfo.xlsx";
		//importExportDatas= System.getProperty("user.dir") + File.separator + "Data" + File.separator
				//+ "EmpInfo.xlsx";
	
	
		
		regEmail = prop.getProperty("email");
		regPass = prop.getProperty("password");
	
		
		
		
	}
}
