package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class PlaceOrderDetails {

	 private String basketID;
	 private String disclosedquantity;
	 private String duration;
	 private String exchange;
	 private String multiplier;
	 private String orderValidityDate;
	 private String ordertag;
	 private String ordertype;
	 private String precision;
	 private String price;
	 private String producttype;
	 private String quantity;
	 private String squareoff;
	 private String stoploss;
	 private String strategyCode;
	 private String symboltoken;
	 private String tickSize;
	 private String tradingsymbol;
	 private String trailTickYesNo;
	 private String trailingStopLoss;
	 private String transactiontype;
	 private String triggerprice;
	 private String variety;
}
