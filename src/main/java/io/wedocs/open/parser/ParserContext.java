package io.wedocs.open.parser;


import io.wedocs.open.config.JBakeConfiguration;

import java.io.File;
import java.util.List;

public class ParserContext {
    private final File file;
    private final List<String> fileLines;
    private final JBakeConfiguration config;

    public ParserContext(
            File file,
            List<String> fileLines,
            JBakeConfiguration config) {
        this.file = file;
        this.fileLines = fileLines;
        this.config = config;
    }

    public File getFile() {
        return file;
    }

    public List<String> getFileLines() {
        return fileLines;
    }

    public JBakeConfiguration getConfig() {
        return config;
    }
}
