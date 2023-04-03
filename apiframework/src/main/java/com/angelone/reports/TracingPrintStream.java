package com.angelone.reports;

import java.io.PrintStream;

import org.testng.Reporter;

import com.angelone.reports.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class TracingPrintStream extends PrintStream{


	public TracingPrintStream(PrintStream original) {
		super(original);
	}

	@Override
	public void println(String line) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		StackTraceElement caller = stack[2];

		ExtentTest extnTest3_0 =   ExtentManager.getExtentTest();
		if(extnTest3_0!=null){
			String prettyPrint = line.replace("\n", "<br>");
			extnTest3_0.info("<pre>" + prettyPrint + "</pre>");
		}
		
		Reporter.log(line);
		super.println(caller.getClassName() + ": " + line);
	}


}
