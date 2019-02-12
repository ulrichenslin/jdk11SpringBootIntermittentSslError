package com.jdk11.ssl.sslWebServer;

import com.jdk11.ssl.jdk11Client.Jdk11LoadTest;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Properties;

public class TomcatManager {

    ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        TomcatManager tm =new TomcatManager().start();
        try {
            new Jdk11LoadTest().http2LoadTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tm.stop();
    }

    public TomcatManager start(){

        ctx = SpringApplication.run(SslWebServerApplication.class);

        loadTrustStore();

        return this;
    }

    public void stop()  {
        ctx.close();
    }


    static final String DEBUG = "all";  // ssl, all
    public static final String KEY_STORE_FILE = "./certs/server/server.jks";
    public static final String SECRET = "secret";
    public static final String TYPE = "jks";

    private static void loadTrustStore() {

        Properties systemProps = System.getProperties();
        systemProps.put("javax.net.ssl.trustStorePassword", SECRET);
        systemProps.put("javax.net.ssl.trustStore", KEY_STORE_FILE);
        systemProps.put("javax.net.ssl.trustStoreType", TYPE);
        systemProps.put("javax.net.debug", DEBUG);
    }
}

