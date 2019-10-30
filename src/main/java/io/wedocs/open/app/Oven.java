package io.wedocs.open.app;

import io.wedocs.open.common.JBakeException;
import io.wedocs.open.config.JBakeConfiguration;
import io.wedocs.open.config.JBakeConfigurationInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * All the baking happens in the Oven!
 *
 * @author Jonathan Bullock <a href="mailto:jonbullock@gmail.com">jonbullock@gmail.com</a>
 */
public class Oven {

    private static final Logger LOGGER = LoggerFactory.getLogger(Oven.class);

    private List<Throwable> errors = new LinkedList<>();
    private int renderedCount = 0;

    /**
     * Checks source path contains required sub-folders (i.e. templates) and setups up variables for them.
     * Creates destination folder if it does not exist.
     *
     * @throws JBakeException If template or contents folder don't exist
     */
    private void checkConfiguration(JBakeConfiguration configuration) {
        JBakeConfigurationInspector inspector = new JBakeConfigurationInspector(configuration);
        inspector.inspect();
    }

    /**
     * Responsible for incremental baking, typically a single file at a time.
     *
     * @param fileToBake The file to bake
     */
    public void bake(File fileToBake) {
        Asset asset = utensils.getAsset();
        if (asset.isAssetFile(fileToBake)) {
            LOGGER.info("Baking a change to an asset [" + fileToBake.getPath() + "]");
            asset.copySingleFile(fileToBake);
        } else {
            LOGGER.info("Playing it safe and running a full bake...");
            bake();
        }
    }

    /**
     * All the good stuff happens in here...
     */
    public void bake() {
        Crawler crawler = utensils.getCrawler();
        Asset asset = utensils.getAsset();
        final long start = new Date().getTime();
        LOGGER.info("Baking has started...");

        // process source content
        crawler.crawl();

        // render content
        renderContent();

        // copy assets
        asset.copy();
        asset.copyAssetsFromContent(config.getContentFolder());

        errors.addAll(asset.getErrors());

        LOGGER.info("Baking finished!");
        long end = new Date().getTime();
        LOGGER.info("Baked {} items in {}ms", renderedCount, end - start);
        if (!errors.isEmpty()) {
            LOGGER.error("Failed to bake {} item(s)!", errors.size());
        }

    }


    public List<Throwable> getErrors() {
        return new ArrayList<>(errors);
    }
}
