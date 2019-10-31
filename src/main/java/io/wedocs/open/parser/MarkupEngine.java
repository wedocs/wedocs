package io.wedocs.open.parser;

import io.wedocs.open.config.JBakeConfiguration;
import io.wedocs.open.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Base class for markup engine wrappers. A markup engine is responsible for rendering
 * <p>
 * This specific engine does nothing, meaning that the body is rendered as raw contents.
 *
 * @author Cédric Champeau
 */
public abstract class MarkupEngine implements ParserEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarkupEngine.class);

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

    /**
     * Parse given file to extract as much infos as possible
     *
     * @param file file to process
     * @return a map containing all infos. Returning null indicates an error, even if an exception would be better.
     */
    @Override
    public Page parse(JBakeConfiguration config, File file) {
        String fileContent;
        try (InputStream is = new FileInputStream(file)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            fileContent = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            LOGGER.error("Error while opening file {}", file, e);
            return null;
        }
        ParserContext context = new ParserContext(file, fileContent, config);
        // eventually process body using specific engine
        if (validate(context)) {
            processBody(context);
        } else {
            LOGGER.error("Incomplete source file ({}) for markup engine: {}", file, getClass().getSimpleName());
            return null;
        }
        // TODO: post parsing plugins to hook in here?
        return new Page(file, new HashMap<>(), context.getBody(), file.getPath());
    }
}
