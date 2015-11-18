package com.jgraham.httpserver.Routers;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;

public interface iAppRouter {

    public iResponseBuilder getResponseBuilder(Request request) throws Exception;
}
