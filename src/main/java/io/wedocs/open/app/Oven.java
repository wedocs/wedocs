package io.wedocs.open.app;

import io.wedocs.open.common.JBakeException;
import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.model.Article;
import io.wedocs.open.template.TemplateRender;
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
public class Oven {

    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private List<Throwable> errors = new LinkedList<>();
    private int renderedCount = 0;

    @Resource
    private DefaultJBakeConfiguration configuration;

    @Resource
    private Crawler crawler;

    @Resource
    private Asset asset;

    @Resource
    private TemplateRender templateRender;
    /**
     * Checks source path contains required sub-folders (i.e. templates) and setups up variables for them.
     * Creates destination folder if it does not exist.
     *
     * @throws JBakeException If template or contents folder don't exist
     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //JBakeConfigurationInspector inspector = new JBakeConfigurationInspector(configuration);
//        //inspector.inspect();
//    }

    /**
     * All the good stuff happens in here...
     */
    public void build() {
        final long start = System.currentTimeMillis();
        LOGGER.info("Baking has started...");

        // process source content
        crawler.crawl(new File(System.getProperty("user.dir"), "docs"));
        for (Article article : crawler.getArticleList()) {
            LOGGER.info("render uri {}", article.getUri());
            templateRender.render(article);
        }
        // copy assets
        asset.copy();
        //asset.copyAssetsFromContent(config.getContentFolder());

        errors.addAll(asset.getErrors());

        LOGGER.info("Baking finished!");
        long end = System.currentTimeMillis();
        LOGGER.info("Baked {} items in {}ms", renderedCount, end - start);
        if (!errors.isEmpty()) {
            LOGGER.error("Failed to bake {} item(s)!", errors.size());
        }
    }

    /**
     * 清除之前的构建目录
     */
    public void clean() {
        File target = new File(System.getProperty("user.dir"), "dist");
        LOGGER.info("clean target {}", target.getPath());
        deleteDir(target);
    }

    /**
     * 递归删除文件
     *
     * @param target
     */
    private void deleteDir(File target) {
        if (target.exists()) {
            if (target.isDirectory()) {
                for (File file : target.listFiles()) {
                    deleteDir(file);
                }
                target.delete();
            } else {  //如果目标文件是目录
                target.delete();
            }
        }
    }

    public List<Throwable> getErrors() {
        return new ArrayList<>(errors);
    }


}
