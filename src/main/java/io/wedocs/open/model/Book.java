package io.wedocs.open.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangkun23 on 2019/10/30.
 */
public class Book {

    @Setter
    @Getter
    private String sha1;

    @Setter
    @Getter
    private String sourceuri;

    @Setter
    @Getter
    private String rendered;

    @Setter
    @Getter
    private String cached;

    @Setter
    @Getter
    private String status;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String label;
}
