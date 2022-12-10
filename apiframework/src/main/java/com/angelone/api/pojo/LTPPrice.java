package com.angelone.api.pojo;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class LTPPrice {

  private String exchange;
  private List<String> tokens;
}
