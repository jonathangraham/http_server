package com.jgraham.httpserver.mocks;

import com.jgraham.httpserver.Responses.iFileModifier;

import java.io.File;
import java.io.IOException;

public class MockFileModifier implements iFileModifier {

    private String contents;

    public void writeContentToFile(String contents, File f) throws IOException {
        this.contents = contents;
    }

    public void deleteFile(File f) {
        contents = "file deleted";
    }

    public String getContents() {
        return contents;
    }
}
