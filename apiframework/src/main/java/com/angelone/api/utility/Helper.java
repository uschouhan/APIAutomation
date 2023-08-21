package com.angelone.api.utility;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.crypto.Mac;
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

import lombok.SneakyThrows;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
		byte[] inputBytes = data.getBytes();
		Deflater deflater = new Deflater();
		deflater.setInput(inputBytes);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// Compress the data
		byte[] buffer = new byte[40000];
		while (!deflater.finished()) {
			int compressedSize = deflater.deflate(buffer);
			outputStream.write(buffer, 0, compressedSize);
		}
		// Close the streams
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Get the compressed data as a byte array
		byte[] compressedBytes = outputStream.toByteArray();

		// Encode the compressed data using Base64
		String compressedData = Base64.getEncoder().encodeToString(compressedBytes);
		// Print the compressed data
		System.out.println(compressedData);
		return compressedData;
	}


	@SneakyThrows
	public String getOtpFromMail(String emailID, String emailPass) {
		//Login lpa = PageFactory.initElements(getDriver(), Login.class);
		String otp = null;
		otp = getOTPmail(emailID, emailPass);
		int count = 0;
		while (count < 5) {
			if (Objects.isNull(otp) || otp.isEmpty()) {
				otp = getOTPmail(emailID, emailPass);
				count++;
				Thread.sleep(3000);
			} else break;
		}
		//System.out.println("First time OTP for is " + otp);
		System.out.println("First time OTP for is " + otp);
		return otp;
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

	 public String genJTWTokenUAT(String UserID,String secret){
		 	// String secret = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
	        //String secret = System.getProperty("AMXJWTSecretKey") ;
	        //String encodedString = Base64.getEncoder().encodeToString(secret.getBytes());

	        JSONObject headers = new JSONObject();
	        JSONObject payload = new JSONObject();
	        JSONObject userdata = new JSONObject();

	        headers.put("alg", "HS256");
	        headers.put("typ", "JWT");

	        userdata.put("country_code", "");
	        userdata.put("mob_no", "");
	        userdata.put("user_id",UserID);
	        userdata.put("source", "TESTSUITE");
	        userdata.put("app_id", "pqa_1");
	        userdata.put("created_at", "2023-01-09T11:37:41.092792305+05:30");

	        payload.put("userData", userdata);
	        payload.put("iss","angel");
	        payload.put("exp","1736316461");
	        payload.put("iat", "1673244461");

	        System.out.println(GenerateJWT(headers, payload, secret));
	        String token=GenerateJWT(headers, payload, secret);
	        return token;
	 }
	 
	    public String GenerateJWT(JSONObject Headers,JSONObject Payload, String secret) {
	        String JWT="";
	        if(Headers.get("alg").toString().equals("HS256")) {
	            String signature = hmacSha256(encode(Headers.toString().getBytes()) + "." + encode(Payload.toString().getBytes()), secret);
	            JWT = encode(Headers.toString().getBytes()) + "." + encode(Payload.toString().getBytes()) + "." + signature;
	        }else if(Headers.get("alg").toString().equals("HS512")) {
	            String signature = hmacSha512(encode(Headers.toString().getBytes()) + "." + encode(Payload.toString().getBytes()), secret);
	            JWT = encode(Headers.toString().getBytes()) + "." + encode(Payload.toString().getBytes()) + "." + signature;
	        }
	        return JWT;
	    }
	    
	    private static String encode(byte[] bytes) {
	        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	    }
	    
	    private String hmacSha256(String data, String secret) {
	        try {

	            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
	            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
	            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
	            sha256Hmac.init(secretKey);

	            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

	            return encode(signedBytes);
	        } catch (Exception ex) {
	            return null;
	        }
	    }
	
	    private String hmacSha512(String data, String secret) {
	        try {

	            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
	            Mac sha512Hmac = Mac.getInstance("HmacSHA512");
	            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA512");
	            sha512Hmac.init(secretKey);

	            byte[] signedBytes = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

	            return encode(signedBytes);
	        } catch (NoSuchAlgorithmException | InvalidKeyException | java.security.InvalidKeyException ex) {
	            return null;
	        }
	    }
	public String BuyroundoffValueToCancelOrder(String ltp1) {
		double lt = Double.parseDouble(ltp1);
		double per = lt * 2 / 100;
		double buyPrice = (lt - per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		return String.valueOf(FinalBuyPrice);
	}
	
	public String gttBuyCancelOrderLimitPrice(String ltp1) {
		int lt = Integer.parseInt(ltp1);
		int per = lt * 2 / 100;
		int buyPrice = (lt - per) * 10;
		int roundOff = Math.round(buyPrice);
		int FinalBuyPrice = roundOff / 10;
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
	
	public String buyValueTriggerPriceForGTT(String ltp) {
		int lt = Integer.parseInt(ltp);
		int per = lt * 10 / 100;
		int buyPrice = (lt - per) * 10;
		int roundOff = Math.round(buyPrice);
		int FinalBuyPrice = roundOff / 10;
		int roundOffFinal = Math.round(FinalBuyPrice);
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

	public String fnoBuyMarketPending(String ltp) {
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
		else if (type.equalsIgnoreCase("D"))
			dateNew = new Date(System.currentTimeMillis() - value * 24 * 3600 * 1000);
		else if (type.equalsIgnoreCase("W"))
			dateNew = new Date(System.currentTimeMillis() - value * 7 * 24 * 3600 * 1000);
		else if (type.equalsIgnoreCase("M"))
			dateNew = new Date(System.currentTimeMillis() - value * 30 * 24 * 3600 * 1000);
		else if (type.equalsIgnoreCase("y"))
			dateNew = new Date(System.currentTimeMillis() - value * 365 * 24 * 3600 * 1000);
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
		String formattedString = String.format("%s-%s-%s-%s-%s", randomString.substring(0, 8),
				randomString.substring(8, 12), randomString.substring(12, 16), randomString.substring(16, 20),
				randomString.substring(20));
		System.out.println(formattedString);
		return formattedString;
	}

	public String modifyJsonData(String jsonFilePath, String value) throws Exception {
		InputStream resourceAsStream = null;
		String text = "";
		try {
			// String dataFileName = "requests/setWatchlistData.json";
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

	public String getTriggerPriceValueForBuy(String ltp1) {
		double lt = Double.parseDouble(ltp1);
		double per = lt * 5 / 100;
		double buyPrice = (lt - per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		System.out.print(FinalBuyPrice);
		return String.valueOf(FinalBuyPrice);
	}

	public static Properties readPropertiesFile(String fileName) throws IOException {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}

	public String decompressData(String compressedData) {

//		byte[] decodedData = Base64.getDecoder().decode(compressedData);
//
//		Inflater inflater = new Inflater();
//		inflater.setInput(decodedData);
//		byte[] decompressedData = new byte[90000];
//		String output = "";
//		try {
//			int decompressedSize = inflater.inflate(decompressedData);
//			inflater.end();
//
//			output = new String(decompressedData, 0, decompressedSize);
//			System.out.println("Decompress Data -> \n"+output);
//		} catch (DataFormatException e) {
//			e.printStackTrace();
//		}
//		return output;
		
		byte[] decodedData = Base64.getDecoder().decode(compressedData);
	    Inflater inflater = new Inflater();
	    inflater.setInput(decodedData);
	    List<Byte> decompressedData = new ArrayList<>();
	    String output = "";

	    try {
	        byte[] buffer = new byte[1024];
	        while (!inflater.finished()) {
	            int decompressedSize = inflater.inflate(buffer);
	            for (int i = 0; i < decompressedSize; i++) {
	                decompressedData.add(buffer[i]);
	            }
	        }
	        inflater.end();

	        byte[] decompressedArray = new byte[decompressedData.size()];
	        for (int i = 0; i < decompressedData.size(); i++) {
	            decompressedArray[i] = decompressedData.get(i);
	        }

	        output = new String(decompressedArray);
	        System.out.println("Decompressed Data -> \n" + output);
	    } catch (DataFormatException e) {
	        e.printStackTrace();
	    }

	    return output;
	}

	public static boolean isExpiryGreaterThanCurrentDate(String givenDateStr) {
        // Specify the given date in the format "01 Jan 1980"
       // String givenDateStr = "01 Jan 1980";
        boolean check=false;
        // Get the current system date
        Date currentDate = new Date();
        
        // Convert the current date to the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String currentDateString = dateFormat.format(currentDate);
        
        try {
            // Parse the given date string into a Date object
            Date givenDate = dateFormat.parse(givenDateStr);
            
            // Compare the system date with the given date
            if (currentDate.compareTo(givenDate) < 0) {
                System.out.println("System date is less than the given date. "+givenDate);
                check=true;
            } else {
                System.out.println("System date is not less than the given date. "+givenDate);
                check=false;
            }
        } catch (Exception e) {
            System.out.println("Invalid date format: " + givenDateStr);
        }
        return check;
    }

	public static boolean isExpiryGreaterThanCurrentDateByWeek(String givenDateStr) {
		
	        //String givenDate = "09 Jun 2022";
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

	        try {
	            // Parsing the given date string
	            Date date = dateFormat.parse(givenDateStr);

	            // Getting the current system date
	            Date currentDate = new Date();

	            // Adding a week (6 days) to the current system date
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(currentDate);
	            calendar.add(Calendar.DAY_OF_YEAR, 6);
	            Date futureDate = calendar.getTime();

	            // Comparing the given date with the future date
	            if (date.after(futureDate)) {
	                System.out.println("Given date is greater than the current system date by a week.");
	                return true;
	            } else {
	                System.out.println("Given date is not greater than the current system date by a week.");
	                return false;
	            }
	        } catch (Exception e) {
	            System.out.println("Invalid date format. Please provide the date in the format 'dd MMM yyyy'.");
	            return false;
	        }
	    
    }

	
	public static long numOfDaysDiff(String fromDate,String toDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		ZonedDateTime fromZdt = ZonedDateTime.parse(fromDate, formatter);
		ZonedDateTime toZdt = ZonedDateTime.parse(toDate, formatter);

		LocalDateTime fromDateTime = fromZdt.toLocalDateTime();
		LocalDateTime toDateTime = toZdt.toLocalDateTime();

		Duration duration = Duration.between(fromDateTime, toDateTime);
		long days = duration.toDays();
		System.out.println("Number of days between " + fromDate + " and " + toDate + ": " + days);
		return days;
	}

	public static void updatePropertyValue(String fileName , String key ,String value) {
		String propertyFilePath = "src/test/resources/"+fileName;
		try {
			PropertiesConfiguration conf = new PropertiesConfiguration(propertyFilePath);
			conf.setProperty(key, value);
			conf.save();
			System.out.println("Property file updated successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
