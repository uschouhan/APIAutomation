package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.CreateBasketPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class CreateBasketMapper {
	private CreateBasketMapper() {
	}

	@SneakyThrows
	public static CreateBasketPOJO createBasketForEquity(String token, String scripExchg, String exchgName,
			String scripIsin, String symbolName, String details, String expiryDate, String tradeSymbol,
			String producttype, String exchange, String ordertype, String price, Integer qty, String variety) {
		CreateBasketPOJO readValue = new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/basketOrderCash.json"),
						CreateBasketPOJO.class);
		readValue.setToken(token);
		readValue.setScripCode(token);
		readValue.setScripExchg(scripExchg);
		readValue.setExchgName(exchgName);
		readValue.setScripIsin(scripIsin);
		readValue.setScripToken(token);
		readValue.setSymbolName(symbolName);
		readValue.setDetails(details);
		readValue.setTradeSymbol(tradeSymbol);
		readValue.setIsinCode(scripIsin);
		readValue.setProducttype(producttype);
		readValue.setExpirydate(expiryDate);
		readValue.setExchange(exchange);
		readValue.setOrdertype(ordertype);
		readValue.setPrice(price);
		readValue.setQuantity(qty);
		readValue.setSymboltoken(token);
		readValue.setTradeSymbol(tradeSymbol);
		readValue.setVariety(variety);
		return readValue;
	}
	

	@SneakyThrows
	public static CreateBasketPOJO createBasketForFNO(String token, String scripExchg, String exchgName,
			String scripIsin, String symbolName, String details, String expiryDate, String tradeSymbol,
			String producttype, String exchange, String ordertype, String price, Integer qty, String variety) {
		
		CreateBasketPOJO readValue = new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/basketOrderFNO.json"),
						CreateBasketPOJO.class);
		readValue.setToken(token);
		readValue.setScripCode(token);
		readValue.setScripExchg(scripExchg);
		readValue.setExchgName(exchgName);
		readValue.setScripIsin(scripIsin);
		readValue.setScripToken(token);
		readValue.setSymbolName(symbolName);
		readValue.setDetails(expiryDate);
		readValue.setTradeSymbol(tradeSymbol);
		readValue.setIsinCode(scripIsin);
		readValue.setProducttype(producttype);
		readValue.setExpirydate(expiryDate);
		readValue.setExchange(exchange);
		readValue.setOrdertype(ordertype);
		readValue.setPrice(price);
		readValue.setQuantity(qty);
		readValue.setSymboltoken(token);
		readValue.setTradeSymbol(tradeSymbol);
		readValue.setVariety(variety);
		return readValue;
	}
	

	@SneakyThrows
	public static CreateBasketPOJO createBasketForCommodityMCX(String token,String scripExchg,String exchgName,String scripIsin,String symbolName,String details,String expiryDate,
			String tradeSymbol,String producttype,String exchange,String ordertype,String price,Integer qty,String variety) {
		
		CreateBasketPOJO readValue = new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/basketOrderCommodityMCX.json"),
						CreateBasketPOJO.class);
		readValue.setToken(token);
		readValue.setScripCode(token);
		readValue.setScripExchg(scripExchg);
		readValue.setExchgName(exchgName);
		readValue.setScripIsin(scripIsin);
		readValue.setScripToken(token);
		readValue.setSymbolName(symbolName);
		readValue.setDetails(expiryDate);
		readValue.setTradeSymbol(tradeSymbol);
		readValue.setIsinCode(scripIsin);
		readValue.setProducttype(producttype);
		readValue.setExpirydate(expiryDate);
		readValue.setExchange(exchange);
		readValue.setOrdertype(ordertype);
		readValue.setPrice(price);
		readValue.setQuantity(qty);
		readValue.setSymboltoken(token);
		readValue.setTradeSymbol(tradeSymbol);
		readValue.setVariety(variety);
		return readValue;
	
	}
	
	@SneakyThrows
	public static CreateBasketPOJO createBasketForCommodityNCDEX(String token,String scripExchg,String exchgName,String scripIsin,String symbolName,String details,String expiryDate,
			String tradeSymbol,String producttype,String exchange,String ordertype,String price,Integer qty,String variety) {
		
		CreateBasketPOJO readValue = new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/basketOrderCommodityNCDEX.json"),
						CreateBasketPOJO.class);
		readValue.setToken(token);
		readValue.setScripCode(token);
		readValue.setScripExchg(scripExchg);
		readValue.setExchgName(exchgName);
		readValue.setScripIsin(scripIsin);
		readValue.setScripToken(token);
		readValue.setSymbolName(symbolName);
		readValue.setDetails(expiryDate);
		readValue.setTradeSymbol(tradeSymbol);
		readValue.setIsinCode(scripIsin);
		readValue.setProducttype(producttype);
		readValue.setExpirydate(expiryDate);
		readValue.setExchange(exchange);
		readValue.setOrdertype(ordertype);
		readValue.setPrice(price);
		readValue.setQuantity(qty);
		readValue.setSymboltoken(token);
		readValue.setTradeSymbol(tradeSymbol);
		readValue.setVariety(variety);
		return readValue;
		
	}
	
	@SneakyThrows
	public static CreateBasketPOJO createBasketFoCurrency(String token,String scripExchg,String exchgName,String scripIsin,String symbolName,String details,String expiryDate,
			String tradeSymbol,String producttype,String exchange,String ordertype,String price,Integer qty,String variety) {
		CreateBasketPOJO readValue = new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/basketOrderCurrency.json"),
						CreateBasketPOJO.class);
		readValue.setToken(token);
		readValue.setScripCode(token);
		readValue.setScripExchg(scripExchg);
		readValue.setExchgName(exchgName);
		readValue.setScripIsin(scripIsin);
		readValue.setScripToken(token);
		readValue.setSymbolName(symbolName);
		readValue.setDetails(expiryDate);
		readValue.setTradeSymbol(tradeSymbol);
		readValue.setIsinCode(scripIsin);
		readValue.setProducttype(producttype);
		readValue.setExpirydate(expiryDate);
		readValue.setExchange(exchange);
		readValue.setOrdertype(ordertype);
		readValue.setPrice(price);
		readValue.setQuantity(qty);
		readValue.setSymboltoken(token);
		readValue.setTradeSymbol(tradeSymbol);
		readValue.setVariety(variety);
		return readValue;
	}
	
	
}
