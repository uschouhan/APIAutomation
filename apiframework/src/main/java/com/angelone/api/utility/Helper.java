package com.angelone.api.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Helper {

	public String decodeData(String data) {

		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.zickty.com/gziptotext/");
		driver.findElement(By.id("input")).sendKeys(data);
		driver.findElement(By.id("button1")).click();
		// String decodedJson = driver.findElement(By.id("output")).getText();
		// System.out.println(" ##### Decoded Text ### \n"+decodedJson);
		String decodedData = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",
				driver.findElement(By.id("output")));
		System.out.println("## using javascript#\n " + decodedData);
		driver.quit();
		return decodedData;
	}

	public String getOTPmail(String emailID, String emailPass) throws InterruptedException {
		// BasePage conf = new BasePage();
		String otpNum = gMailReaderForSparkLoginNewForOTPViaEmail(emailID, emailPass, "Inbox", "Login OTP",
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

}
