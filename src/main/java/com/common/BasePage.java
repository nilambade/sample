package com.common;



import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;

import com.pages.TodolistactionsPage;


public class BasePage {
	public WebDriver driver;
	public Logger log;
	public  TodolistactionsPage Todolistactions;
	


	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		log = Logger.getLogger(this.getClass().getName());
	;
		Todolistactions =  new TodolistactionsPage(driver);
		
	}

}