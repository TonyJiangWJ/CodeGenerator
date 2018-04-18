package com.tony.util;

import com.tony.config.ConfigInfo;
import com.tony.config.JdbcConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jiangwj20966 2018/4/18
 */
public class ConfigParser {
    private static ConfigParser INSTANCE;
    private Element root;
    private Document document;
    private static Logger logger = LoggerFactory.getLogger(ConfigParser.class);

    private List<ConfigInfo> configInfos;
    private JdbcConfig jdbcConfig;
    private String filePath;

    private String authorInfo;

    public static ConfigParser getInstance() {
        if (INSTANCE == null) {
            synchronized (ConfigParser.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigParser();
                }
            }
        }
        return INSTANCE;
    }

    private ConfigParser() {
        InputStream configInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("generate-config.xml");
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(configInputStream);
        } catch (DocumentException e) {
            logger.error("初始化读取XML配置信息失败", e);
        }
        if (document != null) {
            root = document.getRootElement();
        }

    }

    public List<ConfigInfo> getConfigs() {
        if (configInfos == null) {
            try {
                configInfos = new ArrayList<>();
                Element child;
                ConfigInfo config;
                for (Object e : root.elements("config")) {
                    child = (Element) e;
                    config = new ConfigInfo();
                    config.setPackageName(getValueByTagName("packageName", child));
                    config.setQueryPackageName(getValueByTagName("queryPackageName", child));
                    config.setServicePackageName(getValueByTagName("servicePackageName", child));
                    config.setDaoPackageName(getValueByTagName("daoPackageName", child));

                    config.setClassName(getValueByTagName("className", child));
                    config.setDesc(getValueByTagName("desc", child));
                    config.setTableName(getValueByTagName("tableName", child));
                    configInfos.add(config);
                }
                return configInfos;
            } catch (Exception e) {
                logger.error("读取配置信息失败", e);
            }
        }
        return configInfos;
    }

    public JdbcConfig getJdbcConfig() {
        if (jdbcConfig == null) {
            Element jdbcConfigTag = root.element("jdbcConfig");
            jdbcConfig = new JdbcConfig();

            jdbcConfig.setHost(getValueByTagName("host", jdbcConfigTag));
            jdbcConfig.setPort(getValueByTagName("port", jdbcConfigTag));
            jdbcConfig.setDatabase(getValueByTagName("database", jdbcConfigTag));
            jdbcConfig.setUserName(getValueByTagName("userName", jdbcConfigTag));
            jdbcConfig.setPassword(getValueByTagName("password", jdbcConfigTag));

        }
        return jdbcConfig;
    }

    public String getFilePath() {
        if (filePath == null) {
            filePath = getValueByTagName("filePath", root);
        }
        return filePath;
    }

    public String getAuthorInfo() {
        if (authorInfo == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            authorInfo = getValueByTagName("authorInfo", root) + " " + sdf.format(new Date());
        }
        return authorInfo;
    }

    private static String getValueByTagName(String tagName, Element element) {
        Element child = element.element(tagName);
        if (child != null) {
            return child.getStringValue();
        }
        return "";
    }
}
