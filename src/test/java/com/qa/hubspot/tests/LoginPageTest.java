package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.page.HomePage;
import com.qa.hubspot.page.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;


@Epic("Epic - 101 : create login page features")
@Feature("UserStory 501 : create test for login page on hubspot")

public class LoginPageTest { //Simon Steward-Never write driver APIs in testNG class -driver.findElement, driver.SendKeys. 
							 //Never write assertions in Page Class. It should be written inside Test class. Page class is only for library.
	
	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	Credentials userCred;
	
	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		 basePage = new BasePage();
		 prop = basePage.init_properties();
		 String browserName = prop.getProperty("browser");
		 driver = basePage.init_driver(browserName);
		 driver.get(prop.getProperty("url"));
		 
		 loginPage = new LoginPage(driver);
		 userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test (priority = 1)
	@Description("verify Login Page Title Test..")
	@Severity(SeverityLevel.NORMAL)
	
	public void verifyLoginPageTitleTest() throws InterruptedException {
	  String title = loginPage.getPageTitle();
		System.out.println("Login page title is " + title);
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Test (priority = 2, groups = "sanity")
	@Description("verify Sign Up Link Test..")
	@Severity(SeverityLevel.CRITICAL)
	public void verifySignUpLinkTest() {
	 Assert.assertTrue(loginPage.checkSignUpLink());	
	}
	
	@Test (priority = 3)
	@Description("verify Login Test..")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest() {
		HomePage homepage = loginPage.doLogin(userCred);
		String accountName = homepage.getLoggedInUserName();
		Assert.assertEquals(accountName, prop.getProperty("accountname"));
	}
	
	@DataProvider //always return 2-D Array 
	public Object[][] getLoginInvalidData() {
		Object data[][] = { {"test1@gmail.com", "test12345"}, {"test2@gmail.com", " "}, {" ", "test1234"}, {"test", "test"}, {" ", " "} };
		return data;
	}
	
	@Test (priority = 4, dataProvider = "getLoginInvalidData", enabled = false) //negative test case with Data Provider
	public void loginTest_WrongCredentials(String username, String pwd) {
		userCred.setAppUsername(username);
		userCred.setAppPassword(pwd);
		loginPage.doLogin(userCred);
		Assert.assertTrue(loginPage.checkLoginErrorMessage());
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
	
	
}
