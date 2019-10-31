package io.wedocs.open.app;

import io.wedocs.open.common.JBakeException;
import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.config.JBakeConfigurationInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * All the baking happens in the Oven!
 *
 * @author Jonathan Bullock <a href="mailto:jonbullock@gmail.com">jonbullock@gmail.com</a>
 */
@Component
public class Oven implements InitializingBean {

    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private List<Throwable> errors = new LinkedList<>();
    private int renderedCount = 0;

    @Resource
    private DefaultJBakeConfiguration configuration;

    /**
     * Checks source path contains required sub-folders (i.e. templates) and setups up variables for them.
     * Creates destination folder if it does not exist.
     *
     * @throws JBakeException If template or contents folder don't exist
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        JBakeConfigurationInspector inspector = new JBakeConfigurationInspector(configuration);
        inspector.inspect();
    }

    /**
     * Responsible for incremental baking, typically a single file at a time.
     *
     * @param fileToBake The file to bake
     */
    public void bake(File fileToBake) {
//        Asset asset = utensils.getAsset();
//        if (asset.isAssetFile(fileToBake)) {
//            LOGGER.info("Baking a change to an asset [" + fileToBake.getPath() + "]");
//            asset.copySingleFile(fileToBake);
//        } else {
//            LOGGER.info("Playing it safe and running a full bake...");
//            bake();
//        }
    }

    /**
     * All the good stuff happens in here...
     */
    public void bake() {
        Crawler crawler = new Crawler();
        final long start = System.currentTimeMillis();
        LOGGER.info("Baking has started...");

        // process source content
        crawler.crawl();

        // copy assets
        //asset.copy();
        // asset.copyAssetsFromContent(config.getContentFolder());

        // errors.addAll(asset.getErrors());

        LOGGER.info("Baking finished!");
        long end = System.currentTimeMillis();
        LOGGER.info("Baked {} items in {}ms", renderedCount, end - start);
        if (!errors.isEmpty()) {
            LOGGER.error("Failed to bake {} item(s)!", errors.size());
        }
    }

    public List<Throwable> getErrors() {
        return new ArrayList<>(errors);
    }


}
