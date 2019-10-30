package io.wedocs.open.parser;

import io.wedocs.open.app.Crawler;
import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.config.JBakeConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Base class for markup engine wrappers. A markup engine is responsible for rendering
 * markup in a source file and exporting the result into the {@link ParserContext#getDocumentModel() contents} map.
 * <p>
 * This specific engine does nothing, meaning that the body is rendered as raw contents.
 *
 * @author Cédric Champeau
 */
public abstract class MarkupEngine implements ParserEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarkupEngine.class);
    private static final String UTF_8_BOM = "\uFEFF";

    /**
     * Tests if this markup engine can process the document.
     *
     * @param context the parser context
     * @return true if this markup engine has enough context to process this document. false otherwise
     */
    public boolean validate(ParserContext context) {
        return true;
    }

    /**
     * Processes the body of the document. Usually subclasses will parse the document body and render
     * it, exporting the result using the {@link ParserContext#setBody(String)} method.
     *
     * @param context the parser context
     */
    public void processBody(final ParserContext context) {
    }

    @Override
    public Map<String, Object> parse(Configuration config, File file, String contentPath) {
        return parse(new DefaultJBakeConfiguration((CompositeConfiguration) config), file);
    }

    /**
     * Parse given file to extract as much infos as possible
     *
     * @param file file to process
     * @return a map containing all infos. Returning null indicates an error, even if an exception would be better.
     */
    @Override
    public Map<String, Object> parse(JBakeConfiguration config, File file) {
        List<String> fileContents;
        try (InputStream is = new FileInputStream(file)) {
            fileContents = IOUtils.readLines(is, config.getRenderEncoding());
        } catch (IOException e) {
            LOGGER.error("Error while opening file {}", file, e);
            return null;
        }
        ParserContext context = new ParserContext(file, fileContents, config);
        // eventually process body using specific engine
        if (validate(context)) {
            processBody(context);
        } else {
            LOGGER.error("Incomplete source file ({}) for markup engine: {}", file, getClass().getSimpleName());
            return null;
        }
        // TODO: post parsing plugins to hook in here?
        return context.getDocumentModel();
    }
}
