package io.wedocs.open.app;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.model.Page;
import io.wedocs.open.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;

/**
 * Crawls a file system looking for content.
 *
 * @author Jonathan Bullock <a href="mailto:jonbullock@gmail.com">jonbullock@gmail.com</a>
 */
@Component
public class Crawler {

    final Logger LOGGER = LoggerFactory.getLogger(Crawler.class);

    @Resource
    private Parser parser;

    @Resource
    private DefaultJBakeConfiguration configuration;

    @Autowired
    private Configuration configurer;

    /**
     * Crawl all files and folders looking for content.
     *
     * @param path Folder to start from
     */
    public void crawl(File path) {
        LOGGER.info("{}", FileUtil.getFileFilter());
        File[] contents = path.listFiles(FileUtil.getFileFilter());
        if (contents != null) {
            Arrays.sort(contents);
            for (File sourceFile : contents) {
                if (sourceFile.isFile()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Processing [").append(sourceFile.getPath()).append("]... ");
                    String sha1 = buildHash(sourceFile);
                    String uri = buildURI(sourceFile);
                    crawlSourceFile(sourceFile, sha1, uri);
                    LOGGER.info("{}", sb);
                }
                if (sourceFile.isDirectory()) {
                    crawl(sourceFile);
                }
            }
        }
    }

    private String buildHash(final File sourceFile) {
        String sha1;
        try {
            sha1 = FileUtil.sha1(sourceFile);
        } catch (Exception e) {
            LOGGER.error("unable to build sha1 hash for source file '{}'", sourceFile);
            sha1 = "";
        }
        return sha1;
    }

    private String buildURI(final File sourceFile) {
        LOGGER.info("FileUtil.asPath(sourceFile) {}", FileUtil.asPath(sourceFile));
        LOGGER.info("FileUtil.asPath(configuration.getContentFolder() {}", FileUtil.asPath(configuration.getContentFolder()));
        String uri = FileUtil.asPath(sourceFile).replace(FileUtil.asPath(configuration.getContentFolder()), "");
        // strip off leading / to enable generating non-root based sites
        if (uri.startsWith(FileUtil.URI_SEPARATOR_CHAR)) {
            uri = uri.substring(1);
        }
        return uri;
    }


    private void crawlSourceFile(final File sourceFile, final String sha1, final String uri) {
        try {
            Page fileContents = parser.processFile(sourceFile);
            // 解析完毕 根据模板生成文件
            LOGGER.info("uri {}", uri);
            //Template template = configurer.getTemplate("post.ftl");
            //Writer fileWriter = new FileWriter(new File(uri));
            //template.process(fileContents, fileWriter);
            //fileWriter.flush();
            //fileWriter.close();
        } catch (Exception ex) {
            throw new RuntimeException("Failed crawling file: " + sourceFile.getPath() + " " + ex.getMessage(), ex);
        }
    }
}
