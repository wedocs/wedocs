package io.wedocs.open.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkun23 on 2019/11/2.
 */
@ToString
@NoArgsConstructor
public class Summary {

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

    /**
     * 子目录
     */
    @Setter
    @Getter
    private List<Summary> children = new ArrayList<>();

}

