package com.jgraham.httpserver;

import com.jgraham.httpserver.ServerConnection.HttpServerSocket;
import com.jgraham.httpserver.ServerConnection.Server;
import com.jgraham.httpserver.ServerConnection.ServerConfig;
import com.jgraham.httpserver.ServerConnection.iHttpServerSocket;

public class HttpServerMain {
    public static void main(String args[]) throws Exception {

        ServerConfig config = new ServerConfig();
        config.parseArgs(args);

        iHttpServerSocket serverSocket = new HttpServerSocket(config.getPort());

        Server server = new Server(serverSocket, config.getDirectory());

        server.start();
    }
//
//    public static void startServer(ServerConfig config,IRouter appRouter) {
//        // start the server
//    }
}



