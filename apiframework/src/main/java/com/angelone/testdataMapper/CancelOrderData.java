package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.CancelOrderPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class CancelOrderData {
	private CancelOrderData() {
	}

	@SneakyThrows
	public static CancelOrderPOJO cancelOrder() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/cancelOrder.json"),
						CancelOrderPOJO.class);

	}

	@SneakyThrows
	public static CancelOrderPOJO cancelOrder(String orderId, String variety) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/cancelOrder.json"),
						CancelOrderPOJO.class)
				.setOrderid(orderId)
				.setVariety(variety);
	}
	
}
