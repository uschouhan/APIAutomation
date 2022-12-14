package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CancelOrderPOJO {

  private String orderid;
  private String variety;
}
