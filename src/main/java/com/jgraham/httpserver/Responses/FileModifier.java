package com.jgraham.httpserver.Responses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileModifier implements iFileModifier {

    public void writeContentToFile(String contents, File f) throws IOException {
        FileWriter file = new FileWriter(f, false);
        file.write(contents);
        file.flush();
        file.close();
    }

    public void deleteFile(File f) {
            f.delete();
    }
}

