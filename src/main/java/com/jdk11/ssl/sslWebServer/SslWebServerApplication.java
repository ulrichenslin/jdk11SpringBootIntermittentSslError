package com.jdk11.ssl.sslWebServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@SpringBootApplication
@Controller
public class SslWebServerApplication {

    static String staticString = genString();

    static final Integer SLEEP_TIME_MS = 1000;

    public static void main(String[] args) {
        SpringApplication.run(SslWebServerApplication.class, args);
    }

    @GetMapping("/")
    @ResponseBody
    public String index() {
        System.out.println("/");
        return "It works";
    }

    @GetMapping(
            value ="/100kText",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    public String index100kText() {
        System.out.println("/100kText");

        sleep();

        return staticString;
    }


    private static String genString() {
        String pattern = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZY1234567890";
        Random rand = new Random(100);
        String randomString = "";
        for (int i = 0; i < 1024 * 100; i++) {
            randomString += pattern.charAt(rand.nextInt(pattern.length()));
        }
        return randomString;
    }

    private void sleep(){
        try {
            Thread.sleep(SLEEP_TIME_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

