package com.tony.enums;

/**
 * @author jiangwj20966 2018/4/19
 */
public enum EnumXmlTagNames {
    CONFIGS("configs", "配置信息根节点"),
    AUTHOR_INFO("authorInfo", "作者信息"),
    FILE_PATH("filePath", "文件保存路径"),
    JDBC_CONFIG("jdbcConfig", "jdbc配置"),
    JDBC_HOST("host", "host地址"),
    JDBC_PORT("port", "端口"),
    JDBC_DATABASE("database", "数据库"),
    JDBC_USER_NAME("userName", "用户名"),
    JDBC_PASSWORD("password", "密码"),
    CONFIG("config", "配置信息"),
    CLASS_NAME("className", "类名"),
    PACKAGE_NAME("packageName", "包名"),
    QUERY_PACKAGE_NAME("queryPackageName", "query类包名"),
    SERVICE_PACKAGE_NAME("servicePackageName", "service包名"),
    SERVICE_API_PACKAGE_NAME("serviceApiPackageName", "service接口包名"),
    DAO_PACKAGE_NAME("daoPackageName", "dao包名"),
    DESC_NAME("desc", "描述SqlMap名称"),
    TABLE_NAME("tableName", "表名"),
    TEMPLATES("templates", "模板配置信息根节点"),
    TEMPLATE("template", "模板配置"),
    TEMPLATE_NAME("templateName", "模板名称，相对模板路径"),
    TARGET_FILE_NAME("targetFileName", "目标文件名称")
    ;
    private String tagName;
    private String desc;

    EnumXmlTagNames(String tagName, String desc) {
        this.tagName = tagName;
        this.desc = desc;
    }

    public String getTagName() {
        return tagName;
    }

    public String getDesc() {
        return desc;
    }
}
