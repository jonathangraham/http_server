package com.jgraham.httpserver;

import java.io.IOException;

public class HttpServer {
    public static void main(String args[]) throws IOException {

        ServerConfig config = new ServerConfig();
        config.parseArgs(args);
    }
}
