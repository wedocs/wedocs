package io.wedocs.open.app;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.model.Page;
import io.wedocs.open.utils.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        String uri = FileUtil.asPath(sourceFile).replace(FileUtil.asPath(configuration.getContentFolder()), "");

        if (useNoExtensionUri(uri)) {
            // convert URI from xxx.html to xxx/index.html
            uri = createNoExtensionUri(uri);
        } else {
            uri = createUri(uri);
        }

        // strip off leading / to enable generating non-root based sites
        if (uri.startsWith(FileUtil.URI_SEPARATOR_CHAR)) {
            uri = uri.substring(1, uri.length());
        }
        return uri;
    }
    // TODO: Refactor - parametrize the following two methods into one.
    // commons-codec's URLCodec could be used when we add that dependency.
    private String createUri(String uri) {
        try {
            return FileUtil.URI_SEPARATOR_CHAR
                    + FilenameUtils.getPath(uri)
                    + URLEncoder.encode(FilenameUtils.getBaseName(uri), StandardCharsets.UTF_8.name())
                    + configuration.getOutputExtension();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Missing UTF-8 encoding??", e); // Won't happen unless JDK is broken.
        }
    }

    private String createNoExtensionUri(String uri) {
        try {
            return FileUtil.URI_SEPARATOR_CHAR
                    + FilenameUtils.getPath(uri)
                    + URLEncoder.encode(FilenameUtils.getBaseName(uri), StandardCharsets.UTF_8.name())
                    + FileUtil.URI_SEPARATOR_CHAR
                    + "index"
                    + configuration.getOutputExtension();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Missing UTF-8 encoding??", e); // Won't happen unless JDK is broken.
        }
    }

    private boolean useNoExtensionUri(String uri) {
        boolean noExtensionUri = configuration.getUriWithoutExtension();
        String noExtensionUriPrefix = configuration.getPrefixForUriWithoutExtension();

        return noExtensionUri
                && (noExtensionUriPrefix != null)
                && (noExtensionUriPrefix.length() > 0)
                && uri.startsWith(noExtensionUriPrefix);
    }
    private void crawlSourceFile(final File sourceFile, final String sha1, final String uri) {
        try {
            Page fileContents = parser.processFile(sourceFile);
            // 解析完毕 根据模板生成文件
            LOGGER.info("sha1 {}", sha1);
            LOGGER.info("uri {}", uri);
            LOGGER.info("getCanonicalPath {}", sourceFile.getCanonicalPath());
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
