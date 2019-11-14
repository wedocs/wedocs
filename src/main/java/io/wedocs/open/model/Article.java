package io.wedocs.open.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

/**
 * Created by wangkun23 on 2019/11/14.
 */
@ToString
@NoArgsConstructor
public class Article {

    @Setter
    @Getter
    private String sha1;

    @Setter
    @Getter
    private File file;

    /**
     * Content of the page
     */
    @Setter
    @Getter
    private String content;

    /**
     * Direction of the text
     */
    @Setter
    @Getter
    private String dir;

    public Article(File file, String content, String dir) {
        this.file = file;
        this.content = content;
        this.dir = dir;
    }
}
