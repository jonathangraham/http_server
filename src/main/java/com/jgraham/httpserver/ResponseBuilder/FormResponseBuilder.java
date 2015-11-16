package com.jgraham.httpserver.ResponseBuilder;

import com.jgraham.httpserver.Responses.ModifyFile;

import java.io.File;

public class FormResponseBuilder implements iResponseBuilder {

    private String verb;
    private File f;

    public FormResponseBuilder(String verb, File f) {

        this.verb = verb;
        this.f = f;
    }

    public byte[] getResponse() throws Exception{
        StringBuilder response = new StringBuilder();
        response.append(getStatusLine());
        response.append(getHeader());
        response.append(getBody());
        return response.toString().getBytes();
    }

    private String getStatusLine() {
        return "HTTP/1.1 200 OK\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private String getBody() {
        return "";
    }

    public void modifyFile() throws Exception{
        if (verb.equals("PUT")) {
            new ModifyFile(f).modifyFile("data=heathcliff");
        }
        else if (verb.equals("POST")) {
            new ModifyFile(f).modifyFile("data=fatcat");
        }
        else if (verb.equals("DELETE")) {
            new ModifyFile(f).deleteFile();
        }
    }
}
