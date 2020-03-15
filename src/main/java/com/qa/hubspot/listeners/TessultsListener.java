package com.qa.hubspot.listeners;

	import org.testng.ITestContext;
	import org.testng.ITestListener;
	import org.testng.ITestResult;

	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;

	import com.tesults.tesults.*;

	public class TessultsListener implements ITestListener { 
	  // A list to hold your test cases.
	  List<Map<String,Object>> testCases = new ArrayList<Map<String, Object>>();

	  private static String getTestMethodName(ITestResult iTestResult) {
	    return iTestResult.getMethod().getConstructorOrMethod().getName();
	  }

	  @Override
	  public void onFinish(ITestContext iTestContext) {
	    // Map<String, Object> to hold your test results data.
	    Map<String, Object> data = new HashMap<String, Object>();
	    data.put("target", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6ImMxZjNlMjEyLWE4NzQtNDgyMC1hZjFhLTJiMzM3YzY1NzI2Yy0xNTgzNzk4MjY4NTIxIiwiZXhwIjo0MTAyNDQ0ODAwMDAwLCJ2ZXIiOiIwIiwic2VzIjoiNDgyOGQ1OWItMzRkNy00ZWNmLTg4NWMtYmRiZjZkOWY5ZGM3IiwidHlwZSI6InQifQ.w0AxKUrq-uVqTee0B_yw5o5-H-ea02u-lYKaTRXROds");

	    Map<String, Object> results = new HashMap<String, Object>();
	    results.put("cases", testCases);
	    data.put("results", results);

	    // Upload
	    Map<String, Object> response = Results.upload(data);
	    System.out.println("success: " + response.get("success"));
	    System.out.println("message: " + response.get("message"));
	    System.out.println("warnings: " + ((List<String>) response.get("warnings")).size());
	    System.out.println("errors: " + ((List<String>) response.get("errors")).size());
	  }

	  @Override
	  public void onTestSuccess(ITestResult iTestResult) {
	    System.out.println("I am in onTestSuccess method " +  getTestMethodName(iTestResult) + " succeed");
	    Map<String, Object> testCase = new HashMap<String, Object>();
	    testCase.put("name", getTestMethodName(iTestResult));
	    testCase.put("suite", "TesultsExample");
	    testCase.put("result", "pass");
	    testCases.add(testCase);
	  }

	  @Override
	  public void onTestFailure(ITestResult iTestResult) {
	    System.out.println("I am in onTestFailure method " +  getTestMethodName(iTestResult) + " failed");
	    Map<String, Object> testCase = new HashMap<String, Object>();
	    testCase.put("name", getTestMethodName(iTestResult));
	    testCase.put("suite", "TesultsExample");
	    testCase.put("result", "fail");
	    testCases.add(testCase);
	  }

	  @Override
	  public void onTestSkipped(ITestResult iTestResult) {
	    System.out.println("I am in onTestSkipped method "+  getTestMethodName(iTestResult) + " skipped");
	    Map<String, Object> testCase = new HashMap<String, Object>();
	    testCase.put("name", getTestMethodName(iTestResult));
	    testCase.put("suite", "TesultsExample");
	    testCase.put("result", "unknown");
	    testCases.add(testCase);
	  }
	}
	
	
