package com.xeiot.open;

import com.xeiot.open.common.JBakeException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.StringWriter;

/**
 * 启动类
 * Created by wangkun23 on 2019/10/26.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    private final String USAGE_PREFIX = "Usage: jbake";
    private final String ALT_USAGE_PREFIX = "   or  jbake";


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LaunchOptions res = parseArguments(args);
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
        System.out.println(sw.toString());
        parser.getProperties().withUsageWidth(100);
        parser.printUsage(System.out);
    }
}
