package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CreateGttOrderPOJO {

	 private String clientid;
	 private Integer disclosedqty;
	 private String exchange;
	 private Integer price;
	 private String producttype;
	 private Integer qty;
	 private String symboltoken;
	 private Integer timeperiod;
	 private String tradingsymbol;
	 private String transactiontype;
	 private Integer triggerprice;
	 private String strategyCode;
}
