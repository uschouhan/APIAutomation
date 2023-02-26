package com.angelone.reports;

import java.io.File;

import com.angelone.api.utility.Constants;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;


public final class ExtentReport {

    private ExtentReport(){}
    

    public static ExtentReports extent;
    public static ExtentTest extentTest; //loginTest is not thread safe

   
    public static void initReports(){
        extent = new ExtentReports();
        File reportsFolder = new File("reports");
        if (!reportsFolder.exists()) {
            reportsFolder.mkdirs();
        }
        ExtentSparkReporter spark = new ExtentSparkReporter(Constants.EXTENTREPORTPATH).viewConfigurer().viewOrder().as(new ViewName[] {ViewName.DASHBOARD,ViewName.TEST,
                		ViewName.CATEGORY,ViewName.AUTHOR}).apply();
        extent.attachReporter(spark);

    }

    public static synchronized void flushReports(){
        extent.flush();
    }
    
   

    public static synchronized void createTest(String testCaseName){
        extentTest = extent.createTest(testCaseName);
        ExtentManager.setExtentTest(extentTest);
    }

    public static synchronized void assignAuthor(String author){
        ExtentManager.getExtentTest().assignAuthor(author);
    }

}
