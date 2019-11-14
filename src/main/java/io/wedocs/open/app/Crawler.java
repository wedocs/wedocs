package io.wedocs.open.app;

import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.model.Article;
import io.wedocs.open.utils.FileUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Crawls a file system looking for content.
 *
 * @author Jonathan Bullock <a href="mailto:jonbullock@gmail.com">jonbullock@gmail.com</a>
 */
@Component
public class Crawler {

    final Logger LOGGER = LoggerFactory.getLogger(Crawler.class);

    @Setter
    @Getter
    private List<Article> articleList = new CopyOnWriteArrayList<>();

    @Resource
    private Parser parser;

    @Resource
    private DefaultJBakeConfiguration configuration;


    /**
     * Crawl all files and folders looking for content.
     *
     * @param path Folder to start from
     */
    public void crawl(File path) {
        // File[] contents = path.listFiles(FileUtil.getFileFilter());
        File[] contents = path.listFiles();
        if (contents != null) {
            Arrays.sort(contents);
            for (File sourceFile : contents) {
                if (sourceFile.isDirectory()) {
                    crawl(sourceFile);
                }
                if (sourceFile.isFile()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Processing [").append(sourceFile.getPath()).append("]... ");
                    String sha1 = buildHash(sourceFile);
                    String uri = buildURI(sourceFile);
                    Article article = crawlSourceFile(sha1, sourceFile, uri);
                    articleList.add(article);
                    LOGGER.info("{}", sb);
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
        LOGGER.info("sourceFile {}", FileUtil.asPath(sourceFile));
        LOGGER.info("getContentFolder {}", FileUtil.asPath(configuration.getContentFolder()));
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

    /**
     * commons-codec's URLCodec could be used when we add that dependency.
     */
    private String createUri(String uri) {
        try {
            return FileUtil.URI_SEPARATOR_CHAR
                    + FilenameUtils.getPath(uri)
                    + URLEncoder.encode(FilenameUtils.getBaseName(uri), StandardCharsets.UTF_8.name())
                    + configuration.getOutputExtension();
        } catch (UnsupportedEncodingException e) {
            // Won't happen unless JDK is broken.
            throw new RuntimeException("Missing UTF-8 encoding??", e);
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
            // Won't happen unless JDK is broken.
            throw new RuntimeException("Missing UTF-8 encoding??", e);
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

    private Article crawlSourceFile(final String sha1, final File sourceFile, final String uri) {
        String fileContents = parser.processFile(sourceFile);
        return new Article(sha1, sourceFile, uri, fileContents);
    }
}
