package com.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.common.Element;
import com.utilities.WaitUtility;

public class TodolistactionsPage {

	public WebDriver driver;

	public TodolistactionsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@class='new-todo ng-valid ng-touched ng-dirty']")
	private WebElement todolisttextbar;
	
	@FindBy(xpath = "//header[@class='header']")
	private WebElement header;
	
	@FindBy(xpath = "//span//strong[text()='1']")
	private WebElement addlist;
	
	@FindBy(xpath = "//input[@class='toggle ng-valid ng-dirty ng-valid-parse ng-touched']")
	private WebElement checkbox;
	
	@FindBy(xpath = "//button[@class='destroy']]")
	private WebElement crossbutton;
	
	@FindBy(xpath = "//li/a[@class='selected']")
	private WebElement activebutton;
	
	@FindBy(xpath = "//li/a[text()='All']")
	private WebElement allbutton;


//====Navigate To Menu ====
	public void navigateToDoList() {
		 String url = "https://www.tutorialspoint.com/index.htm";
	      driver.get(url);
	      WaitUtility.waitForPageLoaded(driver);
	      
	      WaitUtility.waitForElementsVisible(driver, header);

	      Element.click(driver, todolisttextbar, "textbar");
	 
	      todolisttextbar.sendKeys("Testing Tool");
	      todolisttextbar.sendKeys(Keys.ENTER);
	      
	      Element.click(driver, activebutton, "activebutton");
	      
	      
	      if(addlist.isDisplayed());
	      {
	    	  Element.click(driver, addlist, "addlist");
	    	  Element.click(driver, allbutton, "allbutton");
	    	  Element.click(driver, checkbox, "checkbox");
	    	  Element.click(driver, crossbutton, "crossbutton");
	    	  
	}
	}
}
