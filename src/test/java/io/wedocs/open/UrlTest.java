package io.wedocs.open;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Created by wangkun23 on 2019/11/20.
 */
public class UrlTest {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void builder() throws IOException {
        String webUrl = "test.md";
        URL url = new URL(webUrl);
        logger.info("{}", url.getProtocol());
    }

}
