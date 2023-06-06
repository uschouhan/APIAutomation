package com.angelone.api.pojo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class CheckOppositePendingOrderPOJO {

	    public String exchange;
	    public String symbolToken;
	    public String productType;
	    public String transactionType;
	    public String qty;
}


