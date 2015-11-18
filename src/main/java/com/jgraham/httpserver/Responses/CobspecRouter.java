package com.jgraham.httpserver.Responses;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.*;

import java.io.File;

public class CobspecRouter implements iAppRouter {

    private String directory;
    private iFileModifier fm;
    private iResponseBuilder responseBuilder;

    public CobspecRouter(String directory, iFileModifier fm) {

        this.directory = directory;
        this.fm = fm;
    }

    public iResponseBuilder getResponseBuilder(Request request) throws Exception {

        String verb = request.getRequestType();
        String path = request.getRequestURL();
        String header = request.getRequestHeader();

        if (header.contains("Authorization")) {
            getAuthenticationBuilder(header);
        }
        else if (verb.equals("GET")) {
            getGETResponseBuilder(verb, path, header);
        }
        else if (verb.equals("PATCH")) {
            getPATCHResponseBuilder(verb, path, header);
        }
        else {
            getOtherResponseBuilder(verb, path, header);
        }
        return responseBuilder;
    }

    private void getGETResponseBuilder(String verb, String path, String header) {
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

    private void getOtherResponseBuilder(String verb, String path, String header) throws Exception {
        if (path.equals("/method_options")) {
            responseBuilder = new MethodOptionsBuilder();
        }
        else if (path.equals("/form")) {
            modifyFile(verb, path, header);
            responseBuilder = new FormResponseBuilder();
        }
        else if (new File(getRoute(directory, path)).exists()) {
            responseBuilder = new MethodNotAllowedBuilder();
        }
        else {
            responseBuilder = new Status200Builder();
        }
    }

    private void getPATCHResponseBuilder(String verb, String path, String header) throws Exception {
        modifyFile(verb, path, header);
        responseBuilder = new PatchResponseBuilder();
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

    private void modifyFile(String verb, String path, String header) throws Exception{
        File f = new File(getRoute(directory, path));
        if (header.contains("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec")) {
            fm.writeContentToFile("patched content", f);
        }
        else if (verb.equals("PATCH")) {
            fm.writeContentToFile("default content", f);
        }
        else if (verb.equals("PUT")) {
            fm.writeContentToFile("data=heathcliff", f);
        }
        else if (verb.equals("POST")) {
            fm.writeContentToFile("data=fatcat", f);
        }
        else if (verb.equals("DELETE")) {
            fm.deleteFile(f);
        }
    }

    private String getRoute(String directory, String path) {
        return (System.getProperty("user.dir")) + directory + path;
    }

}
