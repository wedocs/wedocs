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

    @Setter
    @Getter
    private String uri;

    /**
     * Content of the page
     */
    @Setter
    @Getter
    private String content;


    public Article(String sha1, File file, String uri, String content) {
        this.sha1 = sha1;
        this.file = file;
        this.uri = uri;
        this.content = content;
    }
}
