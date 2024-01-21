package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.CancelOrderPOJO;
import com.angelone.api.pojo.OrderStatusPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class OrderStatusMapper {
	private OrderStatusMapper() {
	}

	@SneakyThrows
	public static OrderStatusPOJO orderStatusData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/orderStatus.json"),
						OrderStatusPOJO.class);

	}

	@SneakyThrows
	public static OrderStatusPOJO orderStatusData(String gui_order_id, String order_id) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/orderStatus.json"),
						OrderStatusPOJO.class)
				.setGui_order_id(gui_order_id)
				.setOrder_id(order_id);
	}
	
}
