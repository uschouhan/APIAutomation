package com.angelone.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.angelone.reports.ExtentLogger;



public class TestListener implements ITestListener {
	

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailure(result);
		ExtentLogger.fail("Test Failed");
		
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
		ExtentLogger.skip("Test Skipped");
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
		ExtentLogger.pass("Test Passed");
	}
	
	
	
}
