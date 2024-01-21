package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.ModifyGttOrderPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class ModifyGttOrderMapper {
	private ModifyGttOrderMapper() {
	}

	@SneakyThrows
	public static ModifyGttOrderPOJO modifyGttOrderData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/ModifyGttOrder.json"),
						ModifyGttOrderPOJO.class);

	}

	
	@SneakyThrows
	public static ModifyGttOrderPOJO modifyGttOrderData(String exchange,String id,String price,
			String qty,String symboltoken,String triggerprice,String timeperiod,String disclosedqty,String strategyCode) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/ModifyGttOrder.json"),
						ModifyGttOrderPOJO.class)
				.setDisclosedqty(Integer.valueOf(disclosedqty))
				.setExchange(exchange)
				.setPrice(Integer.valueOf(price))
				.setQty(Integer.valueOf(qty))
				.setSymboltoken(symboltoken)
				.setTimeperiod(Integer.valueOf(timeperiod))
				.setTriggerprice(Integer.valueOf(triggerprice))
				.setStrategyCode(strategyCode)
				.setId(Integer.valueOf(id));
	}
	
	@SneakyThrows
	public static ModifyGttOrderPOJO modifyGttOrderData(String exchange,String id,String price,
			String qty,String symboltoken,String triggerprice) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/ModifyGttOrder.json"),
						ModifyGttOrderPOJO.class)
				.setId(Integer.valueOf(id))	
				.setExchange(exchange)
				.setPrice(Integer.valueOf(price))
				.setQty(Integer.valueOf(qty))
				.setSymboltoken(symboltoken)
				.setTriggerprice(Integer.valueOf(triggerprice));
	}
		
}
