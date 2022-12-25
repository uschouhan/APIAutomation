package com.angelone.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdersDetailsData {

	 	private String expirydate;

	    private String clientID;

	    private String orderstatus;

	    private String exchtime;

	    private String precision;

	    private String filledshares;

	    private String stopLossNestOrdNum;

	    private String takeprofitPrice;

	    private String producttype;

	    private String instrumenttype;

	    private String nestOrdNum;

	    private String cta;

	    private String price;

	    private String averageprice;

	    private String segment;

	    private String guiOrgOrderId;

	    private String tradingsymbol;

	    private String amoOrderId;

	    private String symbolName;

	    private String strikeprice;

	    private String text;

	    private String symbolDesc;

	    private String symboltoken;

	    private String ordertag;

	    private String multiplier;

	    private String isMainOrderPartiallyExecuted;

	    private String takeprofitNestOrdNum;

	    private String classification;

	    private String disclosedquantity;

	    private String rejectionBy;

	    private String exchange;

	    private String guiOrderId;

	    private String ordertype;

	    private String status;

	    private String symbolGroup;

	    private String optiontype;

	    private String orderValidityDate;

	    private String triggerprice;

	    private String isAMO;

	    private String takeProfitOrdStatus;

	    private String filltime;

	    private String execBroker;

	    private String transactiontype;

	    private String tickSize;

	    private String reqID;

	    private String duration;

	    private String Parentorderid;

	    private String stopLossOrdStatus;

	    private String fillid;

	    private String cancelsize;

	    private String exchangeOrderId;

	    private String syomOrderId;

	    private String quantity;

	    private String isTakeProfit;

	    private String squareoff;

	    private String stoploss;

	    private String orderid;

	    private String isStopLoss;

	    private String unfilledshares;

	    private String trailingstoploss;

	    private String lotsize;

	    private String exchorderupdatetime;

	    private String colorCode;

	    private String strategyCode;

	    private String strategyID;

	    private String updatetime;

	    private String user;

	    private String isin;
}
