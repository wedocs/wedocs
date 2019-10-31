package io.wedocs.open.app;

import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.model.Page;
import io.wedocs.open.utils.FileUtil;
import io.wedocs.open.utils.HtmlUtil;
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
import java.util.Map;

/**
 * Crawls a file system looking for content.
 *
 * @author Jonathan Bullock <a href="mailto:jonbullock@gmail.com">jonbullock@gmail.com</a>
 */
@Component
public class Crawler {

    final Logger LOGGER = LoggerFactory.getLogger(Crawler.class);

    private DefaultJBakeConfiguration config;
    private Parser parser;

    @Resource
    private DefaultJBakeConfiguration configuration;
    /**
     * Creates new instance of Crawler.
     */
    public Crawler() {
        this.parser = new Parser(configuration);
    }

    /**
     * Crawl all files and folders looking for content.
     *
     * @param path Folder to start from
     */
    private void crawl(File path) {
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
        String uri = FileUtil.asPath(sourceFile).replace(FileUtil.asPath(config.getContentFolder()), "");

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
                    + config.getOutputExtension();
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
                    + config.getOutputExtension();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Missing UTF-8 encoding??", e); // Won't happen unless JDK is broken.
        }
    }

    private boolean useNoExtensionUri(String uri) {
        boolean noExtensionUri = config.getUriWithoutExtension();
        String noExtensionUriPrefix = config.getPrefixForUriWithoutExtension();

        return noExtensionUri
                && (noExtensionUriPrefix != null)
                && (noExtensionUriPrefix.length() > 0)
                && uri.startsWith(noExtensionUriPrefix);
    }

    private void crawlSourceFile(final File sourceFile, final String sha1, final String uri) {
        try {
            Page fileContents = parser.processFile(sourceFile);

            if (fileContents != null) {
                fileContents.put(Attributes.ROOTPATH, getPathToRoot(sourceFile));
                fileContents.put(Attributes.FILE, sourceFile.getPath());
                if (config.getUriWithoutExtension()) {
                    fileContents.put(Attributes.NO_EXTENSION_URI, uri.replace("/index.html", "/"));
                }
                if (config.getImgPathUpdate()) {
                    // Prevent image source url's from breaking
                    HtmlUtil.fixImageSourceUrls(fileContents, config);
                }
                // 解析完毕 根据模板生成文件

            } else {
                LOGGER.warn("{} has an invalid header, it has been ignored!", sourceFile);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed crawling file: " + sourceFile.getPath() + " " + ex.getMessage(), ex);
        }
    }

    private String getPathToRoot(File sourceFile) {
        return FileUtil.getUriPathToContentRoot(config, sourceFile);
    }


    public abstract static class Attributes {



        private Attributes() {
        }
    }
}
