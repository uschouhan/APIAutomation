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
  
  @Key("GET_ORDER_STATUS")
  String getOrderStatus();
  
  @Key("GET_POSITIONS_ENDPOINT")
  String getPositionEndpoint();
  
  @Key("TRADE_BASE_URL")
  String tradeBaseUrl();
  
  @Key("GET_INSTA_TRADEDETAILS_ENDPOINT")
  String getInstaTradeDetailsEndpoint();
  
  @Key("CREATE_BASKET_ENDPOINT")
  String createBasketEndpoint();
  
  @Key("LOGIN_OTP_ENDPOINT")
  String getLoginOTPEndpoint();
  
  @Key("VERIFY_OTP_ENDPOINT")
  String verifyOTPEndpoint();
  
  @Key("REFRESH_TOKEN_ENDPOINT")
  String refreshTokenEndpoint();
  
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
  
  @Key("STOCK_SHARE_HOLDER")
  String stockShareHolder();
  
  
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
  
  @Key("FUND_WITHDRAWAL_BALANCE_ENDPOINT")
  String fundWithdrawalBalanceEndpoint();
  
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
  
  @Key("PROFILE_ENDPOINT")
  String getProfileEndpoint();

  @Key("TOPGAINER_AND_LOSERS")
  String topgaineAndLosers();
  @Key("SET_WATCHLIST")
  String setWatchlist();
  
  @Key("GETWITHDRAWLIST_BASE_URL")
  String getWithdrawListBaseURL();
  
  @Key("GETWITHDRAWLIST_ENDPOINT")
  String getWithdrawListEndpoint();
  
  @Key("GETTRANSACTIONMERGEDLIST_ENDPOINT")
  String getTransactionMergedListEndpoint();
  
  @Key("GTT_BASE_URL")
  String getGttBaseURL();
  
  @Key("GTT_CREATERULE_ENDPOINT")
  String getCreateRuleEndpoint();
  
  @Key("GTT_ORDERSTATUS_ENDPOINT")
  String getGttOrderStatusEndpoint();
  
  @Key("GTT_CANCELORDER_ENDPOINT")
  String getGttCancelOrderEndpoint();
  
  @Key("GTT_ORDERLIST_ENDPOINT")
  String gttOrderlistEndpoint();

  @Key("GTT_MODIFYORDER_ENDPOINT")
  String gttModifyOrderEndpoint();
  
  @Key("SEARCH_ENDPOINT")
  String getSearchEndpoint();
  
  @Key("PLEDGE_GETTRANSACTION_BASE_URL")
  String getPledgeTransactionBaseURL();

  @Key("PLEDGE_GETTRANSACTION_ENDPOINT")
  String getPledgeTransactionEndpoint();

  @Key("GETUNPLEDGE_TRANSACTION_ENDPOINT")
  String getUnPledgeTransactionEndpoint();

  @Key("GETPLEDGE_STATUS_ENDPOINT")
  String getPledgeStatusEndpoint();

  @Key("CREATE_PLEDGE_ENDPOINT")
  String createPledgeEndpoint();

  @Key("CHECK_OPPOSITE_PENDINGORDER_ENDPOINT")
  String checkOppositePendigOrderEndpoint();

  @Key("GETALL_SYMBOL_ENDPOINT")
  String getAllSymbolEndpoint();

  @Key("GET_SECURITY_INFO_ENDPOINT")
  String getSecurityInfoEndpoint();
  
  //######### PG API ###########
  @Key("PG_TRANSACTION_BASE_URL")
  String getPGTransactionBaseURL();

  @Key("PG_TRANSACTION_LIST_ENDPOINT")
  String getPGTransactionlistEndpoint();

  @Key("PG_BASE_URL")
  String getPGBaseURL();

  @Key("PG_TRANSACTION_ENDPOINT")
  String getPGTransactionEndpoint();

  @Key("PG_TRANSACTION_MERGED_ENDPOINT")
  String getPGTransactionMergedEndpoint();

  @Key("PG_TRANSACTION_QUICK_ADDFUND_SUGGESTION_ENDPOINT")
  String getPGQuickAddFundsSugestionEndpoint();

  @Key("PG_TRANSACTIONS_LIMIT_ENDPOINT")
  String getPGTransactionLimitsEndpoint();

  @Key("PG_TRANSACTION_HELP_TOPIC_ENDPOINT")
  String getPGTransactionHelpTopicEndpoint();

  @Key("PG_ACTUATOR_BASEURL")
  String getPGActuatorBaseURL();

  @Key("PG_ACTUATOR_ANY_ENDPOINT")
  String getPGActuatorAnyEndpoint();

  @Key("PG_ACTUATOR_INFO_ENDPOINT")
  String getPGActuatorInfoEndpoint();
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

  @Key("REPORT_EXCHANGE_BASE_URL")
  String reportexchangebaseurl();
  @Key("REPORT_EXCHANGE_BASE_ENDPOINT")
  String reportexchangebaseendpoint();


  @Key("REPORT_EXCHANGE_BASESCRIPDETAIL_ENDPOINT")

  String reportexchangebasescripdetaisendpoint();
  @Key("INSTA_TRADE_BASE_ENDPOINT")
  String instatradebasaendpoint();
  @Key("ORDER_CHARGES_ENDPOINT")
  String orderchargesendpoint();
  @Key("EQUITY_TRANSACTION_ENDPOINT")
  String equitytransactionendpoint();
}
