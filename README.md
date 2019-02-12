# Open JDK 11 SSL Handshake problem

This project reproduces an error we are experiancing when testing the Open JDK 11 HTTP client over http/2 on Tomcat 9.

The error is not Tomcat related, since it comes form the 'sun.security.ssl.SSLHandshake.produce' function.

Running the test:
```
./gradlew testOpenJdk
```

The log wil show this below error.  This is sometimes right at the beginning of the process, and other times in the middle.

The concern here is that the error is in the 'sun.security.ssl.ServerHello' code, which point to it being a JDK bug.

```
java.util.NoSuchElementException: No value present
        at java.base/java.util.Optional.get(Optional.java:148) ~[na:na]
        at java.base/sun.security.ssl.ServerHello$T13ServerHelloProducer.produce(ServerHello.java:551) ~[na:na]
        at java.base/sun.security.ssl.SSLHandshake.produce(SSLHandshake.java:436) ~[na:na]
        at java.base/sun.security.ssl.ClientHello$T13ClientHelloConsumer.goServerHello(ClientHello.java:1224) ~[na:na]
        at java.base/sun.security.ssl.ClientHello$T13ClientHelloConsumer.consume(ClientHello.java:1160) ~[na:na]
        at java.base/sun.security.ssl.ClientHello$ClientHelloConsumer.onClientHello(ClientHello.java:849) ~[na:na]
        at java.base/sun.security.ssl.ClientHello$ClientHelloConsumer.consume(ClientHello.java:810) ~[na:na]
        at java.base/sun.security.ssl.SSLHandshake.consume(SSLHandshake.java:392) ~[na:na]
        at java.base/sun.security.ssl.HandshakeContext.dispatch(HandshakeContext.java:444) ~[na:na]
        at java.base/sun.security.ssl.SSLEngineImpl$DelegatedTask$DelegatedAction.run(SSLEngineImpl.java:1065) ~[na:na]
        at java.base/sun.security.ssl.SSLEngineImpl$DelegatedTask$DelegatedAction.run(SSLEngineImpl.java:1052) ~[na:na]
        at java.base/java.security.AccessController.doPrivileged(Native Method) ~[na:na]
        at java.base/sun.security.ssl.SSLEngineImpl$DelegatedTask.run(SSLEngineImpl.java:999) ~[na:na]
        at org.apache.tomcat.util.net.SecureNioChannel.tasks(SecureNioChannel.java:423) ~[tomcat-embed-core-9.0.14.jar:9.0.14]
        at org.apache.tomcat.util.net.SecureNioChannel.handshakeUnwrap(SecureNioChannel.java:483) ~[tomcat-embed-core-9.0.14.jar:9.0.14]
        at org.apache.tomcat.util.net.SecureNioChannel.handshake(SecureNioChannel.java:238) ~[tomcat-embed-core-9.0.14.jar:9.0.14]
        at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1394) ~[tomcat-embed-core-9.0.14.jar:9.0.14]
        at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) ~[tomcat-embed-core-9.0.14.jar:9.0.14]
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128) ~[na:na]
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628) ~[na:na]
        at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.14.jar:9.0.14]
        at java.base/java.lang.Thread.run(Thread.java:834) ~[na:na]

```

