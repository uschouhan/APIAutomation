package com.angelone.listeners;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.angelone.reports.ExtentLogger;
import com.angelone.reports.ExtentReport;

public class TestListener implements ITestListener {
	static int failedCount=0;
	static StringBuilder failedTcsDetails = new StringBuilder();
	
	@Override
	public synchronized void onTestFailure(ITestResult result) {
			
		if(result.getThrowable() != null) {
			  StringWriter sw = new StringWriter();
			  PrintWriter pw = new PrintWriter(sw);
			  result.getThrowable().printStackTrace(pw);
			  //add the failed tests to the failed list
		ExtentLogger.fail("Test Failed");
		ExtentLogger.fail(result.getThrowable());
		failedCount+=1;
		result.getTestContext().getCurrentXmlTest().getParameter("UserCredentials").toString();
		String str=result.getTestContext().getName().toString()+" - "+result.getMethod().getMethodName()+" - "+result.getTestContext().getCurrentXmlTest().getParameter("UserCredentials").toString();
		failedTcsDetails.append(str).append("\n");
	
	}
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
//		IResultMap failedTests = context.getFailedTests();
//		Collection<ITestNGMethod> failedMethods = failedTests.getAllMethods();
//		int failcount = failedMethods.size();
//		System.out.println("Failed Test Cases Count = "+failcount);
//		SlackIntegration slack = new SlackIntegration();
//		//slack.sendExecutionStatusToSlack(apiMsg);
//        slack.sendFileToslack("Failed ="+failcount, "reports/ApiSyntheticRun.html");
	}
	
	

	@Override
	public void onStart(ITestContext context) {
		
		ExtentReport.initReports();
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

}
