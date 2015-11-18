package com.jgraham.httpserver;

import com.jgraham.httpserver.Responses.CobspecRouter;
import com.jgraham.httpserver.Responses.FileModifier;
import com.jgraham.httpserver.Responses.iFileModifier;
import com.jgraham.httpserver.Responses.iAppRouter;
import com.jgraham.httpserver.ServerConnection.Server;
import com.jgraham.httpserver.ServerConnection.ServerConfig;

public class HttpServerMain {
    public static void main(String args[]) throws Exception {

        ServerConfig config = new ServerConfig();
        config.parseArgs(args);
        int port = config.getPort();
        String directory = config.getDirectory();
        iFileModifier fm = new FileModifier();
        iAppRouter appRouter = new CobspecRouter(directory, fm);
        startServer(port, appRouter);
    }

    public static void startServer(int port, iAppRouter appRouter) throws Exception{
        Server server = new Server(port, appRouter);
        server.start();
    }
}



