package com.common;

import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser {

	
	private static WebDriver driver;
	
	
	public Browser() {
		
	}
	
	public static WebDriver getInstance() {
		if(driver == null) {
			WebDriverManager.chromedriver().setup();
		}else {
			return driver;
		}
		return driver;
	}
	
	private void setup() {
		
	}
}
