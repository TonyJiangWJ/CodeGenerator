package com.tony.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangwj20966 2018/4/18
 */
public class TargetClass {

    private String packageName;
    private String className;
    private String tableName;
    private String desc;
    private List<ColumnField> fieldList;

    public TargetClass() {
        fieldList = new ArrayList<ColumnField>();
    }

    public boolean contains(String property) {
        for (ColumnField field : fieldList) {
            if (field.getProperty().equals(property)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsType(String type) {
        for (ColumnField field : fieldList) {
            if (field.getJavaType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public String getFullClassName() {
        return packageName + "." + className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ColumnField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ColumnField> fieldList) {
        this.fieldList = fieldList;
    }
}
