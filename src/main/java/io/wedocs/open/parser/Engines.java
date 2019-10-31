package io.wedocs.open.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

/**
 * <p>A singleton class giving access to markup engines. Markup engines are loaded based on classpath.
 * New engines may be registered either at runtime (not recommanded) or by putting a descriptor file
 * on classpath (recommanded).</p>
 *
 * <p>The descriptor file must be found in <i>META-INF</i> directory and named
 * <i>org.jbake.parser.MarkupEngines.properties</i>. The format of the file is easy:</p>
 * <code>
 * org.jbake.parser.RawMarkupEngine=html<br>
 * org.jbake.parser.AsciidoctorEngine=ad,adoc,asciidoc<br>
 * org.jbake.parser.MarkdownEngine=md<br>
 * </code>
 * <p>where the key is the class of the engine (must extend {@link MarkupEngine} and have a no-arg
 * constructor and the value is a comma-separated list of file extensions that this engine is capable of proceeding.</p>
 *
 * <p>Markup engines are singletons, so are typically used to initialize the underlying renderning engines. They
 * <b>must not</b> store specific information of a currently processed file (use {@link ParserContext the parser context}
 * for that).</p>
 * <p>
 * This class loads the engines only if they are found on classpath. If not, the engine is not registered. This allows
 * JBake to support multiple rendering engines without the explicit need to have them on classpath. This is a better
 * fit for embedding.
 *
 * @author Cédric Champeau
 */
public class Engines {
    private static final Logger LOGGER = LoggerFactory.getLogger(Engines.class);
    private static final Engines INSTANCE;

    private final Map<String, ParserEngine> parsers;


    static {
        INSTANCE = new Engines();
    }

    public static ParserEngine get(String fileExtension) {
        return INSTANCE.getEngine(fileExtension);
    }

    public static void register(String fileExtension, ParserEngine engine) {
        INSTANCE.registerEngine(fileExtension, engine);
    }

    public static Set<String> getRecognizedExtensions() {
        return Collections.unmodifiableSet(INSTANCE.parsers.keySet());
    }

    private Engines() {
        parsers = new HashMap<>();
    }

    private void registerEngine(String fileExtension, ParserEngine markupEngine) {
        ParserEngine old = parsers.put(fileExtension, markupEngine);
        if (old != null) {
            LOGGER.warn("Registered a markup engine for extension [.{}] but another one was already defined: {}", fileExtension, old);
        }
    }

    private ParserEngine getEngine(String fileExtension) {
        return parsers.get(fileExtension);
    }
}
