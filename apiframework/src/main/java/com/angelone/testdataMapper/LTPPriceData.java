package com.angelone.testdataMapper;

import com.angelone.api.pojo.LTPPricePOJO;
import com.angelone.api.pojo.UserDetailsPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class LTPPriceData {
	private LTPPriceData() {
	}

	@SneakyThrows
	public static LTPPricePOJO getLTPPrice() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/LTPPrice.json"),
						LTPPricePOJO.class);

	}

	@SneakyThrows
	public static LTPPricePOJO getLTPPrice(String exchange, List<String> scriptId) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/LTPPrice.json"),
						LTPPricePOJO.class)
				.setExchange(exchange)
				.setTokens(scriptId);
	}

	@SneakyThrows
	public static LTPPricePOJO getLTPPrice(String scriptId) {
		// TODO Auto-generated method stub
		
		List<String> symbolId = new ArrayList<>();
		symbolId.add(scriptId);
 		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/LTPPrice.json"),
						LTPPricePOJO.class)
				.setTokens(symbolId);

	}
	
	
}
