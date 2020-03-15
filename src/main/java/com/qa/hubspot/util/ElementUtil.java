package com.qa.hubspot.util;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.hubspot.base.BasePage;

public class ElementUtil extends BasePage{

	WebDriver driver;
	WebDriverWait wait;
	JavaScriptUtil jstUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, AppConstants.DEFAULT_TIMEOUT);
		jstUtil = new JavaScriptUtil(driver);
	}
	
	public boolean waitForElementPresent(By locator) { //Explicitly Wait
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return true;
	}
	
	public boolean waitForElementVisible(By locator) { //Explicitly Wait
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return true;
	}
	
	public boolean waitForTitlePresent(String title) {
		wait.until(ExpectedConditions.titleIs(title));
		return true;
	}
	
	public String doGetPageTitle() {
		return driver.getTitle();
	}
	
	/**
	 * This method is used to create the webelement on the basis of By locator 
	 * @param locator
	 * @return element
	 */
	public WebElement getElement(By locator) {
		WebElement element= null;
		try {
		//	if(waitForElementPresent(locator));  //Approach 2 - creates dependency and applied to every element. Not a bad approach.
			element = driver.findElement(locator);
			if(highlightElement) {
				jstUtil.flash(element);
			}
		} catch(Exception e) {
			System.out.println("Some exception got occurred while creating the web element..");
		}
			return element;
	}

	public void doClick(By locator) {
		try {
		getElement(locator).click();
		}
		catch(Exception e) {
			System.out.println("Some exception occurred while clicking on webelement");
		}
	}

	public void doSendKeys(By locator, String value) {
		try {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
		}
		catch(Exception e) {
			System.out.println("Some exception occurred while entering values in a field");
		}
	}
	
	
	public boolean doIsDisplayed(By locator) {
		try {
		return getElement(locator).isDisplayed();
		}catch (Exception e) {
			System.out.println("Exception occured at IsDisplayed");
		}
		return false;
		}
	
	public String doGetText(By locator) {
		try {
		return getElement(locator).getText();
		} catch(Exception e) {
			System.out.println("Exception occured while getting text from a web element");
		}
		return null;
	}
	
	
	
}
