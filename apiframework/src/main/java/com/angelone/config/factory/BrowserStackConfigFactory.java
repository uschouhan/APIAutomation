package com.angelone.config.factory;


import org.aeonbits.owner.ConfigCache;

import com.angelone.config.BrowserStackConfig;

public final class BrowserStackConfigFactory {

  private BrowserStackConfigFactory() {
  }

  public static BrowserStackConfig getConfig() {
    return ConfigCache.getOrCreate(BrowserStackConfig.class);
  }
}
