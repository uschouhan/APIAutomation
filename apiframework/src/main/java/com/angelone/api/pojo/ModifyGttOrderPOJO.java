package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ModifyGttOrderPOJO {

	 private String exchange;
	 private Integer id;
	 private Integer price;
	 private Integer qty;
	 private String  symboltoken;
	 private Integer triggerprice;
	 private Integer timeperiod;
	 private Integer disclosedqty;
	 private String  strategyCode;
}
