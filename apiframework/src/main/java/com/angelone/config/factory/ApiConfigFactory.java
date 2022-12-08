package com.angelone.config.factory;


import org.aeonbits.owner.ConfigCache;

import com.angelone.config.ApiConfig;

public final class ApiConfigFactory {

  private ApiConfigFactory() {
  }

  public static ApiConfig getConfig() {
    return ConfigCache.getOrCreate(ApiConfig.class);
  }
}
