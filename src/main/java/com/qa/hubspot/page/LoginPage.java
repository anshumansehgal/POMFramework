package com.qa.hubspot.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;
import com.qa.hubspot.util.ElementUtil;
import com.qa.hubspot.util.JavaScriptUtil;

public class LoginPage extends BasePage{

	WebDriver driver;
	ElementUtil elementUtil;
	JavaScriptUtil jstUtil;

	//This Concept is called Encapsulation, we have page variables within a page like these and using these in a methods. POM with Encapsulation approach
//1. Locators - By
	By emailId = By.id("username");
	By password = By.id("password");
	By loginButon = By.id("loginBtn");
	By signUpLink = By.linkText("Sign up");
	By loginErrorText = By.xpath("//div[@class='private-alert__inner']");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
		jstUtil = new JavaScriptUtil(driver);
	}
	
//Page actions--should not use 'driver' inside page actions
	public String getPageTitle() {
		elementUtil.waitForTitlePresent(AppConstants.LOGIN_PAGE_TITLE);
		return elementUtil.doGetPageTitle();
	}
	
	public String getPageTitleUsingJS() {
		return jstUtil.getTitleByJS();
	}
	
	public boolean checkSignUpLink() {
//		elementUtil.waitForElementPresent(signUpLink); //Approach 1-Everytime we've to call, It just takes extra code
		return elementUtil.doIsDisplayed(signUpLink);
	}
	
	public HomePage doLogin(Credentials userCred) {
		elementUtil.waitForElementPresent(emailId); //Whenever it's needed we'll apply this wait--depends on appl.
		elementUtil.doSendKeys(emailId, userCred.getAppUsername());
		elementUtil.doSendKeys(password, userCred.getAppPassword());
		elementUtil.doClick(loginButon);
		
		return new HomePage(driver);
	}
	
	public boolean checkLoginErrorMessage() {
		return elementUtil.doIsDisplayed(loginErrorText);
	}
	
	
	
}
