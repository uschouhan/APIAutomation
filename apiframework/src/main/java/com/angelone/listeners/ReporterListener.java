package com.angelone.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlSuite;

import com.angelone.api.utility.SlackIntegration;

public class ReporterListener implements IReporter {

	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1, String outputDirectory) {
		int failcount=0;
		StringBuilder sb1 = new StringBuilder();
//		for (ISuite iSuite : arg1) {
//			Map<String,ISuiteResult> results = iSuite.getResults();
//			Set<String> keys = results.keySet();
//			for (String key : keys) {
//				ITestContext context = results.get(key).getTestContext();
//				//Get Map for only failed test cases
//				IResultMap resultMap = context.getFailedTests();
//				// Get method detail of failed test cases
//				Collection<ITestNGMethod> failedMethods = resultMap.getAllMethods();
//				failcount += failedMethods.size();
//				//System.out.println("failed test is: "+failcount);
//
//				// Loop one by one in all failed methods
//				List<String> failedtest = new ArrayList<String>();
//				for (ITestNGMethod iTestNGMethod : failedMethods) {
//					failedtest.add(iTestNGMethod.getMethodName());
//
//				}
//				
//				for (String str : failedtest) {
//					str=context.getName().toString()+" - "+str+" - "+context.getCurrentXmlTest().getParameter("UserCredentials").toString();
//					sb1.append(str).append("\n");
//				}
//
//			}
//		}
		//String failedTestList = sb1.toString();
		if(TestListener.failedCount>0) {
			String result= "\nOverall failed Test Cases => "+TestListener.failedCount+"\nGM Details - Failed TCs Name - UserDetails => \n"+TestListener.failedTcsDetails;
			System.out.println(result);
			//System.out.println("final result is : "+apiMsg);
			try {
				SlackIntegration slack = new SlackIntegration();
				//slack.sendExecutionStatusToSlack(apiMsg);
		        slack.sendFileToslack(result, "reports/ApiSyntheticRun.html");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	}
}




			






	


