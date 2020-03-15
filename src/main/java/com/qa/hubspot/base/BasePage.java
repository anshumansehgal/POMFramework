package com.qa.hubspot.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.stream.FileImageInputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.extentreports.utils.FileUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {
	
	//WebDriver driver;
	public Properties prop; //to use prop anywhere | Globally available
	public static boolean highlightElement;
	public OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>(); 
//ThreadLocal will create local copy of WebDriver. initialize driver with ThreadLocal because with this we can run multiple
// instances. It helps in running Parallel testing/Parallel browser so we have to distribute driver locally.
//ThreadLocal is from Java not from Selenium. We have to use get and set method with Threadlocal
	
	public static synchronized WebDriver getDriver() {//Synchronized bec we're talking about multiple threads
		return tldriver.get(); //it will give WebDriver local copy
	}
	

	public WebDriver init_driver(String browserName) {
		highlightElement = prop.getProperty("highlight").equals("yes") ? true : false;
		System.out.println("browser name is "+ browserName);
		optionsManager = new OptionsManager(prop);
		
		if(browserName.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver(optionsManager.getChromeOptions());
			tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
		else if(browserName.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}	
		else if(browserName.equals("safari")) {
			WebDriverManager.getInstance(SafariDriver.class).setup();
//			driver= new SafariDriver();
			tldriver.set(new SafariDriver());
		}
		else
			System.out.println("Browser name " + browserName + " is not found! Please pass correct browser");
	
	
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		
		return getDriver();
	}
	
	
	public Properties init_properties() { //to make connection with config.properties because we have to read values from it
		prop = new Properties();
		String path = null;
		String env = null;
		
		try {
		System.getProperty("env"); //like QA--env variable
		
		if(env.equals("qa"))
			path = "./src/main/java/com/qa/hubspot/config/qa.config.properties";
		else if(env.equals("stg"))
			path = "./src/main/java/com/qa/hubspot/config/stg.config.properties";
		}
		catch(Exception e) {
			path = "./src/main/java/com/qa/hubspot/config/config.properties";
		}
		
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/qa/hubspot/config/config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("Some issue with config properties..please correct with your config.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	/**
	 * Takes screenshot
	 */
	
	public String getScreenshot() {
		
	File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
	String path = System.getProperty("user.dir")+ "/screenshots" + System.currentTimeMillis() + ".png";
	File destination = new File(path);
	
	try {
		FileUtils.copyFile(src, destination);
	} catch (IOException e) {
		System.out.println("Screenshot Capture failed...");
			}
	return path;
	}
	
	
	
	
	
}
