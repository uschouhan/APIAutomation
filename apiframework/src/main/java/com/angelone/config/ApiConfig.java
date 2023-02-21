package com.angelone.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
  "system:properties",
  "system:env",
  "file:${user.dir}/src/test/resources/api-config.properties",
  "file:${user.dir}/src/test/resources/api-data.properties"
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
  
  @Key("MARKET_MOVERS_BY_MOST")
  String marketMoversByMost();
  
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
  
  @Key("NSE_FNO_CHARTS")
  String getNseFnoEndpoint();
  
  @Key("PORTFOLIO_BASE_URL")
  String getPortfolioBaseURL();
  
  @Key("GET_HOLDING_ENDPOINT")
  String getHoldingEndpoint();
  
  @Key("OPTIONS_BASE_URL")
  String getOptionBaseURL();
  
  @Key("OPTIONS_ENDPOINT")
  String getOptionEndpoint();
  
  
  
  //######### Test Data #########
  
  @Key("SECRET_KEY")
  String secretKey();
  
  @Key("BSE_Equity_Topic")
  String bSE_Equity_Topic_value();
  
  @Key("NSE_Equity_Topic")
  String nSE_Equity_Topic_value();
  
  @Key("NSE_FNO_Topic")
  String nSE_FNO_Topic_value();
  
  @Key("NSE_CURRENCY_Topic")
  String nSE_CURRENCY_Topic_value();
  
  @Key("DURATION_TYPE")
  String durationType();
  
  @Key("DURATION")
  Integer duration();
  
  @Key("CURRENCY_SYMBOL")
  String currencySymbol();
  
  @Key("CURRENCY_SYMBOL_TOKEN")
  String currencySymbolToken();
  
  @Key("FNO_SYMBOL")
  String fnoSymbol();
  
  @Key("FNO_SYMBOL_TOKEN")
  String fnoSymbolToken();
  
  @Key("COMODITY_SYMBOL")
  String comoditySymbol();
  
  @Key("COMODITY_SYMBOL_TOKEN")
  String comoditySymbolToken();
  
  
}
