package io.wedocs.open.parser;

import lombok.Getter;
import lombok.Setter;

import java.io.File;


public class ParserContext {

    @Getter
    private final File file;

    @Getter
    private final String fileContent;

    @Setter
    @Getter
    private String body;


    public ParserContext(
            File file,
            String fileContent) {
        this.file = file;
        this.fileContent = fileContent;
    }

}
