package io.wedocs.open.config;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The default implementation
 */
@Configuration
public class DefaultJBakeConfiguration {

    private String SOURCE_FOLDER_KEY = "sourceFolder";
    private String DESTINATION_FOLDER_KEY = "destinationFolder";
    private String ASSET_FOLDER_KEY = "assetFolder";
    private String TEMPLATE_FOLDER_KEY = "templateFolder";
    private String CONTENT_FOLDER_KEY = "contentFolder";
    private Pattern TEMPLATE_DOC_PATTERN = Pattern.compile("(?:template\\.)([a-zA-Z0-9-_]+)(?:\\.file)");
    private String DOCTYPE_FILE_POSTFIX = ".file";
    private String DOCTYPE_EXTENSION_POSTFIX = ".extension";
    private String DOCTYPE_TEMPLATE_PREFIX = "template.";

    public static final String ARCHIVE_FILE = "archive.file";

    @Setter
    @Getter
    @Value("${asset.folder}")
    private String assetFolderName;

    /**
     * 获取静态路径
     *
     * @return
     */
    public File getAssetFolder() {
        return new File(new File(System.getProperty("user.dir")), assetFolderName);
    }

    @Setter
    @Getter
    @Value("${asset.ignore}")
    private Boolean assetIgnoreHidden;

    public static final String BUILD_TIMESTAMP = "build.timestamp";

    @Setter
    @Getter
    @Value("${content.folder}")
    private String contentFolderName;

    /**
     * 获取需要生成的目录
     *
     * @return
     */
    public File getContentFolder() {
        return new File(new File(System.getProperty("user.dir")), contentFolderName);
    }

    public static final String DEFAULT_STATUS = "default.status";
    public static final String DEFAULT_TYPE = "default.type";

    @Setter
    @Getter
    @Value("${destination.folder}")
    private String destinationFolderName;


    /**
     * 获取需要生成的目标目录
     * <p>默认是output</p>
     *
     * @return
     */
    public File getDestinationFolder() {
        return new File(new File(System.getProperty("user.dir")), destinationFolderName);
    }

    public static final String DRAFT_SUFFIX = "draft.suffix";
    public static final String FEED_FILE = "feed.file";
    public static final String HEADER_SEPARATOR = "header.separator";
    public static final String INDEX_FILE = "index.file";

    @Setter
    @Getter
    @Value("${markdown.extensions}")
    private List<String> markdownExtensions;

    @Setter
    @Getter
    @Value("${output.extension}")
    private String outputExtension;

    public static final String PAGINATE_INDEX = "index.paginate";
    public static final String POSTS_PER_PAGE = "index.posts_per_page";
    public static final String RENDER_ARCHIVE = "render.archive";
    public static final String RENDER_FEED = "render.feed";
    public static final String RENDER_INDEX = "render.index";
    public static final String RENDER_SITEMAP = "render.sitemap";
    public static final String RENDER_TAGS = "render.tags";
    public static final String RENDER_TAGS_INDEX = "render.tagsindex";
    public static final String RENDER_ENCODING = "render.encoding";
    public static final String SERVER_PORT = "server.port";
    public static final String SITE_HOST = "site.host";
    public static final String SITEMAP_FILE = "sitemap.file";
    public static final String TAG_SANITIZE = "tag.sanitize";
    public static final String TAG_PATH = "tag.path";
    public static final String TEMPLATE_FOLDER = "template.folder";
    public static final String TEMPLATE_ENCODING = "template.encoding";
    public static final String THYMELEAF_LOCALE = "thymeleaf.locale";

    @Setter
    @Getter
    @Value("${uri.noExtension}")
    private Boolean uriWithoutExtension;

    @Setter
    @Getter
    @Value("${uri.noExtension.prefix}")
    private String prefixForUriWithoutExtension;

    public static final String IMG_PATH_UPDATE = "img.path.update";
    public static final String IMG_PATH_PREPEND_HOST = "img.path.prepend.host";
    public static final String VERSION = "version";
}
