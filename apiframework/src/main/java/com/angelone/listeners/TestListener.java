package com.angelone.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.angelone.reports.ExtentLogger;
import com.angelone.reports.ExtentReport;

public class TestListener implements ITestListener {
	

	@Override
	public void onTestFailure(ITestResult result) {
		
		ExtentLogger.fail("Test Failed");
		ExtentLogger.fail(result.getThrowable());
		
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		
		ExtentLogger.skip("Test Skipped");
		ExtentLogger.skip(result.getThrowable());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		
		ExtentLogger.pass("Test Passed");
	}

	@Override
	public void onFinish(ITestContext context) {
		
		ExtentReport.flushReports();
		
	}

	@Override
	public void onStart(ITestContext context) {
		
		ExtentReport.initReports();
	}

}
