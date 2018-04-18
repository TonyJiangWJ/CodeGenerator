package com.tony.util;

import com.tony.config.TemplatePathConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangwj20966 2018/4/18
 */
public class TemplateConfigParser {
    private static TemplateConfigParser INSTANCE;
    private static Logger logger = LoggerFactory.getLogger(TemplateConfigParser.class);
    private Element root;
    private Document document;
    private String templatePath;
    private List<TemplatePathConfig> templatePathConfigs;

    public static TemplateConfigParser getInstance() {
        if (INSTANCE == null) {
            synchronized (ConfigParser.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TemplateConfigParser();
                }
            }
        }
        return INSTANCE;
    }

    private TemplateConfigParser() {
        InputStream configInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("template-path-config.xml");
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

    public List<TemplatePathConfig> getTemplatePathConfigs() {
        if (templatePathConfigs == null) {
            try {
                templatePathConfigs = new ArrayList<>();
                Element child;
                TemplatePathConfig config;
                for (Object e : root.elements("template")) {
                    child = (Element) e;
                    config = new TemplatePathConfig();
                    config.setReplacement(getAttrValueWithTag("targetFileName", "replacement", child));
                    config.setReplaceWith(getAttrValueWithTag("targetFileName", "replaceWith", child));
                    config.setTargetFileName(getValueByTagName("targetFileName", child));
                    config.setTemplateName(getValueByTagName("templateName", child));
                    config.setType(getAttrValue("type", child));
                    templatePathConfigs.add(config);
                }
            } catch (Exception e) {
                logger.error("读取配置信息失败", e);
            }
        }
        return templatePathConfigs;
    }

    public String getTemplatePath() {
        if (templatePath == null) {
            templatePath = getAttrValue("templatePath", root);
        }
        return templatePath;
    }

    private static String getValueByTagName(String tagName, Element element) {
        Element child = element.element(tagName);
        if (child != null) {
            return child.getStringValue();
        }
        return "";
    }

    private static String getAttrValue(String attrName, Element element) {
        String result = element.attributeValue(attrName);
        return result == null ? "" : result;
    }

    private static String getAttrValueWithTag(String tagName, String attrName, Element element) {
        Element child = element.element(tagName);
        if (child != null) {
            return getAttrValue(attrName, child);
        }
        return "";
    }
}
