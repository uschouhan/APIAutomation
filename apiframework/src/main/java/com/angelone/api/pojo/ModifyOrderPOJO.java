package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ModifyOrderPOJO {

	 private String disclosedquantity;
	 private String duration;
	 private String exchange;
	 private String multiplier;
	 private String orderValidityDate;
	 private String orderid;
	 private String ordertype;
	 private String precision;
	 private String price;
	 private String quantity;
	 private String symboltoken;
	 private String triggerprice;
	 private String variety;
}
