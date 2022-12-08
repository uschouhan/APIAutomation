package com.angelone.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
  "system:properties",
  "system:env",
  "file:${user.dir}/src/test/resources/api-config.properties"
})
public interface ApiConfig extends Config {

  @Key("BASE_URL")
  String apiBaseUrl();

  @Key("TOKEN_ENDPOINT")
  String tokenEndpoint();

  @Key("PLACE_ORDER_ENDPOINT")
  String orderEndpoint();
}
