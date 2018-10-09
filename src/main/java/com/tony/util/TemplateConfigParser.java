package com.tony.util;

import com.tony.config.TemplatePathConfig;
import com.tony.enums.EnumXmlAttrNames;
import com.tony.enums.EnumXmlTagNames;
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
        InputStream configInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PropertyHelper.getTemplatePath());
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
                for (Object e : root.elements(EnumXmlTagNames.TEMPLATE.getTagName())) {
                    child = (Element) e;
                    config = new TemplatePathConfig();
                    config.setReplacement(getAttrValueWithTag(EnumXmlTagNames.TARGET_FILE_NAME.getTagName(), EnumXmlAttrNames.REPLACEMENT.getAttrName(), child));
                    config.setReplaceWith(getAttrValueWithTag(EnumXmlTagNames.TARGET_FILE_NAME.getTagName(), EnumXmlAttrNames.REPLACE_WITH.getAttrName(), child));
                    config.setTargetFileName(getValueByTagName(EnumXmlTagNames.TARGET_FILE_NAME.getTagName(), child));
                    config.setTemplateName(getValueByTagName(EnumXmlTagNames.TEMPLATE_NAME.getTagName(), child));
                    config.setType(getAttrValue(EnumXmlAttrNames.TYPE.getAttrName(), child));
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
            templatePath = getAttrValue(EnumXmlAttrNames.TEMPLATE_PATH.getAttrName(), root);
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
