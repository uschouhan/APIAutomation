package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class FundRmsLimitPOJO {

  private String exchange;
  private String product;
  private String segment;
  
}
