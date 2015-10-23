package com.jgraham.httpserver;


public class HttpServerMain {
    public static void main(String args[]) throws Exception {

        ServerConfig config = new ServerConfig();
        config.parseArgs(args);

        iHttpServerSocket serverSocket = new HttpServerSocket(config.getPort());

        Server server = new Server(serverSocket, config.getDirectory());

        server.start();
    }
}

