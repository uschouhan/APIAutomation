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
  
  @Key("MODIFY_ORDER_ENDPOINT")
  String modifyOrderEndpoint();
  
  @Key("GET_ORDER_BOOK_ENDPOINT")
  String getOrderBookEndpoint();
  
  @Key("GET_POSITIONS_ENDPOINT")
  String getPositionEndpoint();
  
  @Key("TRADE_BASE_URL")
  String tradeBaseUrl();
  
  @Key("GET_INSTA_TRADEDETAILS_ENDPOINT")
  String getInstaTradeDetailsEndpoint();
  
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

  @Key("SECTOR_HEATMAP_ENDPOINT")
  String sectorHeatmapEndpoint();

  @Key("FUTURE_MARKET_IND_ENDPOINT")
  String futureMarketIndEndpoint();

  @Key("FUNDAMENTAL_RATIO")
  String fundamentalRatioEndpoint();
  
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
  
  @Key("CHARTS_MCX_BASE_URL")
  String chartsMCXBaseURL();
  
  @Key("CHARTS_MCX_ENDPOINT")
  String getChartsMCXEndpoint();
  
  @Key("PORTFOLIO_BASE_URL")
  String getPortfolioBaseURL();
  
  @Key("GET_HOLDING_ENDPOINT")
  String getHoldingEndpoint();
  
  @Key("OPTIONS_BASE_URL")
  String getOptionBaseURL();
  
  @Key("OPTIONS_ENDPOINT")
  String getOptionEndpoint();
  
  @Key("FUND_WITHDRAWAL_BASE_URL")
  String fundWithdrawalBaseUrl();
  
  @Key("FUND_WITHDRAWAL_ENDPOINT")
  String fundWithdrawalEndpoint();
  
  @Key("MARGIN_AMOUNT_BASE_URL")
  String marginAmountBaseURL();
  
  @Key("MARGIN_AMOUNT_ENDPOINT")
  String marginAmountEndpoint();
  
  @Key("PLEDGE_GETUSERSECURITY_BASE_URL")
  String getUserSecurityBaseURL();
  
  @Key("PLEDGE_GETUSERSECURITY_ENDPOINT")
  String getUserSecurityEndpoint();
  
  @Key("PLEDGE_GETWITHDRAWSECURITY_BASE_URL")
  String getWithdrawSecurityBaseURL();
  
  @Key("PLEDGE_GETWITHDRAWSECURITY_ENDPOINT")
  String getWithdrawSecurityEndpoint();
  
  @Key("FUND_RMS_LIMIT_ENDPOINT")
  String getFundRMSLimitEndpoint();

  @Key("IPO_BASE_URL")
  String getIpoBaseURL();

  @Key("IPO_MASTER_ENDPOINT")
  String getIpoMasterEndpoint();

  @Key("PORTFOLIO_ADVISORY_BASE_URL")
  String getPortfolioAdvisoryBaseURL();

  @Key("PORTFOLIO_LIST_ENDPOINT")
  String getPortfolioListEndpoint();

  @Key("TOPGAINER_AND_LOSERS")
  String topgaineAndLosers();
  @Key("SET_WATCHLIST")
  String setWatchlist();

  //######### Test Data #########
  
  @Key("SECRET_KEY")
  String secretKey();
  
  @Key("BSE_Equity_Topic")
  String bSE_Equity_Topic_value();
  
  @Key("NSE_Equity_Topic")
  String nSE_Equity_Topic_value();
  
  @Key("NSE_FNO_Topic")
  String nSE_FNO_Topic_value();
  
  @Key("MCX_TOPIC")
  String mcx_Topic_value();
  
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
  
  @Key("COMODITY_SYMBOL_NCDEX")
  String comodityNcdexSymbol();
  
  @Key("COMODITY_SYMBOL_NCDEX_TOKEN")
  String comodityNcdexSymbolToken();
  
}
