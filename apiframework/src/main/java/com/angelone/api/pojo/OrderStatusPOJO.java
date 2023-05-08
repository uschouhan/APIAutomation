package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class OrderStatusPOJO {

  private String gui_order_id;
  private String order_id;
}
