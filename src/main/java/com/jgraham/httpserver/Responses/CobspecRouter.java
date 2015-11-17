package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;

import java.io.File;

public class CobspecRouter implements iResponseRoute {

    private String directory;
    private iResponseBuilder responseBuilder;

    public CobspecRouter(String directory) {
        this.directory = directory;
    }

    public iResponseBuilder getResponseBuilder(Request request) throws Exception {
        String path = request.getRequestURL();
        String header = request.getRequestHeader();
        String verb = request.getRequestType();
        if (header.contains("Authorization")) {
            getAuthenticationBuilder(header);
        }
        else if (verb.equals("GET")) {
            getGETResponseBuilder(path, header, directory);
        }
        else if (verb.equals("PATCH")) {
            getPATCHResponseBuilder(header, path);
        }
        else {
            getOtherResponseBuilder(path, directory, verb);
        }
        return responseBuilder;
    }

    private void getGETResponseBuilder(String path, String header, String directory) {
        if ("/".equals(path)) {
            responseBuilder = new FileDirectoryBuilder(directory);
        }
        else if (path.equals("/method_options")) {
            responseBuilder = new MethodOptionsBuilder();
        }
        else if (path.equals("/redirect")) {
            responseBuilder = new RedirectBuilder();
        }
        else if (header.contains("Range")) {
            responseBuilder = new PartialContentBuilder(header, path);
        }
        else if (path.contains("/form") && (new File(getRoute(directory, path)).exists())) {
            responseBuilder = new FileContentsBuilder(path, directory);
        }
        else if (path.contains("/form")) {
            responseBuilder = new Status200Builder();
        }
        else if (new File(getRoute(directory, path)).exists()) {
            responseBuilder = new FileContentsBuilder(path, directory);
        }
        else if (path.contains("?")) {
            responseBuilder = new ParameterDecodeBuilder(path);
        }
        else if (path.equals("/logs")) {
            responseBuilder = new BasicAuthenticationRequiredBuilder();
        }
        else {
            responseBuilder = new FourOhFourBuilder();
        }

    }

    private void getOtherResponseBuilder(String path, String directory, String verb) throws Exception {
        if (path.equals("/method_options")) {
            responseBuilder = new MethodOptionsBuilder();
        }
        else if (path.equals("/form")) {
            File f = new File(getRoute(directory, path));
            responseBuilder = new FormResponseBuilder(verb, f);
        }
        else if (new File(getRoute(directory, path)).exists()) {
            responseBuilder = new MethodNotAllowedBuilder();
        }
        else {
            responseBuilder = new Status200Builder();
        }
    }

    private void getPATCHResponseBuilder(String header, String path) {
        File f = new File(getRoute(directory, path));
        responseBuilder = new PatchResponseBuilder(header, f);
    }

    private String getRoute(String directory, String path) {
        return (System.getProperty("user.dir")) + directory + path;
    }

    private void getAuthenticationBuilder(String header) {
        Boolean authorized = isAuthorized(header);
        if (authorized == true) {
            responseBuilder = new BasicAuthenticationBuilder();
        }
        else {
            responseBuilder = new BasicAuthenticationRequiredBuilder();
        }
    }

    private Boolean isAuthorized(String header) {
        String credentials = header.split(" ")[2];
        return (credentials.equals("YWRtaW46aHVudGVyMg=="));
    }

}
