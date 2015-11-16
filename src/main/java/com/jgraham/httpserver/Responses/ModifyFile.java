package com.jgraham.httpserver.Responses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModifyFile {

    private File f;

    public ModifyFile(File f) {
        this.f = f;
    }

    public void modifyFile(String contents) throws IOException {
        FileWriter file = new FileWriter(f, false);
        file.write(contents);
        file.flush();
        file.close();
    }

    public void deleteFile() {
            f.delete();
    }
}

