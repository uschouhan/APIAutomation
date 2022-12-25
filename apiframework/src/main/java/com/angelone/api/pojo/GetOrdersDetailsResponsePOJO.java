package com.angelone.api.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetOrdersDetailsResponsePOJO {

	private List<OrdersDetailsData> data;

    private String message;

    private String errorcode;

    private String status;
}
