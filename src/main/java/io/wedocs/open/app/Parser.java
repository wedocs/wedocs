package io.wedocs.open.app;

import io.wedocs.open.model.Page;
import io.wedocs.open.parser.MarkdownEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * Parses a File for content.
 *
 * @author Jonathan Bullock <a href="mailto:jonbullock@gmail.com">jonbullock@gmail.com</a>
 */
@Component
public class Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    @Resource
    private MarkdownEngine markdownEngine;

    /**
     * Process the file by parsing the contents.
     *
     * @param file File input for parsing
     * @return The contents of the file
     */
    public String processFile(File file) {
//        ParserEngine engine = Engines.get(FileUtil.fileExt(file));
//        if (engine == null) {
//            LOGGER.error("Unable to find suitable markup engine for {}", file);
//            return null;
//        }
        return markdownEngine.parse(file);
    }
}
