package io.wedocs.open.app;

import io.wedocs.open.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import io.wedocs.open.config.DefaultJBakeConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Deals with assets (static files such as css, js or image files).
 *
 * @author Jonathan Bullock <a href="mailto:jonbullock@gmail.com">jonbullock@gmail.com</a>
 */
@Component
public class Asset {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final List<Throwable> errors = new LinkedList<>();

    @Resource
    private DefaultJBakeConfiguration configuration;

    /**
     * Copy all files from assets folder to destination folder
     * read from configuration
     */
    public void copy() {
        try {
            String assetsDestinationPath = configuration.getDestinationFolder().getCanonicalPath() + File.separatorChar + "/assets";
            copy(configuration.getAssetFolder(), new File(assetsDestinationPath));
        } catch (IOException e) {
            LOGGER.error("Failed to copy the asset file.", e);
        }
    }

    /**
     * Copy all files from supplied path.
     *
     * @param path The starting path
     */
    public void copy(File path, File destination) {
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (!configuration.getAssetIgnoreHidden()
                        || !file.isHidden()) && (file.isFile()
                        || FileUtil.directoryOnlyIfNotIgnored(file));
            }
        };
        copy(path, destination, filter);
    }

    /**
     * Copy one asset file at a time.
     *
     * @param asset The asset file to copy
     */
    public void copySingleFile(File asset) {
        try {
            if (!asset.isDirectory()) {
                String targetPath = configuration.getDestinationFolder().getCanonicalPath() + File.separatorChar + assetSubPath(asset);
                LOGGER.info("Copying single asset file to [{}]", targetPath);
                copyFile(asset, new File(targetPath));
            } else {
                LOGGER.info("Skip copying single asset file [{}]. Is a directory.", asset.getPath());
            }
        } catch (IOException io) {
            LOGGER.error("Failed to copy the asset file.", io);
        }
    }

    /**
     * Determine if a given file is an asset file.
     *
     * @param path to the file to validate.
     * @return true if the path provided points to a file in the asset folder.
     */
    public boolean isAssetFile(File path) {
        boolean isAsset = false;

        try {
            if (FileUtil.directoryOnlyIfNotIgnored(path.getParentFile())) {
                if (FileUtil.isFileInDirectory(path, configuration.getAssetFolder())) {
                    isAsset = true;
                } else if (FileUtil.isFileInDirectory(path, configuration.getContentFolder())
                        && FileUtil.getNotContentFileFilter().accept(path)) {
                    isAsset = true;
                }
            }
        } catch (IOException ioe) {
            LOGGER.error("Unable to determine the path to asset file {}", path.getPath(), ioe);
        }
        return isAsset;
    }

    /**
     * Responsible for copying any asset files that exist within the content directory.
     *
     * @param path of the content directory
     */
    public void copyAssetsFromContent(File path) {
        copy(path, configuration.getDestinationFolder(), FileUtil.getNotContentFileFilter());
    }

    /**
     * Accessor method to the collection of errors generated during the bake
     *
     * @return a list of errors.
     */
    public List<Throwable> getErrors() {
        return new ArrayList<>(errors);
    }

    private String assetSubPath(File asset) throws IOException {
        // First, strip asset folder from file path
        String targetFolder = asset.getCanonicalPath().replace(configuration.getAssetFolder().getCanonicalPath() + File.separatorChar, "");
        // And just to be sure, let's also remove the content folder, as some assets are copied from here.
        targetFolder = targetFolder.replace(configuration.getContentFolder().getCanonicalPath() + File.separatorChar, "");
        return targetFolder;
    }

    private void copy(File sourceFolder, File targetFolder, final FileFilter filter) {
        final File[] assets = sourceFolder.listFiles(filter);
        if (assets != null) {
            Arrays.sort(assets);
            for (File asset : assets) {
                final File target = new File(targetFolder, asset.getName());
                if (asset.isFile()) {
                    copyFile(asset, target);
                } else if (asset.isDirectory()) {
                    copy(asset, target, filter);
                }
            }
        }
    }

    private void copyFile(File asset, File targetFolder) {
        try {
            FileUtils.copyFile(asset, targetFolder);
            LOGGER.info("Copying [{}]... done!", asset.getPath());
        } catch (IOException e) {
            LOGGER.error("Copying [{}]... failed!", asset.getPath(), e);
            errors.add(e);
        }
    }
}
