package io.wedocs.open.parser;

import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.model.Page;

import java.io.File;

/**
 * Created by wangkun23 on 2019/10/31.
 */
public interface ParserEngine {

    /**
     * Parse a given file and transform to a model representation used by {@link MarkdownEngine} implementations
     * to render the file content.
     *
     * @param config The project configuration
     * @param file   The file to be parsed
     * @return A model representation of the given file
     */
    Page parse(DefaultJBakeConfiguration config, File file);
}
