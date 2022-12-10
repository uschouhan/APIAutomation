package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.PlaceOrderDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class PlaceOrderTestData {
	private PlaceOrderTestData() {
	}

	@SneakyThrows
	public static PlaceOrderDetails placeOrder() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/placeOrder.json"),
						PlaceOrderDetails.class);

	}

	@SneakyThrows
	public static PlaceOrderDetails placeOrder(String basketID, String disclosedquantity,String duration,String exchange,String multiplier,
			String orderValidityDate,String ordertag,String ordertype,String precision,String price,String producttype,
			String quantity,String squareoff,String stoploss,String strategyCode,String symboltoken,String tickSize,String tradingsymbol,
			String trailTickYesNo,String trailingStopLoss,String transactiontype,String triggerprice,String variety) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/placeOrder.json"),
						PlaceOrderDetails.class)
				.setBasketID(basketID)
				.setDisclosedquantity(disclosedquantity)
				.setDuration(duration)
				.setExchange(exchange)
				.setMultiplier(multiplier)
				.setOrderValidityDate(orderValidityDate)
				.setOrdertag(ordertag)
				.setOrdertype(ordertype)
				.setPrecision(precision)
				.setPrice(price)
				.setProducttype(producttype)
				.setQuantity(quantity)
				.setSquareoff(squareoff)
				.setStoploss(stoploss)
				.setStrategyCode(strategyCode)
				.setSymboltoken(symboltoken)
				.setTickSize(tickSize)
				.setTradingsymbol(tradingsymbol)
				.setTrailTickYesNo(trailTickYesNo)
				.setTrailingStopLoss(trailingStopLoss)
				.setTransactiontype(transactiontype)
				.setVariety(variety);
				
	}
	
	@SneakyThrows
	public static PlaceOrderDetails placeOrder(String exchange,String ordertype,String price,String producttype,
			String quantity,String stoploss,String symboltoken,String tradingsymbol,String transactiontype,String triggerprice,String variety) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/placeOrder.json"),
						PlaceOrderDetails.class)
				.setExchange(exchange)
				.setOrdertype(ordertype)
				.setPrice(price)
				.setProducttype(producttype)
				.setQuantity(quantity)
				.setStoploss(stoploss)
				.setSymboltoken(symboltoken)
				.setTradingsymbol(tradingsymbol)
				.setTransactiontype(transactiontype)
				.setVariety(variety);
				
	}
	
	@SneakyThrows
	public static PlaceOrderDetails placeOrder(String ordertype,String price,String producttype,
			String symboltoken,String variety) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/placeOrder.json"),
						PlaceOrderDetails.class)
				.setOrdertype(ordertype)
				.setPrice(price)
				.setProducttype(producttype)
				.setSymboltoken(symboltoken)
				.setVariety(variety);
				
	}
	
	
}
