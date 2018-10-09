package com.tony.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jiangwj20966 2018/10/9
 */
public class PropertyHelper {

    private static Logger logger = LoggerFactory.getLogger(PropertyHelper.class);

    public static String getGenerateConfigName() {
        InputStream configInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("generate.properties");
        Properties properties = new Properties();
        try {
            properties.load(configInputStream);
        } catch (IOException e) {
            logger.error("读取配置信息失败", e);
        }
        return properties.getProperty("generate.config.name");
    }

    public static String getTemplatePath() {
        InputStream configInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("generate.properties");
        Properties properties = new Properties();
        try {
            properties.load(configInputStream);
        } catch (IOException e) {
            logger.error("读取配置信息失败", e);
        }
        return properties.getProperty("template.path.config.name");
    }
}
