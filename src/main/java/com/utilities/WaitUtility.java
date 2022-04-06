package com.utilities;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class WaitUtility {
	
	public WebDriver driver;
	
	public WaitUtility(WebDriver driver) {
		this.driver = driver;
	}
	
	
	  public static void waitForPageLoaded(WebDriver driver) {
	      ExpectedCondition<Boolean> expectation = new
	              ExpectedCondition<Boolean>() {
	                  public Boolean apply(WebDriver driver) {
	                      return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
	                  }
	              };
	      try {
	          Thread.sleep(1000);
	          WebDriverWait wait = new WebDriverWait(driver, 30);
	          wait.until(expectation);
	      } catch (Throwable error) {
	          Assert.fail("Timeout waiting for Page Load Request to complete.");
	      }
	  }

		public static Boolean waitForElementToDisappear(WebDriver driver, By xpath) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				return wait.until(ExpectedConditions.invisibilityOfElementLocated(xpath));
			} catch (Exception ex) {
				System.out.println("Warning: " + ex.getMessage());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Logs.logger.info("Warning: " + ex.getMessage());
			}
			return false;
		}
		
		public static Boolean waitForElementToDisappear(WebDriver driver, WebElement e) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 20);
				return wait.until(ExpectedConditions.invisibilityOf(e));
			} catch (Exception ex) {
				//Logs.logger.info("Warning: " + ex.getMessage());
			}
			return false;
		}
		
		public static void waitForElementsVisible(WebDriver driver, WebElement e) {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(e));
			//wait.ignoring(Exception.class).until(ExpectedConditions.visibilityOfAllElements(e));
		}
		
		public static boolean checkIfElementVisibleInstant(WebDriver driver, WebElement el) {
			boolean flag = false;
			try {
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				if (el.isDisplayed()) {
					flag = true;
				}
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			} catch (NoSuchElementException e) {
				//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			}
			return flag;
		}
		
		public static void waitForElementLoad(WebDriver driver,String email) {
			WebElement w = driver.findElement(By.xpath("//td[text()='"+email+"']"));
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.textToBePresentInElement(w, ""));
		}

		public static void waitForElement() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
		}
		public static void waits(){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		public static void waitForElementLoad(WebDriver driver)
		{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
		}
		
		public static void click(WebDriver driver, WebElement element, String elementName) {
			try {
				waitForElementVisible(driver, element);
				waitForElementClickable(driver, element);
				element.click();
			} catch (Exception ex) {
				Reporter.log("FAIL:- " + elementName + " is not Clickable");
				
			}
		}
		
		/**
		 * Waits for a given element to be visible
		 * 
		 * @param driver WebDriver instance
		 * @param e      element to wait for
		 * @return
		 */
		public static WebElement waitForElementVisible(WebDriver driver, WebElement e) {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			return wait.until(ExpectedConditions.visibilityOf(e));
		}

		public static Boolean waitForElementToDisappera(WebDriver driver, WebElement e) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 60);
				return wait.until(ExpectedConditions.invisibilityOf(e));
			} catch (Exception ex) {
				
			}
			return false;
		}

		public static Boolean waitForElementToDisappera(WebDriver driver, By xpath) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				return wait.until(ExpectedConditions.invisibilityOfElementLocated(xpath));
			} catch (Exception ex) {
				
			}
			return false;
		}

		/**
		 * Waits for a given element to be visible
		 * 
		 * @param driver WebDriver instance
		 * @param e      element to wait for
		 */
		public static boolean checkIfElementVisibleInstant1(WebDriver driver, WebElement el) {
			boolean flag = false;
			try {
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
				if (el.isDisplayed()) {
					flag = true;
				}
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			} catch (NoSuchElementException e) {
				//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			}
			return flag;
		}

		/**
		 * Waits for a given elements to be visible
		 * 
		 * @param driver WebDriver instance
		 * @param e      element to wait for
		 */
		public static void waitForElementsVisible(WebDriver driver, List<WebElement> e) {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfAllElements(e));
		}

		/**
		 * Waits for a given element to be selected
		 * 
		 * @param driver WebDriver instance
		 * @param e      element to wait for
		 */
		public static void waitForElementSelected(WebDriver driver, WebElement e) {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeSelected(e));
		}

		/**
		 * Waits for given element to be clickable
		 * 
		 * @param driver WebDriver instance
		 * @param e      element to wait for
		 */
		public static WebElement waitForElementClickable(WebDriver driver, WebElement e) {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			return wait.until(ExpectedConditions.elementToBeClickable(e));
		}

		/**
		 * Waits for the page to have a given title
		 * 
		 * This method is an attempt to address a problem where drivers are trying to
		 * check the page title on page load before the title has changed to that of the
		 * new page.
		 * 
		 * @param driver WebDriver instance
		 * @param title  title the page should have
		 */
		public static boolean waitForPageTitle(WebDriver driver, String title) {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			return wait.until(ExpectedConditions.titleContains(title));
		}

		/**
		 * waiting for seconds
		 * 
		 * @param timeoutInSeconds timeout in seconds for wait
		 */
		public static void waitForSeconds(int timeoutInSeconds) {
			try {
				Thread.sleep(timeoutInSeconds * 1000);
			} catch (InterruptedException e) {
				
			}
		}
}
