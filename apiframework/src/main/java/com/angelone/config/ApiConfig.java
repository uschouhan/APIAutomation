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

  @Key("LOGIN_MPIN")
  String loginMpinEndpoint();
  
  @Key("PLACE_ORDER_ENDPOINT")
  String orderEndpoint();
  
  @Key("LTP_PRICE_ENDPOINT")
  String ltpPriceEndpoint();
  
  @Key("CANCEL_ORDER_ENDPOINT")
  String cancelOrderEndpoint();
  
  @Key("GET_ORDER_BOOK_ENDPOINT")
  String getOrderBookEndpoint();
  
  @Key("GET_POSITIONS_ENDPOINT")
  String getPositionEndpoint();
  
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
  
  @Key("WATCHLIST_BASE_URL")
  String watchlistEndpoint();
  
  @Key("GET_WATCHLIST")
  String getWatchlistEndpoint();
  
  @Key("CHARTS_EQUITY_BASE_URL")
  String chartsEquityBaseURL();
  
  @Key("BSE_EQUITY_CHARTS")
  String getBSEequityEndpoint();
  
  @Key("NSE_EQUITY_CHARTS")
  String getNSEequityEndpoint();
  
  @Key("NSE_CURRENCY_CHARTS")
  String getNSECurrencyEndpoint();
  
  @Key("PORTFOLIO_BASE_URL")
  String getPortfolioBaseURL();
  
  @Key("GET_HOLDING_ENDPOINT")
  String getHoldingEndpoint();
  
}
