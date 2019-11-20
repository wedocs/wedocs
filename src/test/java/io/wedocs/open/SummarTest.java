package io.wedocs.open;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import io.wedocs.open.model.Summary;
import io.wedocs.open.parser.SummaryParser;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by wangkun23 on 2019/11/20.
 */
public class SummarTest {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void summary() throws IOException {
        SummaryParser summaryParser = new SummaryParser();
        Summary summary = summaryParser.parse(new ClassPathResource("SUMMARY.md").getFile());
        logger.info("{}", summary);
    }

}
