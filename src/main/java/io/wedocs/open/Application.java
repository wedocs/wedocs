package io.wedocs.open;

import io.wedocs.open.app.Oven;
import io.wedocs.open.common.JBakeException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.List;

/**
 * 启动类
 * Created by wangkun23 on 2019/10/26.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String USAGE_PREFIX = "Usage: jbake";
    private final String ALT_USAGE_PREFIX = "   or  jbake";

    @Resource
    private Oven oven;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LaunchOptions res = parseArguments(args);
        if (res.isHelpNeeded()) {
            printUsage(res);
            // Help was requested, so we are done here
            return;
        }
        if (res.isBuild()) {
            oven.build();
            final List<Throwable> errors = oven.getErrors();
            if (!errors.isEmpty()) {
                final StringBuilder msg = new StringBuilder();
                // TODO: decide, if we want the all errors here
                msg.append(MessageFormat.format("JBake failed with {0} errors:\n", errors.size()));
                int errNr = 1;
                for (final Throwable error : errors) {
                    msg.append(MessageFormat.format("{0}. {1}\n", errNr, error.getMessage()));
                    ++errNr;
                }
                throw new JBakeException(msg.toString(), errors.get(0));
            }
        }
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
}
