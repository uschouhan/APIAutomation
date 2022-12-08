package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserDetails {

  private String userid;
  private String passorpin;
}
