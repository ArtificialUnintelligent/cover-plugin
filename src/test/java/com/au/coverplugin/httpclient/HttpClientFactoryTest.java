package com.au.coverplugin.httpclient;

import org.apache.http.client.HttpClient;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 5:56 PM 2019/1/23
 */
public class HttpClientFactoryTest {

    @Test
    public void createTest(){
        HttpClientFactory factory = new HttpClientFactory();
        HttpClient client = factory.create();
        Assert.assertNotNull(client);
    }

}
