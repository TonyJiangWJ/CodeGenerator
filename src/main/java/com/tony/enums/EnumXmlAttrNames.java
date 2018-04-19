package com.tony.enums;

/**
 * @author jiangwj20966 2018/4/19
 */
public enum EnumXmlAttrNames {
    TEMPLATE_PATH("templatePath", "模板根路径"),
    TYPE("type", "类型"),
    REPLACEMENT("replacement", "待替换字符串"),
    REPLACE_WITH("replaceWith", "替换类型");
    private String attrName;
    private String desc;

    EnumXmlAttrNames(String attrName, String desc) {
        this.attrName = attrName;
        this.desc = desc;
    }

    public String getAttrName() {
        return attrName;
    }

    public String getDesc() {
        return desc;
    }
}
