package io.wedocs.open.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.wedocs.open.config.DefaultJBakeConfiguration;
import io.wedocs.open.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * 数据和模板组合
 * Created by wangkun23 on 2019/11/14.
 */
@Component
public class TemplateRender {
    final Logger logger = LoggerFactory.getLogger(getClass());



    @Autowired
    private Configuration configurer;

    @Resource
    private DefaultJBakeConfiguration config;

    public void render(Article article) {
        try {
            // 解析完毕 根据模板生成文件
            logger.info("uri {}", article.getUri());
            logger.info("getDestinationFolder {}", config.getDestinationFolder());

            Template template = configurer.getTemplate("post.ftl");
            File target = new File(config.getDestinationFolder(), article.getUri());
            if (!target.exists()) {
                target.getParentFile().mkdirs();
                target.createNewFile();
            }
            Writer fileWriter = new FileWriter(target);
            template.process(article, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception ex) {
            throw new RuntimeException("Failed crawling file: " + article.getFile().getPath() + " " + ex.getMessage(), ex);
        }
    }
}
