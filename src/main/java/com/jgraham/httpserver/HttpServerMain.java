package com.jgraham.httpserver;

import com.jgraham.httpserver.Routers.CobspecRouter;
import com.jgraham.httpserver.Routers.iAppRouter;
import com.jgraham.httpserver.ServerConnection.Server;
import com.jgraham.httpserver.ServerConnection.ServerConfig;

public class HttpServerMain {
    public static void main(String args[]) throws Exception {

        ServerConfig config = new ServerConfig();
        config.parseArgs(args);
        int port = config.getPort();
        String directory = config.getDirectory();
        iAppRouter appRouter = new CobspecRouter(directory);
        startServer(port, appRouter);
    }

    public static void startServer(int port, iAppRouter appRouter) throws Exception{
        Server server = new Server(port, appRouter);
        server.start();
    }
}



