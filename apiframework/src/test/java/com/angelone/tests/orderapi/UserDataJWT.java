package com.angelone.tests.orderapi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

import org.apache.logging.log4j.core.util.IOUtils;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import lombok.Data;

public class UserDataJWT {

	private String country_code;
	private String user_id;
	private String created_at;
	private String mob_no;
	private String source;
	private String app_id;

	public UserDataJWT() {
		this.country_code = "";
		this.user_id = "U50049267";
		this.created_at = "2022-02-24T19:12:24.087297091+05:30";
		this.mob_no = "495358049";
		this.source = "SPARK";
		this.app_id = "56567";
	}

	@Test
	public void testName() throws Exception {
		String data = "eJzclMtu2zoQhl/FmO1hCF50Xx3HkQujjdom7sIoCoG2WIEoRSki7UII/O4FK8ex2xTIJkAb7fSDM/PPzIe5B+vazbdSK+sg+3wP1+1aaVkW2wYyKFojAUHeCKUhg20nTdULSihJ4v9rr+JN2wCCqlMVZEAZ4ZwRGoQsSDjhgMBuetUZ0UjIYDGfLSbvllcYECirjJeKnPB0SnxOQPC1l7K8cwNkwDDxHyDotKzqB5kc5Uo46VQjrRNN5wMI4xeEX7B0QqIsSLIwwiwGBO1aq1o41fqCPnJrxpSH364VvldAYKQrO13VJ7UAwaMfQLDRrZVlL5yPTjEdvbjWCT8hmmA2KsbKUnRd3+6kn8x0J3tR+2HuRF82ovcZGSZsfCxdKRrnEwSYpqdORvngZHG7KMrl0Pna+d1WuQEQFJfzWXmp/RY/jrYBwUwradzD2/eNkXawj/phIZ8+5MXVzRQQXKva9+StrsaWS60a5aj3eSqwQ4FeVsoujJW9H+yVcHKpfuY8XUOcEZ6xCNM0jWj8Hwkz7mMLK+3QrFt9YAL26IXBM5USZnIjlP4uhslcGWHOGQz5/B9lkIU4PIcwpJg8H0JyfHyEMCA4fpUQJjR6GsKb+ezFIVzlt5PLafH29wsYsuQNoYSH5/TRv4w++gR9NMBxeH4Cj8rz6Ev4ryeQ4ih+jfTFfziBq/zWcwH7L/sfAQAA//91uy8L";
		InputStream fis = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
		// FileInputStream fis = new FileInputStream(data);
		String decodedString = gzipToString1(fis);
		System.out.println("Decompressed data " + decodedString);
	}
	
	@Test
	public void testDecode() throws Exception {
		String data = "eJzclMtu2zoQhl/FmO1hCF50Xx3HkQujjdom7sIoCoG2WIEoRSki7UII/O4FK8ex2xTIJkAb7fSDM/PPzIe5B+vazbdSK+sg+3wP1+1aaVkW2wYyKFojAUHeCKUhg20nTdULSihJ4v9rr+JN2wCCqlMVZEAZ4ZwRGoQsSDjhgMBuetUZ0UjIYDGfLSbvllcYECirjJeKnPB0SnxOQPC1l7K8cwNkwDDxHyDotKzqB5kc5Uo46VQjrRNN5wMI4xeEX7B0QqIsSLIwwiwGBO1aq1o41fqCPnJrxpSH364VvldAYKQrO13VJ7UAwaMfQLDRrZVlL5yPTjEdvbjWCT8hmmA2KsbKUnRd3+6kn8x0J3tR+2HuRF82ovcZGSZsfCxdKRrnEwSYpqdORvngZHG7KMrl0Pna+d1WuQEQFJfzWXmp/RY/jrYBwUwradzD2/eNkXawj/phIZ8+5MXVzRQQXKva9+StrsaWS60a5aj3eSqwQ4FeVsoujJW9H+yVcHKpfuY8XUOcEZ6xCNM0jWj8Hwkz7mMLK+3QrFt9YAL26IXBM5USZnIjlP4uhslcGWHOGQz5/B9lkIU4PIcwpJg8H0JyfHyEMCA4fpUQJjR6GsKb+ezFIVzlt5PLafH29wsYsuQNoYSH5/TRv4w++gR9NMBxeH4Cj8rz6Ev4ryeQ4ih+jfTFfziBq/zWcwH7L/sfAQAA//91uy8L";
		byte[] decodedData = Base64.getDecoder().decode(data);
		InputStream fis = new ByteArrayInputStream(decodedData);
		
		String decodedString = gzipToString1(fis);
		System.out.println("Decompressed data " + decodedString);
	}

	private String gzipToString1(InputStream fis) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try (InputStream fromIs = new GZIPInputStream(fis)) {
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fromIs.read(buffer)) > 0) {
				os.write(buffer, 0, len);
			}
		} finally {
			os.close();
			fis.close();
		}
		return os.toString("UTF-8");
	}

	
	public static String decryptData(InputStream gzip) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		// Read and decompress the data
		byte[] readBuffer = new byte[5000];
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(os.toByteArray());
		GZIPInputStream inputStream = new GZIPInputStream(gzip);
		int read = inputStream.read(readBuffer, 0, readBuffer.length);
		inputStream.close();
		// Should hold the original (reconstructed) data
		byte[] result = Arrays.copyOf(readBuffer, read);

		// Decode the bytes into a String
		String message = new String(result, "UTF-8");

		System.out.println("UnCompressed Message length : " + message.length());
		return message;
	}

	
	

}
