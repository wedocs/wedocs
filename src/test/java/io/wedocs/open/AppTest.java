package io.wedocs.open;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Base64Utils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

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

        MutableDataHolder options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        // enable table parse!
        options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));

        // DataHolder options = PegdownOptionsAdapter.flexmarkOptions(Extensions.ANCHORLINKS);


        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        String data = FileUtils.readFileToString(new ClassPathResource("SUMMARY.md").getFile(), StandardCharsets.UTF_8);
        // You can re-use parser and renderer instances
        Node document = parser.parse(data);
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(html);
    }


    @Test
    public void builder() {
        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse("This is [link](https://github.com/freewind-demos/java-flexmark-java-demo/blob/master/src/main/java/demo/Hello.java)");
        String html = renderer.render(document);
        System.out.println(html);
    }

}
