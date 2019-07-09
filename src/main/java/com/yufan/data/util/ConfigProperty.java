package com.yufan.data.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 创建人: lirf
 * 创建时间:  2019/4/15 11:59
 * 功能介绍:
 */
public class ConfigProperty {

    private final Logger log = Logger.getLogger(ConfigProperty.class);
    private Properties property = new Properties();

    public ConfigProperty() {
        InputStream input = null;
        try {
            input = ConfigProperty.class.getResourceAsStream("/config.properties");
            property.load(input);
        } catch (Exception e) {
            log.error("获取配置文件config.properties信息出错:", e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getValue(String key) {
        return property.getProperty(key);
    }
}
