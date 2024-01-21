package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.ModifyOrderPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class ModifyOrderMapper {
	private ModifyOrderMapper() {
	}

	@SneakyThrows
	public static ModifyOrderPOJO modifyOrder() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/modifyOrder.json"),
						ModifyOrderPOJO.class);
	}

	@SneakyThrows
	public static ModifyOrderPOJO modifyOrder(String disclosedquantity,String duration,String exchange,String multiplier,
			String orderValidityDate,String orderId,String ordertype,String precision,String price,
			String quantity,String symboltoken,String triggerprice,String variety) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/modifyOrder.json"),
						ModifyOrderPOJO.class)
				.setDisclosedquantity(disclosedquantity)
				.setDuration(duration)
				.setExchange(exchange)
				.setMultiplier(multiplier)
				.setOrderValidityDate(orderValidityDate)
				.setOrderid(orderId)
				.setOrdertype(ordertype)
				.setPrecision(precision)
				.setPrice(price)
				.setQuantity(quantity)
				.setSymboltoken(symboltoken)
				.setTriggerprice(triggerprice)
				.setVariety(variety);
				
	}
	

	
}
