package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;

import java.io.File;

public class ResponseRoute {

    private iResponseBuilder responseBuilder;

    public iResponseBuilder getResponseBuilder(Request request, String directory) {
        String path = request.getRequestURL();
        if (path.equals("/method_options")) {
            responseBuilder = new MethodOptionsBuilder();
        }
        else if ("GET".equals(request.getRequestType())) {
            getGETResponseBuilder(request, path, directory);
        }
        else {
            getPUTResponseBuilder(request);
        }
        return responseBuilder;
    }

    private void getGETResponseBuilder(Request request, String path, String directory) {
        if ("/".equals(path)) {
            responseBuilder = new FileDirectoryBuilder(directory);
        }
        else if (new File(getFilePath(getRoute(directory), path)).exists()) {
            responseBuilder = new FileContentsBuilder(path);
        }
        else {
            responseBuilder = new FourOhFourBuilder();
        }

    }

    private void getPUTResponseBuilder(Request request) {
        responseBuilder = new Status200Builder();
    }

    private String getRoute(String directory) {
        return (System.getProperty("user.dir")) + directory;
    }

    private String getFilePath(String route, String path) {
        return (route + path);
    }
}
