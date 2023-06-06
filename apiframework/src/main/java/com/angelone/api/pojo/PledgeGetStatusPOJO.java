package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class PledgeGetStatusPOJO {

  private String party_code;
  private String req_id;
  
}
