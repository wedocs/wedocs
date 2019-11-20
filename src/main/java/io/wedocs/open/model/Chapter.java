package io.wedocs.open.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by wangkun23 on 2019/11/20.
 */
@ToString
@NoArgsConstructor
public class Chapter {

    @Setter
    @Getter
    private String path;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private int level;

    @Setter
    @Getter
    private Boolean exists;

    @Setter
    @Getter
    private Boolean external;

    @Setter
    @Getter
    private Boolean introduction;
}
