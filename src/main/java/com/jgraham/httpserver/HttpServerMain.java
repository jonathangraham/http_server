package com.jgraham.httpserver;

import com.jgraham.httpserver.Responses.CobspecRouter;
import com.jgraham.httpserver.Responses.iResponseRoute;
import com.jgraham.httpserver.ServerConnection.Server;
import com.jgraham.httpserver.ServerConnection.ServerConfig;

public class HttpServerMain {
    public static void main(String args[]) throws Exception {

        ServerConfig config = new ServerConfig();
        config.parseArgs(args);
        int port = config.getPort();
        String directory = config.getDirectory();
        iResponseRoute appRouter = new CobspecRouter(directory);
        startServer(port, appRouter);
    }

    public static void startServer(int port, iResponseRoute appRouter) throws Exception{
        Server server = new Server(port, appRouter);
        server.start();
    }
}



