package com.angelone.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
  "system:properties",
  "system:env",
  "file:${user.dir}/src/test/resources/api-config-prod.properties",
  "file:${user.dir}/src/test/resources/api-config-uat.properties",
  "file:${user.dir}/src/test/resources/api-data.properties"
})
public interface ApiConfig extends Config {
	
	@DefaultValue("prod")
	String environment();

  @Key("${environment}.BASE_URL")
  String apiBaseUrl();

  @Key("${environment}.TOKEN_ENDPOINT")
  String tokenEndpoint();

  @Key("${environment}.LOGIN_MPIN")
  String loginMpinEndpoint();
  
  @Key("${environment}.PLACE_ORDER_ENDPOINT")
  String orderEndpoint();
  
  @Key("${environment}.LTP_PRICE_ENDPOINT")
  String ltpPriceEndpoint();
  
  @Key("${environment}.CANCEL_ORDER_ENDPOINT")
  String cancelOrderEndpoint();
  
  @Key("${environment}.MODIFY_ORDER_ENDPOINT")
  String modifyOrderEndpoint();
  
  @Key("${environment}.GET_ORDER_BOOK_ENDPOINT")
  String getOrderBookEndpoint();
  
  @Key("${environment}.GET_ORDER_STATUS")
  String getOrderStatus();
  
  @Key("${environment}.GET_POSITIONS_ENDPOINT")
  String getPositionEndpoint();
  
  @Key("${environment}.TRADE_BASE_URL")
  String tradeBaseUrl();
  
  @Key("${environment}.GET_INSTA_TRADEDETAILS_ENDPOINT")
  String getInstaTradeDetailsEndpoint();
  
  @Key("${environment}.CREATE_BASKET_ENDPOINT")
  String createBasketEndpoint();
  
  @Key("${environment}.GET_BASKET_LIST_ENDPOINT")
  String getBasketListEndpoint();
  
  @Key("${environment}.LOGIN_OTP_ENDPOINT")
  String getLoginOTPEndpoint();
  
  @Key("${environment}.VERIFY_OTP_ENDPOINT")
  String verifyOTPEndpoint();
  
  @Key("${environment}.REFRESH_TOKEN_ENDPOINT")
  String refreshTokenEndpoint();
  
  @Key("${environment}.DISCOVERY_BASE_URL")
  String discoveryBaseUrl();
  
  @Key("${environment}.FUTURE_BUILTUP_HEATMAP_ENDPOINT")
  String futureBuiltupHeatMapEndpoint();
  
  @Key("${environment}.MARKET_MOVERS_BY_MOST")
  String marketMoversByMost();

  @Key("${environment}.SECTOR_HEATMAP_ENDPOINT")
  String sectorHeatmapEndpoint();

  @Key("${environment}.FUTURE_MARKET_IND_ENDPOINT")
  String futureMarketIndEndpoint();

  @Key("${environment}.FUNDAMENTAL_RATIO")
  String fundamentalRatioEndpoint();
  
  @Key("${environment}.STOCK_SHARE_HOLDER")
  String stockShareHolder();
  
  
  @Key("${environment}.WATCHLIST_BASE_URL")
  String watchlistEndpoint();
  
  @Key("${environment}.GET_WATCHLIST")
  String getWatchlistEndpoint();
  
  @Key("${environment}.CHARTS_EQUITY_BASE_URL")
  String chartsEquityBaseURL();
  
  @Key("${environment}.BSE_EQUITY_CHARTS")
  String getBSEequityEndpoint();
  
  @Key("${environment}.NSE_EQUITY_CHARTS")
  String getNSEequityEndpoint();
  
  @Key("${environment}.NSE_CURRENCY_CHARTS")
  String getNSECurrencyEndpoint();
  
  @Key("${environment}.NSE_FNO_CHARTS")
  String getNseFnoEndpoint();
  
  @Key("${environment}.CHARTS_MCX_BASE_URL")
  String chartsMCXBaseURL();
  
  @Key("${environment}.CHARTS_MCX_ENDPOINT")
  String getChartsMCXEndpoint();
  
  @Key("${environment}.PORTFOLIO_BASE_URL")
  String getPortfolioBaseURL();
  
  @Key("${environment}.GET_HOLDING_ENDPOINT")
  String getHoldingEndpoint();
  
