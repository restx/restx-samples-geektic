package main;

import restx.server.WebServer;
import restx.server.simple.simple.SimpleWebServer;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.Integer.parseInt;

public class MainGeekticServer {
    public static final String WEB_APP_LOCATION = "web";

    public static void main(String[] args) throws Exception {
        int port = parseInt(firstNonNull(System.getenv("PORT"), "8086"));
        new MainGeekticServer().start(port);
    }

    public void start(int port) throws Exception {
        WebServer server = SimpleWebServer.builder().setRouterPath("")
                            .setAppBase(WEB_APP_LOCATION).setPort(port).build();
        server.startAndAwait();
    }
}
