package io.wedocs.open.app;

import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.model.Page;
import io.wedocs.open.parser.Engines;
import io.wedocs.open.parser.ParserEngine;
import io.wedocs.open.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Parses a File for content.
 *
 * @author Jonathan Bullock <a href="mailto:jonbullock@gmail.com">jonbullock@gmail.com</a>
 */
public class Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    private DefaultJBakeConfiguration config;

    /**
     * Creates a new instance of Parser.
     *
     * @param config Project configuration
     */
    public Parser(DefaultJBakeConfiguration config) {
        this.config = config;
    }

    /**
     * Process the file by parsing the contents.
     *
     * @param file File input for parsing
     * @return The contents of the file
     */
    public Page processFile(File file) {
        ParserEngine engine = Engines.get(FileUtil.fileExt(file));
        if (engine == null) {
            LOGGER.error("Unable to find suitable markup engine for {}", file);
            return null;
        }
        return engine.parse(config, file);
    }
}
