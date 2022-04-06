
package com.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Reporter {

	private static ExtentHtmlReporter reporter;
	private static ExtentReports report;
	private static ExtentTest extentTest;

	public Reporter() {
		reporter = new ExtentHtmlReporter(getReportName());
		reporter.config().setEncoding("utf-8");
		reporter.config().setDocumentTitle("AutomationReport");
		reporter.config().setReportName(getReportName());
		report = new ExtentReports();
		report.attachReporter(reporter);
	}

	public void createReport(String testName) {
		extentTest = report.createTest(testName);
	}

	public static void pass(String description) {
		extentTest.pass(description);
	}

	public static void info(String description) {
		extentTest.info(description);
	}

	public static void error(String description) {
		extentTest.error(description);
	}

	public static void fail(String description) {
		extentTest.fail(description);
	}

	public static void skip(String description) {
		extentTest.skip(description);
	}

	public void export() {
		report.flush();
	}

	public static void fail(String details, MediaEntityModelProvider mediaEntityModelProvider) {
		extentTest.fail(details, mediaEntityModelProvider);

	}

	public String getReportName() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		Constants.ReportFileName = Constants.CLIENT + "_" + dateFormat.format(date) + ".html";
		return System.getProperty("user.dir") + Constants.REPORTPATH + Constants.ReportFileName;
	}
}
