package io.wedocs.open.parser;


import java.io.File;

/**
 * Created by wangkun23 on 2019/10/31.
 */
public interface ParserEngine {
    /**
     * Parse a given file and transform to a model representation used by {@link MarkdownEngine} implementations
     * to render the file content.
     *
     * @param file The file to be parsed
     * @return A model representation of the given file
     */
    String parse(File file);
}
