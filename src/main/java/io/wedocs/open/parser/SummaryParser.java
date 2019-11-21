package io.wedocs.open.parser;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.MutableDataSet;
import io.wedocs.open.model.Summary;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * summary解析器
 * <p>经过研究gitbook源码产生的新灵感，由于解析summary的格式非常复发,所以采用先转换给markdown在转成java对象</p>
 * Created by wangkun23 on 2019/11/4.
 */
@Component
public class SummaryParser {

    final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Parse a given file and transform to a model representation used by {@link MarkdownEngine} implementations
     * to render the file content.
     *
     * @param file The file to be parsed
     * @return A model representation of the given file
     */
    public Summary parse(File file) {
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        options.set(Parser.HTML_BLOCK_PARSER, false);
        //options.set(Parser.EXTENSIONS, Arrays.asList(WikiLinkExtension.create()));
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Summary summary = new Summary();
        try {
            String data = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Node document = parser.parse(data);
            String html = renderer.render(document);
            Document doc = Jsoup.parse(html);
            Element element = doc.selectFirst("body>ul");
            summary.setLevel(0);
            parseItems(element, summary);
        } catch (IOException ex) {
            logger.error("", ex);
        }
        return summary;
    }

    /**
     * 解析gitbook summary的子元素
     * <ul>
     * <li>
     * <p><a href="https://github.com">前言</a></p>
     * </li>
     * <li>
     * <p><a href="https://github.com">第1章 介绍</a></p>
     * <ul>
     * <li><a href="https://github.com">1.1 LoRaWAN Classes</a></li>
     * <li><a href="https://github.com">1.2 文档约定</a></li>
     * <li><a href="https://github.com">1.3 介绍</a>
     * <ul>
     * <li><a href="https://github.com">1.3.1 LoRaWAN Classes</a></li>
     * <li><a href="https://github.com">1.3.2 文档约定</a></li>
     * Disconnected from the target VM, address: '127.0.0.1:52018', transport: 'socket'
     * </ul>
     * </li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param element
     * @param summary
     */
    private void parseItems(Element element, Summary summary) {
        for (Element liElement : element.select(">li")) {
            Element aElement = liElement.selectFirst("a");
            String href = aElement.attr("href");
            String title = aElement.text();

            Summary summaryItem = new Summary();
            summaryItem.setPath(href);
            summaryItem.setTitle(title);
            summaryItem.setLevel(summary.getLevel() + 1);

            summaryItem.setExists(false);
            summaryItem.setExternal(false);
            summaryItem.setIntroduction(false);
            summary.getChildren().add(summaryItem);
            Element children = liElement.selectFirst("ul");
            if (children != null) {
                parseItems(children, summaryItem);
            }
        }
    }
}
