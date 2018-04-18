package com.tony.model;

import com.tony.config.ConfigInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangwj20966 2018/4/18
 */
public class TargetClass extends ConfigInfo {

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

    public String getLowerStartName() {
        return getClassName().substring(0, 1).toLowerCase() + getClassName().substring(1);
    }

    public String getFullClassName() {
        return getPackageName() + "." + getClassName();
    }

    public List<ColumnField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ColumnField> fieldList) {
        this.fieldList = fieldList;
    }
}
