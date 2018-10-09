package com.tony.util;

import com.tony.config.ConfigInfo;
import com.tony.config.JdbcConfig;
import com.tony.enums.EnumXmlTagNames;
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
        InputStream configInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PropertyHelper.getGenerateConfigName());
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
                for (Object e : root.elements(EnumXmlTagNames.CONFIG.getTagName())) {
                    child = (Element) e;
                    config = new ConfigInfo();
                    config.setPackageName(getValueByTagName(EnumXmlTagNames.PACKAGE_NAME.getTagName(), child));
                    config.setQueryPackageName(getValueByTagName(EnumXmlTagNames.QUERY_PACKAGE_NAME.getTagName(), child));
                    config.setServicePackageName(getValueByTagName(EnumXmlTagNames.SERVICE_PACKAGE_NAME.getTagName(), child));
                    config.setServiceApiPackageName(getValueByTagName(EnumXmlTagNames.SERVICE_API_PACKAGE_NAME.getTagName(), child));
                    config.setDaoPackageName(getValueByTagName(EnumXmlTagNames.DAO_PACKAGE_NAME.getTagName(), child));

                    config.setClassName(getValueByTagName(EnumXmlTagNames.CLASS_NAME.getTagName(), child));
                    config.setDesc(getValueByTagName(EnumXmlTagNames.DESC_NAME.getTagName(), child));
                    config.setTableName(getValueByTagName(EnumXmlTagNames.TABLE_NAME.getTagName(), child));
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
            Element jdbcConfigTag = root.element(EnumXmlTagNames.JDBC_CONFIG.getTagName());
            jdbcConfig = new JdbcConfig();

            jdbcConfig.setHost(getValueByTagName(EnumXmlTagNames.JDBC_HOST.getTagName(), jdbcConfigTag));
            jdbcConfig.setPort(getValueByTagName(EnumXmlTagNames.JDBC_PORT.getTagName(), jdbcConfigTag));
            jdbcConfig.setDatabase(getValueByTagName(EnumXmlTagNames.JDBC_DATABASE.getTagName(), jdbcConfigTag));
            jdbcConfig.setUserName(getValueByTagName(EnumXmlTagNames.JDBC_USER_NAME.getTagName(), jdbcConfigTag));
            jdbcConfig.setPassword(getValueByTagName(EnumXmlTagNames.JDBC_PASSWORD.getTagName(), jdbcConfigTag));

        }
        return jdbcConfig;
    }

    public String getFilePath() {
        if (filePath == null) {
            filePath = getValueByTagName(EnumXmlTagNames.FILE_PATH.getTagName(), root);
        }
        return filePath;
    }

    public String getAuthorInfo() {
        if (authorInfo == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            authorInfo = getValueByTagName(EnumXmlTagNames.AUTHOR_INFO.getTagName(), root) + " " + sdf.format(new Date());
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
