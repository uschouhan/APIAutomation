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
		for (ISuite iSuite : arg1) {
			Map<String, ISuiteResult> results = iSuite.getResults();

			Set<String> keys = results.keySet();
			for (String key : keys) {
				ITestContext context = results.get(key).getTestContext();

				// Get Map for only failed test cases
				IResultMap resultMap = context.getFailedTests();

				// Get method detail of failed test cases
				Collection<ITestNGMethod> failedMethods = resultMap.getAllMethods();
				int failcount = failedMethods.size();

				// Loop one by one in all failed methods
				List<String> failedtest = new ArrayList<String>();
				for (ITestNGMethod iTestNGMethod : failedMethods) {
					failedtest.add(iTestNGMethod.getMethodName());

				}

				// Get Map for only skipped test cases
				IResultMap resultMap3 = context.getSkippedTests();

				// Get method detail of skipped test cases
				Collection<ITestNGMethod> skippedMethods = resultMap3.getAllMethods();
				int skipcount = skippedMethods.size();

				// Get Map for only passed test cases
				IResultMap resultMap2 = context.getPassedTests();

				// Get method detail of passed test cases
				Collection<ITestNGMethod> passedMethods = resultMap2.getAllMethods();
				int passcount = passedMethods.size();

				// Loop one by one in all passed methods
				List<String> passedTest = new ArrayList<String>();
				for (ITestNGMethod iTestNGMethod : passedMethods) {
					passedTest.add(iTestNGMethod.getMethodName());
				}

				// Converting ArrayList to String in Java using advanced for-each loop
				StringBuilder sb = new StringBuilder();
				for (String str : passedTest) {
					sb.append(str).append("\n"); // separating contents using newline
				}
				String passedTestList = sb.toString();

				// Converting ArrayList to String in Java using advanced for-each loop
				StringBuilder sb1 = new StringBuilder();
				for (String str : failedtest) {
					sb1.append(str).append("\n");
				}

				String failedTestList = sb1.toString();
				
				if(!failedTestList.isEmpty()) {
				SlackIntegration slack = new SlackIntegration();
				String strinmsg = "testing the msg";
				String message = "=============*Spark Automation Execution Summary*===========================" + "\n"
						+ "Total Count of Passed Test: " + passcount + "\n" + "Total Count of Failed Test: " + failcount
						+ "\n" + "Total Count of Skipped Test: " + skipcount + "\n"
						+ "====================The Execution Report====================" + "\n";
				//slack.sendResultToslack(message);
				slack.sendFileToslack(message, "reports/ApiSyntheticRun.html");
				}
			}

		}
	}

	/*
	 * public static void UploadReportToFileIO() { RequestSpecification req =
	 * RestAssured.given(); req.contentType("multipart/form-data");
	 * req.multiPart("file", new File(
	 * "C:\\\\Users\\\\vidya\\\\Documents\\\\SparkAutomation\\\\Android_AutomationReport.html"
	 * )); Response resp = req.post("https://file.io");
	 * System.out.println(resp.getBody().asString()); String uploadedfilepath =
	 * JsonPath.read(resp.getBody().asString(),"$.link"); String
	 * ReportLink=uploadedfilepath;
	 * //LogFactory.LogInfo("File.io Report Link: "+uploadedfilepath); }
	 */

}