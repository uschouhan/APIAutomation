package com.angelone.api.pojo;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class GttOrderListPOJO {

  private Integer count;
  private Integer page;
  private List<String> status;
  
}
