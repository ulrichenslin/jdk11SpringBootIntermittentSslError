package com.jdk11.ssl.jdk11Client;

import com.jdk11.ssl.sslWebServer.TomcatManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.*;


public class Jdk11Test {


    @BeforeClass
    public static void startTomcat() throws Exception {
        new TomcatManager().start();
    }

    @Test
    public void http2Test() throws Exception {
        HttpResponse<String> response = new Jdk11Client().call(Jdk11LoadTest.url);
        Assert.assertEquals("TLSv1.3", response.sslSession().get().getProtocol().toString());
        Assert.assertEquals("HTTP_2", response.version().toString());
    }

    /**
     * Observe the log while threads complete.  The first few calls always fails
     *
     * @throws Exception
     */
    @Test
    public void http2LoadTest() throws InterruptedException, ExecutionException, TimeoutException {
        new Jdk11LoadTest().http2LoadTest();
    }

}