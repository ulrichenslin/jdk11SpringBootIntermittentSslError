package com.jdk11.ssl.jdk11Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class Jdk11LoadTest {

    //client is share to test connection reuse
    Jdk11Client httpClient = new Jdk11Client();

    public static String url = "https://localhost:8443/100kText";

    Integer threads = 40;
    Integer cycles = 100;

    public void http2LoadTest() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        List<HttpClientThread> actors = new ArrayList<>();
        for (int i = 0; i < cycles; i++) {
            actors.add(new HttpClientThread(httpClient,url));
        }

        List<Future<Long>> results = submitAll(executor, actors);

        executor.shutdown();
        for (Future<Long> result : results) {
            try {
                result.get(3, TimeUnit.SECONDS);
            }catch(Exception all){}
        }
    }


    private static <T> List<Future<T>> submitAll(ExecutorService executor, Collection<? extends Callable<T>> tasks) {
        List<Future<T>> result = new ArrayList<Future<T>>(tasks.size());

        for (Callable<T> task : tasks)
            result.add(executor.submit(task));

        return result;
    }


    public class HttpClientThread implements Callable<Long> {

        Jdk11Client httpClient;
        String url;

        public HttpClientThread(Jdk11Client httpClient, String url) {
            this.httpClient = httpClient;
            this.url = url;
        }

        public Long call() throws Exception {
            httpClient.call(url);
            return 0l;
        }
    }

}
