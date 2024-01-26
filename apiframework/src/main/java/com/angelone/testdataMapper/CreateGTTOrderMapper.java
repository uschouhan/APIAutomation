package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.CreateGttOrderPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class CreateGTTOrderMapper {
	private CreateGTTOrderMapper() {
	}

	@SneakyThrows
	public static CreateGttOrderPOJO placeGTTOrder() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/createGttOrder.json"),
						CreateGttOrderPOJO.class);

	}

	
	@SneakyThrows
	public static CreateGttOrderPOJO placeGTTOrder(String clientId,String disclosedqty,String exchange,String price,
			String producttype,String qty,String symboltoken,String timeperiod,String tradingsymbol,String transactiontype,String triggerprice,String strategyCode) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/createGttOrder.json"),
						CreateGttOrderPOJO.class)
				.setClientid(clientId)	
				.setDisclosedqty(Integer.valueOf(disclosedqty))
				.setExchange(exchange)
				.setPrice(Integer.valueOf(price))
				.setProducttype(producttype)
				.setQty(Integer.valueOf(qty))
				.setSymboltoken(symboltoken)
				.setTimeperiod(Integer.valueOf(timeperiod))
				.setTradingsymbol(tradingsymbol)
				.setTransactiontype(transactiontype)
				.setTriggerprice(Integer.valueOf(triggerprice))
				.setStrategyCode(strategyCode);	
	}
	
	@SneakyThrows
	public static CreateGttOrderPOJO placeGTTOrder(String clientId,String exchange,String price,
			String producttype,String qty,String symboltoken,String tradingsymbol,String triggerprice) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/createGttOrder.json"),
						CreateGttOrderPOJO.class)
				.setClientid(clientId)	
				.setExchange(exchange)
				.setPrice(Integer.valueOf(price))
				.setProducttype(producttype)
				.setQty(Integer.valueOf(qty))
				.setSymboltoken(symboltoken)
				.setTradingsymbol(tradingsymbol)
				.setTriggerprice(Integer.valueOf(triggerprice));		
				
	}
		
}
