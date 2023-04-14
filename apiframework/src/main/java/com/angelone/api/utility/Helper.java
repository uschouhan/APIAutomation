package com.angelone.api.utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.angelone.api.pojo.UserDataJWT_POJO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;

public class Helper {

	public String decodeData(String data) {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.zickty.com/gziptotext/");
		driver.findElement(By.id("input")).sendKeys(data);
		driver.findElement(By.id("button1")).click();
		// String decodedJson = driver.findElement(By.id("output")).getText();
		// System.out.println(" ##### Decoded Text ### \n"+decodedJson);
		String decodedData = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",
				driver.findElement(By.id("output")));
		System.out.println("Decoded Data \n " + decodedData);
		driver.quit();
		return decodedData;
	}

	public String encodeData(String data) {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.zickty.com/gziptotext/");
		driver.findElement(By.id("output")).sendKeys(data);
		driver.findElement(By.id("button2")).click();
		// String decodedJson = driver.findElement(By.id("output")).getText();
		// System.out.println(" ##### Decoded Text ### \n"+decodedJson);
		String encodedData = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",
				driver.findElement(By.id("input")));
		System.out.println("enCoded Data \n " + encodedData);
		driver.quit();
		return encodedData;
	}

	public String getOTPmail(String emailID, String emailPass) throws InterruptedException {
		// BasePage conf = new BasePage();
		String otpNum = gMailReaderForSparkLoginNewForOTPViaEmail(emailID, emailPass, "Inbox", "OTP for Login",
				"Hello, your OTP is:<br />", 8);
		return otpNum;

	}

	public String gMailReaderForSparkLoginNewForOTPViaEmail(String emailID, String epass, String mailFolderName,
			String emailSubjectContent, String emailContent, int lengthOfOTP) {

		Message[] unreadMessages = null;
		final String userName = emailID;
		final String password = epass;
		String[] timeStamp;
		String emailSubject;
		Message emailMessage = null;
		String subStrOTP = null;
		Properties sysProps = System.getProperties();
		sysProps.setProperty("mail.store.protocol", "imaps");
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		try {
			Session session = Session.getDefaultInstance(sysProps, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", userName, password);
			Folder emailBox = store.getFolder(mailFolderName);
			emailBox.open(Folder.READ_WRITE);
			SearchTerm todayEmail = new ReceivedDateTerm(ComparisonTerm.EQ, new Date());
			// System.out.println("Total Message Count: " + emailBox.getMessageCount());
			System.out.println("Total Message Count: " + emailBox.getMessageCount());
			int iniCont = emailBox.getMessageCount();
			for (int i = 0; i < 7; i++) {
				int itraCont = emailBox.getMessageCount();
				if (itraCont > iniCont)
					break;
				Thread.sleep(1000);
			}

			int n = emailBox.getMessageCount();
			unreadMessages = emailBox.getMessages(n - 0, n);
			// System.out.println("Unread Emails count:" + unreadMessages.length);
			System.out.println("Unread Emails count:" + unreadMessages.length);
			for (int i = unreadMessages.length - 1; i <= unreadMessages.length + 1; i--) {
				if (i == -1)
					break;
				emailMessage = unreadMessages[i];

				emailSubject = emailMessage.getSubject();

				if (emailSubject.contains(emailSubjectContent)) {
					// System.out.println("OTP mail found");
					System.out.println("OTP mail found");
					String line;
					StringBuffer buffer = new StringBuffer();
					BufferedReader reader = new BufferedReader(new InputStreamReader(emailMessage.getInputStream()));
					while ((line = reader.readLine()) != null) {
						buffer.append(line);
					}
					String messageContent = emailContent;
					int otpIndex = buffer.toString().indexOf(":");
					int useIndex = buffer.toString().indexOf("Use");
					// String data = sc.nextLine();
					// Regular expression to match digits in a string
					String regex = "[0-9]{6}";
					// Creating a pattern object
					Pattern pattern = Pattern.compile(regex);
					// Creating a Matcher object
					Matcher matcher = pattern.matcher(buffer.toString());
					// System.out.println("Digits in the given string are: ");
					System.out.println("Digits in the given string are: ");
					while (matcher.find()) {
						// System.out.print(matcher.group()+" ");
						System.out.println(matcher.group() + " ");
						subStrOTP = matcher.group();
						break;
					}

				}

				// System.out.println("OTP for the Testcase : " + subStrOTP);
				System.out.println("OTP for the Testcase :  " + subStrOTP);
				emailMessage.setFlag(Flags.Flag.SEEN, true);
				timeStamp = emailMessage.getHeader("Date");
				// System.out.println("OTP email receieved from vender on: " +
				// Arrays.toString(timeStamp));
				System.out.println("OTP email receieved from vender on: " + Arrays.toString(timeStamp));
				emailMessage.setFlag(Flags.Flag.SEEN, true);
				emailMessage.setFlag(Flags.Flag.DELETED, true);
				boolean expunge = true;
				emailBox.expunge();

				break;

			}

			emailBox.close(true);

			store.close();

		} catch (

		Exception mex) {

			mex.printStackTrace();

			// System.out.println("OTP Not found ");
			System.out.println("OTP Not found ");
		}

		return subStrOTP;

	}

	public String genJTWToken(UserDataJWT_POJO data, String secret) {
		// String secret = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
		String jwtToken = "";
		try {
			SecretKeySpec hmacKey = new SecretKeySpec(secret.getBytes("UTF-8"), SignatureAlgorithm.HS256.getJcaName());
			Instant now = Instant.now();
			long futureTime = now.plus(365l, ChronoUnit.DAYS).toEpochMilli();
			long pastTime = now.minus(1l, ChronoUnit.DAYS).toEpochMilli();
			System.out.println("exp time = " + futureTime);
			System.out.println("iat time = " + pastTime);
			// UserDataJWT data = new UserDataJWT();
			Map<String, Object> userData = new HashMap<>();
			userData.put("userData", data);
			jwtToken = Jwts.builder().addClaims(userData).claim("iss", "angel").claim("exp", futureTime)
					.claim("iat", pastTime).setSubject("JWT key").setId(UUID.randomUUID().toString())
					.setIssuedAt(Date.from(now)).setExpiration(Date.from(now.plus(2l, ChronoUnit.DAYS)))
					.signWith(hmacKey).compact();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("JWT token === > " + jwtToken);
		return jwtToken;
	}

	public String BuyroundoffValueToCancelOrder(String ltp1) {
		double lt = Double.parseDouble(ltp1);
		double per = lt * 2 / 100;
		double buyPrice = (lt - per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		return String.valueOf(FinalBuyPrice);
	}

	public String buyValueCustomPriceForCurrency(String ltp) {
		double lt = Double.parseDouble(ltp);
		double per = lt * 2 / 100;
		double buyPrice = (lt - per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		return String.valueOf(FinalBuyPrice);
	}

	public String buyValueTriggerPriceForCommodity(String ltp) {
		double lt = Double.parseDouble(ltp);
		double per = lt * 2 / 100;
		double buyPrice = (lt - per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		double roundOffFinal = Math.round(FinalBuyPrice);
		return String.valueOf(roundOffFinal);
	}

	public String SellTriggerPriceGreater(String ltp1) {
		double lt = Double.parseDouble(ltp1);
		double per = lt * 2 / 100;
		double buyPrice = (lt + per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		System.out.print(FinalBuyPrice);
		return String.valueOf(FinalBuyPrice);

	}
	
	public String SellTriggerPriceGreaterT2T(String ltp1) {
		double lt = Double.parseDouble(ltp1);
		double per = lt * (0.5) / 100;
		double buyPrice = (lt + per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		System.out.print(FinalBuyPrice);
		return String.valueOf(FinalBuyPrice);

	}
	
	public  String fnoBuyMarketPending(String ltp) {
		double lt = Double.parseDouble(ltp);
		double per = lt * 5 / 100;
		double buyPrice = (lt - per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		return String.valueOf(FinalBuyPrice);
	}

	public static String dateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getCurrenctTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		Date date = new Date();
		System.out.println("End Time = " + dateFormat.format(date));
		return dateFormat.format(date);
	}

	public String getCurrenctTimeMinus(String type, int value) {
		Date dateNew = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		if (type.equalsIgnoreCase("h"))
			dateNew = new Date(System.currentTimeMillis() - value * 3600 * 1000);
		else if (type.equalsIgnoreCase("m"))
			dateNew = new Date(System.currentTimeMillis() - value * 60 * 1000);
		else
			System.out.println("Please provide correct type and value");
		System.out.println("Start Time = " + dateFormat.format(dateNew));
		return dateFormat.format(dateNew);
	}

	public String orderTypeCheckForEquity() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		if (hour >= 9 && hour <= 14) {
			if (hour == 9 && minute < 15)
				return "AMO";
			else
				return "NORMAL";
		} else if (hour == 15) {
			if (minute < 30)
				return "NORMAL";
			else
				return "AMO";

		} else
			return "AMO";
	}

	public String orderTypeCheckForCurrency() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		if (hour >= 9 && hour <= 16) {
			if (hour == 9 && minute < 1)
				return "AMO";
			else
				return "NORMAL";
		} else if (hour == 17) {
			if (minute < 1)
				return "NORMAL";
			else
				return "AMO";

		} else
			return "AMO";
	}

	public String orderTypeCheckForComodity() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
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
	
	public static String generateDeviceId() {
		 UUID uuid = UUID.randomUUID();
	        String randomString = uuid.toString().replace("-", "");
	        String formattedString = String.format("%s-%s-%s-%s-%s",
	            randomString.substring(0, 8),
	            randomString.substring(8, 12),
	            randomString.substring(12, 16),
	            randomString.substring(16, 20),
	            randomString.substring(20));
	        System.out.println(formattedString);
       return formattedString;
   }

	public String modifyJsonData(String jsonFilePath , String value) throws Exception {
		InputStream resourceAsStream = null;
		String text="";
		try {
			//String dataFileName = "requests/setWatchlistData.json";
			resourceAsStream = getClass().getClassLoader().getResourceAsStream(jsonFilePath);
			JSONTokener tokener = new JSONTokener(resourceAsStream);
			JSONArray testData = new JSONArray(tokener);
			text = String.format(testData.toString(), value);
			System.out.println(text);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		} finally {
			if (resourceAsStream != null) {
				resourceAsStream.close();
			}
		}
		return text;
	}
}
