package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class GttCancelOrderPOJO {

  private String id;
  private String symboltoken;
  private String exchange;
  
}
