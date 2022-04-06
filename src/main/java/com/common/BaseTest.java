package com.common;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.framework.Setup;

public class BaseTest {

	public WebDriver driver;
	public BasePage page;
	public static Properties prop;

	@BeforeClass
	public void beforeClass(){
		Constants.initConfig();
		prop=Constants.prop;
		driver = Setup.getDriver();
		page = new BasePage(driver);
		page.log.info("============ Starting Test ========== ");
	}

	@AfterClass
	public void afterClass() {
		page.log.info("============ Test Finish ========== ");
//		driver.close();
//		driver.quit();
	}
	@AfterSuite
	public void afterSuite()
	{
		driver.close();
		driver.quit();
	}

	public static Properties getPropertisData() {
		FileReader reader = null;
		try {
			reader = new FileReader(Constants.CONFIGPROPERTIES);
			prop = new Properties();
			prop.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	public static String[] getColumnsFromMatrix() throws IOException 
	{
		FileInputStream fis = new FileInputStream("./" + Constants.CONFIGPROPERTIES);
		Properties p = new Properties();
		p.load(fis);
		String[] columnsName = prop.getProperty("columnNames").split(",");
		return columnsName;
	}
	public String waitUntilDonwloadCompleted(WebDriver driver) {
		// Store the current window handle
		String mainWindow = driver.getWindowHandle();

		// open a new tab
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.open()");
		// switch to new tab
		// Switch to new window opened
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		// navigate to chrome downloads
		driver.get("chrome://downloads");

		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		// wait until the file is downloaded
		Long percentage = (long) 0;
		while (percentage != 100) {
			try {
				percentage = (Long) js1.executeScript(
						"return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('#progress').value");
				System.out.println(percentage);

			} catch (Exception e) {
				System.out.println(percentage);

			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		// get the latest downloaded file name
		String fileName = (String) js1.executeScript(
				"return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
		// get the latest downloaded file url
		String sourceURL = (String) js1.executeScript(
				"return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').href");
		// file downloaded location
		String donwloadedAt = (String) js1.executeScript(
				"return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div.is-active.focus-row-active #file-icon-wrapper img').src");
		System.out.println("Download deatils");
		System.out.println("File Name :-" + fileName);
		System.out.println("Donwloaded path :- " + donwloadedAt);
		System.out.println("Downloaded from url :- " + sourceURL);
		// print the details
		System.out.println(fileName);
		System.out.println(sourceURL);
		// close the downloads tab2
		driver.close();
		// switch back to main window
		driver.switchTo().window(mainWindow);
		return fileName;
	}
}
