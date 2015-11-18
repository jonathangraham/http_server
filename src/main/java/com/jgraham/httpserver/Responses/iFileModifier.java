package com.jgraham.httpserver.Responses;

import java.io.File;
import java.io.IOException;

public interface iFileModifier {

    public void writeContentToFile(String contents, File f) throws IOException;

    public void deleteFile(File f);
}
