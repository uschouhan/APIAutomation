package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.PlaceOrderDetailsPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class PlaceOrderTestData {
	private PlaceOrderTestData() {
	}

	@SneakyThrows
	public static PlaceOrderDetailsPOJO placeOrder() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/placeOrder.json"),
						PlaceOrderDetailsPOJO.class);

	}

	@SneakyThrows
	public static PlaceOrderDetailsPOJO placeOrder(String basketID, String disclosedquantity,String duration,String exchange,String multiplier,
			String orderValidityDate,String ordertag,String ordertype,String precision,String price,String producttype,
			String quantity,String squareoff,String stoploss,String strategyCode,String symboltoken,String tickSize,String tradingsymbol,
			String trailTickYesNo,String trailingStopLoss,String transactiontype,String triggerprice,String variety) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/placeOrder.json"),
						PlaceOrderDetailsPOJO.class)
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
	public static PlaceOrderDetailsPOJO placeOrder(String exchange,String ordertype,String price,String producttype,
			String quantity,String stoploss,String symboltoken,String tradingsymbol,String transactiontype,String triggerprice,String variety) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/placeOrder.json"),
						PlaceOrderDetailsPOJO.class)
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
	public static PlaceOrderDetailsPOJO placeOrder(String ordertype,String price,String producttype,
			String symboltoken,String variety) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/placeOrder.json"),
						PlaceOrderDetailsPOJO.class)
				.setOrdertype(ordertype)
				.setPrice(price)
				.setProducttype(producttype)
				.setSymboltoken(symboltoken)
				.setVariety(variety);
				
	}
	

	@SneakyThrows
	public static PlaceOrderDetailsPOJO placeOrder(String ordertype,String price,String producttype,
			String symboltoken,String tradingsymbol,String variety) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/placeOrder.json"),
						PlaceOrderDetailsPOJO.class)
				.setOrdertype(ordertype)
				.setPrice(price)
				.setProducttype(producttype)
				.setSymboltoken(symboltoken)
				.setTradingsymbol(tradingsymbol)
				.setVariety(variety);
				
	}
	
}
