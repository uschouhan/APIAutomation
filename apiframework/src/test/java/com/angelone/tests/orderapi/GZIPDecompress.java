package com.angelone.tests.orderapi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;

import com.angelone.api.utility.Helper;


import io.restassured.path.json.JsonPath;

public class GZIPDecompress {

	public static void main(String[] args) throws IOException {
		String compressedString = "eJzclMtu2zoQhl/FmO1hCF50Xx3HkQujjdom7sIoCoG2WIEoRSki7UII/O4FK8ex2xTIJkAb7fSDM/PPzIe5B+vazbdSK+sg+3wP1+1aaVkW2wYyKFojAUHeCKUhg20nTdULSihJ4v9rr+JN2wCCqlMVZEAZ4ZwRGoQsSDjhgMBuetUZ0UjIYDGfLSbvllcYECirjJeKnPB0SnxOQPC1l7K8cwNkwDDxHyDotKzqB5kc5Uo46VQjrRNN5wMI4xeEX7B0QqIsSLIwwiwGBO1aq1o41fqCPnJrxpSH364VvldAYKQrO13VJ7UAwaMfQLDRrZVlL5yPTjEdvbjWCT8hmmA2KsbKUnRd3+6kn8x0J3tR+2HuRF82ovcZGSZsfCxdKRrnEwSYpqdORvngZHG7KMrl0Pna+d1WuQEQFJfzWXmp/RY/jrYBwUwradzD2/eNkXawj/phIZ8+5MXVzRQQXKva9+StrsaWS60a5aj3eSqwQ4FeVsoujJW9H+yVcHKpfuY8XUOcEZ6xCNM0jWj8Hwkz7mMLK+3QrFt9YAL26IXBM5USZnIjlP4uhslcGWHOGQz5/B9lkIU4PIcwpJg8H0JyfHyEMCA4fpUQJjR6GsKb+ezFIVzlt5PLafH29wsYsuQNoYSH5/TRv4w++gR9NMBxeH4Cj8rz6Ev4ryeQ4ih+jfTFfziBq/zWcwH7L/sfAQAA//91uy8L";
//    byte[] compressedBytes = compressedString.getBytes();
//
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    ByteArrayInputStream bais = new ByteArrayInputStream(compressedBytes);
//    GZIPInputStream gzipInputStream = new GZIPInputStream(bais);
//
//    byte[] buffer = new byte[1024];
//    int len;
//    while ((len = gzipInputStream.read(buffer)) > 0) {
//      baos.write(buffer, 0, len);
//    }
//
//    gzipInputStream.close();
//    baos.close();
//    bais.close();
//
//    String decompressedString = baos.toString("UTF-8");
//    System.out.println(decompressedString);

//	  String status = "Total Failed Count --> 12 ";
		// SlackIntegration slack = new SlackIntegration();
		// slack.sendFileToslack(status,"Extent.html");

		// System.out.println(Helper.generateDeviceId());

//		JsonPath jPath = new JsonPath("text");
//		List<String> list = jPath.getList("data.symbolList[0].cardValues");
//		System.out.println(list.size());
//		System.out.println(jPath.getString("data.symbolList[0].symbolDesc"));
//		System.out.println(jPath.getString("data.symbolList[0].symbolDetails.tradeSymbol"));
//		System.out.println(jPath.getString("data.symbolList[0].symbolDetails.symbolName"));
//		System.out.println(jPath.getString("data.symbolList[0].symbolDetails.tokenID"));
//		System.out.println(jPath.getString("data.symbolList[0].symbolDetails.expiry"));
//		System.out.println(jPath.getString("data.symbolList[0].symbolDetails.details"));
//		System.out.println(jPath.getString("data.symbolList[0].symbolDetails.astCls"));
//		System.out.println(jPath.getString("data.symbolList[0].symbolDetails.optType"));
//		System.out.println(jPath.getString("data.symbolList[0].symbolDetails.minLot"));
//		System.out.println(jPath.getString("data.symbolList[0].symbolDetails.regLot"));

		decompressData(compressedString);

	}

	public static void decompressData(String compressedData) {
		// String compressedData =
		// "eJzcl0tv2zgQx+/7KYw5qwJHEmVKN8d2FtpNnC1sYLEocqAl1iash1ek03qLfvcFKSmRH03iIu2hJ3uGjxnN/P6i+OELfOI6XedS6SSDGMOQUeYTwojzNDLjhYAYlnXFs5QrDb2xuzoTNcToQKIy8ZHvcg2xrnfCAZXWcjsRmstcQfzhS+N4WlDweiP0mG8hhoBiQN0AwQFd80zM98WyyiGGf6bzq9Hsz3fT9+CAVGOu1n9Lvb4uK4g/8lwJB0oljHtRbUQJMSBGSMGBdFdf73TnBQe40mOTCaRcrcGBrEvNBBmYKIOb5DZZTCdmUD5ImyggIeCA+Jyu2zrM5lPr2Mp6n3FtXAQHf/BygBEzc9dytf6rlqkZQWZdsnwso30OWY6rzFjJbEo99jtB4puk80rf2hICNtZc/tcuyqtPj5t6odm0kOVN1c0tNnouVsmkNautXuy37dJtLVJlquBZQ6ZikW5M2i4xQWuxetpHiXQiVHq+KkrUUpiK2XYoXaUbG5EN/WFkV+t602X5zjpsI9tHb5tpmnzUrNJmNdsVbRaNPWnmmJJWefZe7yEmDiy5Ev3/o4dVG5I4wPtGtvy3+VXt79LAZh3dn63ZWZYra8nyQSgtssZQ46ooqkzqvSHubqtlVT5CJ1VSZjI1xWg9Bf88l+UqF4ual4qnZrpNE0KfBsOQwVfnUAPekQaiIMLAtRwcimB+lcxei79Pggvpny9Gi2nT6bvrQTKbJKPnBXB1iQBCJOQlBZDQGxEkHnm1AoIowuck4L+NBM6X5lEFowMR+MGQYviiCNpmdgqghCB6v7oEyAn8/in8lLkROwv/2dd/c8r8fPwvev8b/OkPwZ/++BPgBfyPDoHv479t2C9NPw5ZEHknEggOJeD5PnPD8EQAyfU4ueT7J7An8QUKMAEGN4s3/ezxhi9S70eGenw99Yz+BOZ7xfgW6JRFaKF9HnSzU/9Tp+nLq0HHHuh4DHrk0j7qSFyvpR1b2rGjHYkbdcAjcRk9gB770OObUh/5bEgN9vcG/G/cM/D0nnG7v5FnLxnewSWjjX90y7j/ev/b/wEAAP//CcLcKw==";
		byte[] decodedData = Base64.getDecoder().decode(compressedData);

		Inflater inflater = new Inflater();
		inflater.setInput(decodedData);
		byte[] decompressedData = new byte[10000];
		try {
			int decompressedSize = inflater.inflate(decompressedData);
			inflater.end();

			String output = new String(decompressedData, 0, decompressedSize);
			System.out.println(output);
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
	}
}
