package com.angelone.testdataMapper;

import com.angelone.api.pojo.LTPPrice;
import com.angelone.api.pojo.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class LTPPriceData {
	private LTPPriceData() {
	}

	@SneakyThrows
	public static LTPPrice getLTPPrice() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/LTPPrice.json"),
						LTPPrice.class);

	}

	@SneakyThrows
	public static LTPPrice getLTPPrice(String exchange, List<String> scriptId) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/LTPPrice.json"),
						LTPPrice.class)
				.setExchange(exchange)
				.setTokens(scriptId);
	}

	@SneakyThrows
	public static LTPPrice getLTPPrice(String scriptId) {
		// TODO Auto-generated method stub
		
		List<String> symbolId = new ArrayList<>();
		symbolId.add(scriptId);
 		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/LTPPrice.json"),
						LTPPrice.class)
				.setTokens(symbolId);

	}
	
	
}
