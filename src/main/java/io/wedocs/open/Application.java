package io.wedocs.open;

import io.wedocs.open.common.JBakeException;
import io.wedocs.open.config.JBakeConfiguration;
import io.wedocs.open.config.JBakeConfigurationFactory;
import org.apache.commons.configuration.ConfigurationException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.StringWriter;

/**
 * 启动类
 * Created by wangkun23 on 2019/10/26.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String USAGE_PREFIX = "Usage: jbake";
    private final String ALT_USAGE_PREFIX = "   or  jbake";

    private Baker baker;
    private JBakeConfigurationFactory configurationFactory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public Application() {
        this.baker = new Baker();
        this.configurationFactory = new JBakeConfigurationFactory();
    }

    @Override
    public void run(String... args) throws Exception {
        LaunchOptions res = parseArguments(args);

        final JBakeConfiguration config;
        try {
            if (res.isRunServer()) {
                config = getJBakeConfigurationFactory().createJettyJbakeConfiguration(res.getSource(), res.getDestination(), res.isClearCache());
            } else {
                config = getJBakeConfigurationFactory().createDefaultJbakeConfiguration(res.getSource(), res.getDestination(), res.isClearCache());
            }
        } catch (final ConfigurationException e) {
            throw new JBakeException("Configuration error: " + e.getMessage(), e);
        }
        logger.info("JBake " + config.getVersion() + " (" + config.getBuildTimeStamp() + ") [http://jbake.org]");
        logger.info("");

        if (res.isHelpNeeded()) {
            printUsage(res);
            // Help was requested, so we are done here
            return;
        }

        if (res.isBake()) {
            baker.bake(config);
        }

        if (res.isInit()) {
            initStructure(res.getTemplate(), config);
        }

//        if (res.isRunServer()) {
//            watcher.start(config);
//            // TODO: short term fix until bake, server, init commands no longer share underlying values (such as source/dest)
//            if (res.isBake()) {
//                // bake and server commands have been run together
//                if (res.getDestination() != null) {
//                    // use the destination provided via the commandline
//                    runServer(res.getDestination(), config.getServerPort());
//                } else if (!res.getSource().getPath().equals(".")) {
//                    // use the source folder provided via the commandline
//                    runServer(res.getSource(), config.getServerPort());
//                } else {
//                    // use the default DESTINATION_FOLDER value
//                    runServer(config.getDestinationFolder(), config.getServerPort());
//                }
//            } else {
//                // use the default destination folder
//                runServer(config.getDestinationFolder(), config.getServerPort());
//            }
//        }
    }

    private LaunchOptions parseArguments(String[] args) {
        LaunchOptions res = new LaunchOptions();
        CmdLineParser parser = new CmdLineParser(res);
        try {
            parser.parseArgument(args);
        } catch (final CmdLineException e) {
            printUsage(res);
            throw new JBakeException("Invalid commandline arguments: " + e.getMessage(), e);
        }
        return res;
    }

    private void printUsage(Object options) {
        CmdLineParser parser = new CmdLineParser(options);
        StringWriter sw = new StringWriter();
        sw.append(USAGE_PREFIX + "\n");
        sw.append(ALT_USAGE_PREFIX + " <source> <destination>\n");
        sw.append(ALT_USAGE_PREFIX + " [OPTION]... [<value>...]\n\n");
        sw.append("Options:");
        logger.info("{}", sw.toString());
        parser.getProperties().withUsageWidth(100);
        parser.printUsage(System.out);
    }

    private void initStructure(String type, JBakeConfiguration config) {
        Init init = new Init(config);
//        try {
//            File templateFolder = FileUtil.getRunningLocation();
//            File outputFolder;
//            if (config.getSourceFolder() != null) {
//                outputFolder = config.getSourceFolder();
//            } else {
//                outputFolder = new File(".");
//            }
//            init.run(outputFolder, templateFolder, type);
//            logger.info("Base folder structure successfully created.");
//        } catch (final Exception e) {
//            final String msg = "Failed to initialise structure: " + e.getMessage();
//            throw new JBakeException(msg, e);
//        }
    }

    public JBakeConfigurationFactory getJBakeConfigurationFactory() {
        return configurationFactory;
    }

    public void setJBakeConfigurationFactory(JBakeConfigurationFactory factory) {
        configurationFactory = factory;
    }
}
