package com.jgraham.httpserver.Routers;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;

import java.io.File;

public class CobspecRouter implements iAppRouter {

    private String directory;
    private iResponseBuilder responseBuilder;
    private String content = "default content";
    private String verb;
    private String path;
    private String header;


    public CobspecRouter(String directory) {

        this.directory = directory;
    }

    public iResponseBuilder getResponseBuilder(Request request) throws Exception {

        verb = request.getRequestType();
        path = request.getRequestURL();
        header = request.getRequestHeader();

        if (header.contains("Authorization")) {
            getAuthenticationBuilder();
        }
        else if (verb.equals("GET")) {
            getGETResponseBuilder();
        }
        else if (verb.equals("PATCH")) {
            getPATCHResponseBuilder();
        }
        else {
            getOtherResponseBuilder();
        }
        return responseBuilder;
    }

    private void getGETResponseBuilder() {
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
        else if (path.contains("/form") || path.contains("/patch")) {
            responseBuilder = new FormResponseBuilder(content);
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

    private void getOtherResponseBuilder() throws Exception {
        if (path.equals("/method_options")) {
            responseBuilder = new MethodOptionsBuilder();
        }
        else if (path.equals("/form")) {
            if (verb.equals("PUT")) {
                content = "data=heathcliff";
            }
            else if (verb.equals("POST")) {
                content = "data=fatcat";
            }
            else if (verb.equals("DELETE")) {
                content = "";
            }
            responseBuilder = new FormResponseBuilder(content);
        }
        else if (new File(getRoute(directory, path)).exists()) {
            responseBuilder = new MethodNotAllowedBuilder();
        }
        else {
            responseBuilder = new Status200Builder();
        }
    }

    private void getPATCHResponseBuilder() throws Exception {
        if (header.contains("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec")) {
            content = "patched content";
        }
        else{
            content = "default content";
        }
        responseBuilder = new PatchResponseBuilder();
    }

    private void getAuthenticationBuilder() {
        Boolean authorized = isAuthorized();
        if (authorized == true) {
            responseBuilder = new BasicAuthenticationBuilder();
        }
        else {
            responseBuilder = new BasicAuthenticationRequiredBuilder();
        }
    }

    private Boolean isAuthorized() {
        String credentials = header.split(" ")[2];
        return (credentials.equals("YWRtaW46aHVudGVyMg=="));
    }

    private String getRoute(String directory, String path) {
        return (System.getProperty("user.dir")) + directory + path;
    }

    public String getContent() {
        return content;
    }

}