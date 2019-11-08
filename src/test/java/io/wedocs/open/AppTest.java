package io.wedocs.open;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import io.wedocs.open.model.Summary;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Base64Utils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by wangkun23 on 2019/10/26.
 */
public class AppTest {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void md5() throws UnsupportedEncodingException {

    }

    @Test
    public void summary() throws IOException {
        //MutableDataSet options = new MutableDataSet();
        // uncomment to set optional extensions
        //options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));
        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        //options.setFrom(ParserEmulationProfile.MARKDOWN);
        //options.set(Parser.EXTENSIONS, Arrays.asList(new Extension[] { TablesExtension.create()}));

        // MutableDataHolder options = new MutableDataSet();
        // options.setFrom(ParserEmulationProfile.MARKDOWN);
        // enable table parse!
        // options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));

        DataHolder options = PegdownOptionsAdapter.flexmarkOptions(Extensions.AUTOLINKS);

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        String data = FileUtils.readFileToString(new ClassPathResource("SUMMARY.md").getFile(), StandardCharsets.UTF_8);
        // You can re-use parser and renderer instances
        Node document = parser.parse(data);
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(html);
    }


    @Test
    public void builder() throws IOException {
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        options.set(Parser.HTML_BLOCK_PARSER, false);
        //options.set(Parser.EXTENSIONS, Arrays.asList(WikiLinkExtension.create()));
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String data = FileUtils.readFileToString(new ClassPathResource("SUMMARY.md").getFile(), StandardCharsets.UTF_8);
        Node document = parser.parse(data);
        String html = renderer.render(document);
        Document doc = Jsoup.parse(html);
        Element element = doc.selectFirst("body>ul");
        Summary summary = new Summary();
        summary.setLevel(0);
        parse(element, summary);
        logger.info("{}", summary);
    }

    private void parse(Element element, Summary summary) {
        for (Element liElement : element.select(">li")) {
            String title = liElement.selectFirst("a").text();
            Summary summaryItem = new Summary();
            summaryItem.setTitle(title);
            summaryItem.setLevel(summary.getLevel() + 1);
            logger.info("{}", title);
            summary.getChildren().add(summaryItem);
            Element children = liElement.selectFirst("ul");
            if (children != null) {
                parse(children, summaryItem);
            }
        }
    }
}
