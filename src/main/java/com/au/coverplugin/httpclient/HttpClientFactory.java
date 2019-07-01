package com.au.coverplugin.httpclient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 10:44 AM 2019/1/22
 */
public class HttpClientFactory {

    private static final int DEFAULT_CONNECTION_TIMEOUT = 10000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 60000;

    private final HttpClientBuilder hcb = HttpClientBuilder.create();
    private final RequestConfig.Builder rcb = RequestConfig.custom()
        .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
        .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT);

    public HttpClient create(){
        return hcb.setDefaultRequestConfig(rcb.build()).build();
    }
}
