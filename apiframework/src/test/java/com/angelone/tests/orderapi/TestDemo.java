package com.angelone.tests.orderapi;

import java.util.Calendar;
import java.util.TimeZone;

import org.testng.annotations.Test;

import com.angelone.api.utility.Helper;

public class TestDemo {

	@Test
	public void testDemo() throws Exception {
		System.out.println("Demo  Test");
		String orderTypeCheckForComodity = orderTypeCheckForComodity();
		System.out.println(" Order Type = "+orderTypeCheckForComodity);
	}
	
	
	 public String orderTypeCheckForComodity() {
	        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
	        //int hour = calendar.get(Calendar.HOUR_OF_DAY);
	        //int minute = calendar.get(Calendar.MINUTE);
	        int hour = 0;
	        int minute = 55;
	        if (hour >= 9 && hour <= 22) {
	            if (hour == 9 && minute < 1)
	                return "AMO";
	            else
	                return "NORMAL";
	        } else if (hour == 23) {
	            if (minute < 59)
	                return "NORMAL";
	            else
	                return "AMO";

	        } else
	            return "AMO";
	        }
}
