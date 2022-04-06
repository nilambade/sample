package com.functional.tests;

import org.testng.annotations.Test;

import com.common.BaseTest;
import com.common.Element;
import com.utilities.WaitUtility;

public class Todolistactions extends  BaseTest {

	@Test(priority = 1)
	public void Todolist() {
		WaitUtility.waitForPageLoaded(driver);
		Element.reporter("Navigate todo list");
		page.Todolistactions.navigateToDoList();
	}
}
