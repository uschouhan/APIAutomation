package com.angelone.testdataMapper;

import com.angelone.api.pojo.CreateStockSIPPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class CreateStockSIPMapper {
	private CreateStockSIPMapper() {
	}

	@SneakyThrows
	public static CreateStockSIPPOJO stockSIP() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/createStockSIP.json"),
						CreateStockSIPPOJO.class);
	}

	@SneakyThrows
	public static CreateStockSIPPOJO stockSIP(String instrument_type,String instrument_id,String instrument_name,String instrument_trade_symbol,String instrument_symbol
	,String order_value,String frequency,String order_type,String exchange,String start_date) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/createStockSIP.json"),
						CreateStockSIPPOJO.class)
				.setInstrument_type(instrument_type)
				.setInstrument_id(instrument_id)
				.setInstrument_name(instrument_name)
				.setInstrument_trade_symbol(instrument_trade_symbol)
				.setInstrument_symbol(instrument_symbol)
				.setOrder_value(Integer.valueOf(order_value))
				.setFrequency(frequency)
				.setOrder_type(order_type)
				.setExchange(exchange)
				.setStart_date(start_date);

	}

}
