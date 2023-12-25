package com.angelone.testdataMapper;

import com.angelone.api.pojo.GttCancelOrderPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class GttCancelOrderMapper {
	private GttCancelOrderMapper() {
	}

	@SneakyThrows
	public static GttCancelOrderPOJO setGttCancelOrderData(String id,String symbolToken,String exchange) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/gttCancelOrder.json"),
						GttCancelOrderPOJO.class)
				.setId(id)
				.setSymboltoken(symbolToken)
				.setExchange(exchange);

	}
	
}
