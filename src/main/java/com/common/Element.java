package com.common;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.framework.Logs;
import com.utilities.WaitUtility;



public class Element {


	public static void click(WebDriver driver, WebElement element, String elementName) {
		try {
		//	WaitUtility.waitForElementVisible(driver, element);
			WaitUtility.waitForElementClickable(driver, element);
		//	WaitUtility.waitForSeconds(2);
			element.click();
		} catch (Exception ex) {
			Reporter.info("FAIL:- " + elementName + " is not Clickable");
			Logs.log.info(elementName + " is not Clickable");
		}
	}

	public static void clickAction(WebDriver driver, WebElement element, String elementName) {
		try {
			WaitUtility.waitForElementVisible(driver, element);
			WaitUtility.waitForElementClickable(driver, element);
			WaitUtility.waitForSeconds(2);
			//element.click();
			Actions builder = new Actions(driver);
			builder.moveToElement(element).click(element);
			builder.perform();
		} catch (Exception ex) {
			Reporter.info("FAIL:- " + elementName + " is not Clickable");
			Logs.log.info(elementName + " is not Clickable");
		}
	}
	
	public static boolean isVisible(WebDriver driver, WebElement element, String elementName) {
		try {
			WaitUtility.waitForElementVisible(driver, element);
		//	WaitUtility.waitForSeconds(2);
			if (element.isDisplayed()) {
				return true;
			}
		} catch (Exception ex) {
			Logs.log.info(elementName + " is not Displayed");
			return false;
		}
		return false;
	}

	public static void clearEnterText(WebDriver driver, WebElement element, String text, String elementName) {
		try {
			WaitUtility.waitForElementVisible(driver, element);
		//	WaitUtility.waitForSeconds(2);
			if (element.isEnabled()) {
				element.clear();
				element.sendKeys(text.trim());
			}
		} catch (Exception ex) {
			Logs.log.info(elementName + " is not Enabled");
			Reporter.info("FAIL:- " + elementName + " is not Enabled");
		}
	}

	public static void enterText(WebDriver driver, WebElement element, String text, String elementName) {
		try {
			WaitUtility.waitForElementVisible(driver, element);
			WaitUtility.waitForSeconds(2);
		 
				element.sendKeys(text);
			
		} catch (Exception ex) {
			
		}
	}

	public static void enterText(WebDriver driver, WebElement element, Keys key, String elementName) {
		try {
			WaitUtility.waitForElementVisible(driver, element);
			if (element.isEnabled()) {
				element.sendKeys(key);
			}
		} catch (Exception ex) {
			Logs.log.info(elementName + " is not Enabled");
			Reporter.info("FAIL:- " + elementName + " is not Enabled");
		}
	}

	public static boolean isEnable(WebDriver driver, WebElement element, String elementName) {
		try {
			WaitUtility.waitForElementVisible(driver, element);
			if (element.isEnabled()) {
				return true;
			}
		} catch (Exception ex) {
			Reporter.info("FAIL:- " + elementName + " is not Enabled");
			Logs.log.info(elementName + " is not Enabled");
			return false;
		}
		return false;
	}
	public static void reporter(String statement) {
		Reporter.info(statement);
		Logs.log.info(statement);
	}
}

