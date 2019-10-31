package io.wedocs.open.parser;

import io.wedocs.open.config.DefaultJBakeConfiguration;
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

    @Getter
    private final DefaultJBakeConfiguration config;

    public ParserContext(
            File file,
            String fileContent,
            DefaultJBakeConfiguration config) {
        this.file = file;
        this.fileContent = fileContent;
        this.config = config;
    }

}
