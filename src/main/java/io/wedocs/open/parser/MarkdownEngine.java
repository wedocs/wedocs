package io.wedocs.open.parser;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.options.DataHolder;
import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * Base class for markup engine wrappers. A markup engine is responsible for rendering
 * <p>
 * This specific engine does nothing, meaning that the body is rendered as raw contents.
 *
 * @author Cédric Champeau
 */
@Component
public class MarkdownEngine implements ParserEngine {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private DefaultJBakeConfiguration config;

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
        List<String> mdExts = config.getMarkdownExtensions();

        int extensions = Extensions.NONE;

        for (String ext : mdExts) {
            if (ext.startsWith("-")) {
                ext = ext.substring(1);
                extensions = removeExtension(extensions, extensionFor(ext));
            } else {
                if (ext.startsWith("+")) {
                    ext = ext.substring(1);
                }
                extensions = addExtension(extensions, extensionFor(ext));
            }
        }

        DataHolder options = PegdownOptionsAdapter.flexmarkOptions(extensions);

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Document document = parser.parse(context.getFileContent());

        context.setBody(renderer.render(document));
    }

    /**
     * Parse given file to extract as much infos as possible
     *
     * @param file file to process
     * @return a map containing all infos. Returning null indicates an error, even if an exception would be better.
     */
    @Override
    public Page parse(File file) {
        String fileContent;
        try (InputStream is = new FileInputStream(file)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            fileContent = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            LOGGER.error("Error while opening file {}", file, e);
            return null;
        }
        ParserContext context = new ParserContext(file, fileContent);
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

    private int extensionFor(String name) {
        int extension = Extensions.NONE;

        try {
            Field extField = Extensions.class.getDeclaredField(name);
            extension = extField.getInt(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.debug("Undeclared extension field '{}', fallback to NONE", name);
        }
        return extension;
    }

    private int addExtension(int previousExtensions, int additionalExtension) {
        return previousExtensions | additionalExtension;
    }

    private int removeExtension(int previousExtensions, int unwantedExtension) {
        return previousExtensions & (~unwantedExtension);
    }
}
