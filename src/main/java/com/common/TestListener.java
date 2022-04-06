
package com.common;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;

public class TestListener implements ITestListener {
	Reporter extent;

	public void onStart(ITestContext context) {
		System.out.println("onStart");
		extent = new Reporter();
	}

	public void onTestStart(ITestResult result) {
		System.out.println("onTestStart");
		extent.createReport(result.getMethod().getMethodName());
	}

	public void onFinish(ITestContext context) {
		ITestNGMethod[] totalTestCasesCount = context.getAllTestMethods();
		Constants.totalTestCasesCount = String.valueOf(totalTestCasesCount.length);

		Set<ITestResult> passedTestCasesCount = context.getPassedTests().getAllResults();
		Constants.passedTestCasesCount = String.valueOf(passedTestCasesCount.size());

		Set<ITestResult> failedTestCasesCount = context.getFailedTests().getAllResults();
		Constants.failedTestCasesCount = String.valueOf(failedTestCasesCount.size());

		Set<ITestResult> skippedTests = context.getSkippedTests().getAllResults();
		Constants.skippedTestCasesCount = String.valueOf(skippedTests.size());
		extent.export();
	}

	public void onTestSuccess(ITestResult result) {
		// extent.pass("PASS==");
		// extent.export();
	}

	public void onTestFailure(ITestResult result) {
		WebDriver driver = ((BaseTest) result.getInstance()).driver;
		String methodName = result.getMethod().getMethodName();
		if (result.getStatus() == ITestResult.FAILURE) {
			String expMsg = Arrays.toString(result.getThrowable().getStackTrace());

			Reporter.fail(
					"<details><summary><b><font color=red>Exception Occured, Click to see details:</font></b></summary>"
							+ expMsg.replaceAll(",", "<br>") + "</details> \n");

			String path = takeScreenshot(methodName, driver);

			try {
				Reporter.fail("<b><font color=red> Screenshot of failure</font></b>",
						MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			} catch (Exception ex) {
				Reporter.fail("Test Failed, unable to attach screenshot.");
			}
		}
	}

	public void onTestSkipped(ITestResult result) {
		Reporter.skip("This Test is skiped.");

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public String getScreenshotName(String methodName) {
		Date d = new Date();
		String fileName = methodName + "_" + d.toString().replace(":", "_").replace(" ", "_") + ".png";
		return fileName;
	}

	public String takeScreenshot(String methodName, WebDriver driver) {
		String fileName = getScreenshotName(methodName);
		String directory = System.getProperty("user.dir") + "/screenshots/";
		new File(directory).mkdirs();
		String path = directory + fileName;
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path));
		} catch (Exception e) {

		}
		return path;
	}

}
