package io.wedocs.open.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.util.Map;

/**
 * Created by wangkun23 on 2019/10/31.
 */
@NoArgsConstructor
@ToString
public class Page {

    @Setter
    @Getter
    private File file;

    /**
     * Attributes extracted from the YAML header
     */
    @Setter
    @Getter
    private Map<String, String> attributes;

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

    public Page(File file, Map<String, String> attributes, String content, String dir) {
        this.file = file;
        this.attributes = attributes;
        this.content = content;
        this.dir = dir;
    }
}