  @Key("${environment}.OPTIONS_BASE_URL")
  String getOptionBaseURL();
  
  @Key("${environment}.OPTIONS_ENDPOINT")
  String getOptionEndpoint();
  
  @Key("${environment}.FUND_WITHDRAWAL_BASE_URL")
  String fundWithdrawalBaseUrl();
  
  @Key("${environment}.FUND_WITHDRAWAL_BALANCE_ENDPOINT")
  String fundWithdrawalBalanceEndpoint();
  
  @Key("${environment}.FUND_WITHDRAWAL_ENDPOINT")
  String fundWithdrawalEndpoint();
  
  @Key("${environment}.MARGIN_AMOUNT_BASE_URL")
  String marginAmountBaseURL();
  
  @Key("${environment}.MARGIN_AMOUNT_ENDPOINT")
  String marginAmountEndpoint();
  
  @Key("${environment}.PLEDGE_GETUSERSECURITY_BASE_URL")
  String getUserSecurityBaseURL();
  
  @Key("${environment}.PLEDGE_GETUSERSECURITY_ENDPOINT")
  String getUserSecurityEndpoint();
  
  @Key("${environment}.PLEDGE_GETWITHDRAWSECURITY_BASE_URL")
  String getWithdrawSecurityBaseURL();
  
  @Key("${environment}.PLEDGE_GETWITHDRAWSECURITY_ENDPOINT")
  String getWithdrawSecurityEndpoint();
  
  @Key("${environment}.FUND_RMS_LIMIT_ENDPOINT")
  String getFundRMSLimitEndpoint();

  @Key("${environment}.IPO_BASE_URL")
  String getIpoBaseURL();

  @Key("${environment}.IPO_MASTER_ENDPOINT")
  String getIpoMasterEndpoint();

  @Key("${environment}.PORTFOLIO_ADVISORY_BASE_URL")
  String getPortfolioAdvisoryBaseURL();

  @Key("${environment}.PORTFOLIO_LIST_ENDPOINT")
  String getPortfolioListEndpoint();
  
  @Key("${environment}.PROFILE_ENDPOINT")
  String getProfileEndpoint();

  @Key("${environment}.TOPGAINER_AND_LOSERS")
  String topgaineAndLosers();
  @Key("${environment}.SET_WATCHLIST")
  String setWatchlist();
  
  @Key("${environment}.GETWITHDRAWLIST_BASE_URL")
  String getWithdrawListBaseURL();
  
  @Key("${environment}.GETWITHDRAWLIST_ENDPOINT")
  String getWithdrawListEndpoint();
  
  @Key("${environment}.GETTRANSACTIONMERGEDLIST_ENDPOINT")
  String getTransactionMergedListEndpoint();
  
  @Key("${environment}.GTT_BASE_URL")
  String getGttBaseURL();
  
  @Key("${environment}.GTT_CREATERULE_ENDPOINT")
  String getCreateRuleEndpoint();
  
  @Key("${environment}.GTT_ORDERSTATUS_ENDPOINT")
  String getGttOrderStatusEndpoint();
  
  @Key("${environment}.GTT_CANCELORDER_ENDPOINT")
  String getGttCancelOrderEndpoint();
  
  @Key("${environment}.GTT_ORDERLIST_ENDPOINT")
  String gttOrderlistEndpoint();

  @Key("${environment}.GTT_MODIFYORDER_ENDPOINT")
  String gttModifyOrderEndpoint();
  
  @Key("${environment}.SEARCH_ENDPOINT")
  String getSearchEndpoint();
  
  @Key("${environment}.PLEDGE_GETTRANSACTION_BASE_URL")
  String getPledgeTransactionBaseURL();

  @Key("${environment}.PLEDGE_GETTRANSACTION_ENDPOINT")
  String getPledgeTransactionEndpoint();

  @Key("${environment}.GETUNPLEDGE_TRANSACTION_ENDPOINT")
  String getUnPledgeTransactionEndpoint();

  @Key("${environment}.GETPLEDGE_STATUS_ENDPOINT")
  String getPledgeStatusEndpoint();

  @Key("${environment}.CREATE_PLEDGE_ENDPOINT")
  String createPledgeEndpoint();

  @Key("${environment}.CHECK_OPPOSITE_PENDINGORDER_ENDPOINT")
  String checkOppositePendigOrderEndpoint();

