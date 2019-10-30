package io.wedocs.open.parser;


import io.wedocs.open.app.Crawler;

import java.util.Date;
import java.util.Map;

/**
 * An internal rendering engine used to notify the user that the markup format he used requires an engine that couldn't
 * be loaded.
 *
 * @author Cédric Champeau
 */
public class ErrorEngine extends MarkupEngine {
    private final String engineName;

    public ErrorEngine() {
        this("unknown");
    }

    public ErrorEngine(final String name) {
        engineName = name;
    }

    @Override
    public void processHeader(final ParserContext context) {
        Map<String, Object> contents = context.getDocumentModel();
        contents.put(Crawler.Attributes.TYPE, "post");
        contents.put(Crawler.Attributes.STATUS, "published");
        contents.put(Crawler.Attributes.TITLE, "Rendering engine missing");
        contents.put(Crawler.Attributes.DATE, new Date());
        contents.put(Crawler.Attributes.TAGS, new String[0]);
        contents.put(Crawler.Attributes.ID, context.getFile().getName());
    }

    @Override
    public void processBody(final ParserContext context) {
        context.setBody("The markup engine [" + engineName + "] for [" + context.getFile() + "] couldn't be loaded");
    }
}
