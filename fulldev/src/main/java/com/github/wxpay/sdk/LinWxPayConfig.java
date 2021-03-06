package com.github.wxpay.sdk;

import java.io.InputStream;

public class LinWxPayConfig extends WXPayConfig {
    @Override
    public String getAppID() {
        return "";
    }

    @Override
    public String getMchID() {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOWNLOADBILL_URL_SUFFIX, true);
            }
        };
        return iwxPayDomain;

    }
}
