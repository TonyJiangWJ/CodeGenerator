package com.tony.config;

import org.apache.commons.lang.StringUtils;

/**
 * @author jiangwj20966 2018/4/18
 */
public class ConfigInfo {
    private String tableName;
    private String className;
    private String packageName;
    private String queryPackageName;
    private String servicePackageName;
    private String serviceApiPackageName;
    private String daoPackageName;
    private String mapperPackageName;
    private String desc;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getQueryPackageName() {
        return queryPackageName;
    }

    public void setQueryPackageName(String queryPackageName) {
        this.queryPackageName = queryPackageName;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

    public String getServiceApiPackageName() {
        return serviceApiPackageName;
    }

    public void setServiceApiPackageName(String serviceApiPackageName) {
        this.serviceApiPackageName = serviceApiPackageName;
    }

    public String getDaoPackageName() {
        return daoPackageName;
    }

    public void setDaoPackageName(String daoPackageName) {
        this.daoPackageName = daoPackageName;
    }

    public String getMapperPackageName() {
        if (StringUtils.isEmpty(this.mapperPackageName)) {
            return this.daoPackageName + ".mapper";
        }
        return mapperPackageName;
    }

    public void setMapperPackageName(String mapperPackageName) {
        this.mapperPackageName = mapperPackageName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
