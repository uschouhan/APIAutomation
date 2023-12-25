package com.angelone.testdataMapper;


import com.angelone.api.pojo.CheckOppositePendingOrderPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class CheckOppositePendingOrder {
	private CheckOppositePendingOrder() {
	}

	@SneakyThrows
	public static CheckOppositePendingOrderPOJO getOppositePendingOrderData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/checkOppositePendingOrder.json"),
						CheckOppositePendingOrderPOJO.class);

	}

	
	@SneakyThrows
	public static CheckOppositePendingOrderPOJO getOppositePendingOrderData(String stockxchangecode,String symboltoken, String productType,String transactionType,String qty ) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/checkOppositePendingOrder.json"),
						CheckOppositePendingOrderPOJO.class)
				.setExchange(stockxchangecode)
		.setSymbolToken(symboltoken)
		.setProductType(productType)
		.setTransactionType(transactionType)
		.setQty(qty);

			
	}
}
