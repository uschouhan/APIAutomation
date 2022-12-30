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
  
  @Key("LTP_PRICE_ENDPOINT")
  String ltpPriceEndpoint();
  
  @Key("CANCEL_ORDER_ENDPOINT")
  String cancelOrderEndpoint();
  
  @Key("GET_ORDER_BOOK_ENDPOINT")
  String getOrderBookEndpoint();
  
  @Key("TRADE_BASE_URL")
  String tradeBaseUrl();
  
  @Key("LOGIN_OTP_ENDPOINT")
  String getLoginOTPEndpoint();
  
  @Key("VERIFY_OTP_ENDPOINT")
  String verifyOTPEndpoint();
  
  @Key("DISCOVERY_BASE_URL")
  String discoveryBaseUrl();
  
  @Key("FUTURE_BUILTUP_HEATMAP_ENDPOINT")
  String futureBuiltupHeatMapEndpoint();
  
  
}
