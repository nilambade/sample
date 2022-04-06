package com.framework;

import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.common.Constants;

public class Start {
	// public static WebDriver driver;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Constants.prop = getPropertisData();
			Constants.CLIENT = Constants.prop.getProperty("client");
			new Logs();
			Setup st = new Setup();
			Setup.getDriver();
			st.executeTest();
		} catch (Exception e) {
			System.out.println("Exception during executions.Error: " + e.getMessage());
		} finally {
			teardown();
			System.out.println("============ Automation Tests Finished ================");
		}
	}
	private static void teardown() {
		Setup.driver.close();
		Setup.driver.quit();
	}

	public static Properties getPropertisData() {
		Properties prop = null;
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
}
