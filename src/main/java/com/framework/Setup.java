package com.framework;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.TestNG;
import org.testng.collections.Lists;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.common.BaseTest;
import com.common.Constants;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Setup {
	public static WebDriver driver;

	public Logger log = null;
	
	public void executeTest() {
		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		suites.add(Constants.TESTNG);
		testng.setTestSuites(suites);
		testng.run();
	}

	public void setUp(String browser) {
		
		String browserName = Constants.prop.getProperty("browser");
	
	      if(browser.equalsIgnoreCase("iexplore")){
	          File file = new File("");
	          System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
	          driver = new InternetExplorerDriver();

	      }
	      if(browser.equalsIgnoreCase("firefox")){
	          driver = new FirefoxDriver();
	      }
	      if(browser.equalsIgnoreCase("chrome")){
	          File file = new File("");//v22
	        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
	        driver = new ChromeDriver();
	      }
	
	}
	public static WebDriver getDriver() {
		if (driver == null) {
			if (Constants.prop.getProperty("remote").equalsIgnoreCase("true")) {
				WebDriverManager.chromedriver().setup();
				// ====== Setup download location ==========
				String downloadDir = Constants.downloadedfilepath;
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("download.default_directory", downloadDir);
				chromePrefs.put("download.prompt_for_download", false);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("--headless");
				options.addArguments("--disable-gpu");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--no-sandbox");
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(cap);
				// ====== Setup download location END==========
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			} else if (Constants.prop.getProperty("browser").equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				// ====== Setup download location ==========
				String downloadDir = Constants.downloadedfilepath;
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("download.default_directory", downloadDir);
				chromePrefs.put("download.prompt_for_download", false);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", chromePrefs);
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(cap);
				// ====== Setup download location END==========
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			}
			else if (Constants.prop.getProperty("browser").equalsIgnoreCase("firefox"))
			{
				WebDriverManager.firefoxdriver().setup();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			}
		}
		return driver;
	}

	public void getConfigFromS3() {
		if (Constants.prop.getProperty("remote").equalsIgnoreCase("true")) {
			final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
			final S3Object object = s3Client.getObject("automation-execution-reports", "Automation.xlsx");
			String localfileName = System.getProperty("user.dir")
					+ Constants.prop.getProperty("automation.details.location.s3");
			final File localFile = new File(localfileName);
			try {
				FileUtils.copyToFile(object.getObjectContent(), localFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