  @Key("${environment}.GETALL_SYMBOL_ENDPOINT")
  String getAllSymbolEndpoint();

  @Key("${environment}.GET_SECURITY_INFO_ENDPOINT")
  String getSecurityInfoEndpoint();
  
  //######### PG API ###########
  @Key("${environment}.PG_TRANSACTION_BASE_URL")
  String getPGTransactionBaseURL();

  @Key("${environment}.PG_TRANSACTION_LIST_ENDPOINT")
  String getPGTransactionlistEndpoint();

  @Key("${environment}.PG_BASE_URL")
  String getPGBaseURL();

  @Key("${environment}.PG_TRANSACTION_ENDPOINT")
  String getPGTransactionEndpoint();

  @Key("${environment}.PG_TRANSACTION_MERGED_ENDPOINT")
  String getPGTransactionMergedEndpoint();

  @Key("${environment}.PG_TRANSACTION_QUICK_ADDFUND_SUGGESTION_ENDPOINT")
  String getPGQuickAddFundsSugestionEndpoint();

  @Key("${environment}.PG_TRANSACTIONS_LIMIT_ENDPOINT")
  String getPGTransactionLimitsEndpoint();

  @Key("${environment}.PG_TRANSACTION_HELP_TOPIC_ENDPOINT")
  String getPGTransactionHelpTopicEndpoint();

  @Key("${environment}.PG_ACTUATOR_BASEURL")
  String getPGActuatorBaseURL();

  @Key("${environment}.PG_ACTUATOR_ANY_ENDPOINT")
  String getPGActuatorAnyEndpoint();

  @Key("${environment}.PG_ACTUATOR_INFO_ENDPOINT")
  String getPGActuatorInfoEndpoint();

  @Key("${environment}.REPORT_EXCHANGE_BASE_URL")
  String reportexchangebaseurl();
  @Key("${environment}.REPORT_EXCHANGE_BASE_ENDPOINT")
  String reportexchangebaseendpoint();


  @Key("${environment}.REPORT_EXCHANGE_BASESCRIPDETAIL_ENDPOINT")

  String reportexchangebasescripdetaisendpoint();
  @Key("${environment}.INSTA_TRADE_BASE_ENDPOINT")
  String instatradebasaendpoint();
  @Key("${environment}.ORDER_CHARGES_ENDPOINT")
  String orderchargesendpoint();
  @Key("${environment}.EQUITY_TRANSACTION_ENDPOINT")
  String equitytransactionendpoint();

  //SIP endpoints
  @Key("${environment}.STOCK_SIP_BASE_URL")
  String stockSipBaseUrl();

  @Key("${environment}.CREATE_STOCK_SIP_ENDPOINT")
  String createStockSipEndpoint();

  //######### Test Data #########
  
  @Key("${environment}.SECRET_KEY")
  String secretKey();
  
  @Key("${environment}.BSE_Equity_Topic")
  String bSE_Equity_Topic_value();
  
  @Key("${environment}.NSE_Equity_Topic")
  String nSE_Equity_Topic_value();
  
  @Key("${environment}.NSE_FNO_Topic")
  String nSE_FNO_Topic_value();
  
  @Key("${environment}.MCX_TOPIC")
  String mcx_Topic_value();
  
  @Key("${environment}.NSE_CURRENCY_Topic")
  String nSE_CURRENCY_Topic_value();
  
  @Key("${environment}.DURATION_TYPE")
  String durationType();
  
  @Key("${environment}.DURATION")
  Integer duration();
  
  @Key("${environment}.CURRENCY_SYMBOL")
  String currencySymbol();
  
  @Key("${environment}.CURRENCY_SYMBOL_TOKEN")
  String currencySymbolToken();
  
  @Key("${environment}.FNO_SYMBOL")
  String fnoSymbol();
  
  @Key("${environment}.FNO_SYMBOL_TOKEN")
  String fnoSymbolToken();
  
  @Key("${environment}.COMODITY_SYMBOL")
  String comoditySymbol();
  
  @Key("${environment}.COMODITY_SYMBOL_TOKEN")
  String comoditySymbolToken();
  
  @Key("${environment}.COMODITY_SYMBOL_NCDEX")
  String comodityNcdexSymbol();
  
  @Key("${environment}.COMODITY_SYMBOL_NCDEX_TOKEN")
  String comodityNcdexSymbolToken();
}
