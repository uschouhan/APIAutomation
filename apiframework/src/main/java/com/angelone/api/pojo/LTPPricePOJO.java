package com.angelone.api.pojo;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class LTPPricePOJO {

  private String exchange;
  private List<String> tokens;
}
