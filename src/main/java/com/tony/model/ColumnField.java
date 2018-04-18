package com.tony.model;

/**
 * @author jiangwj20966 2018/4/18
 */
public class ColumnField {
    private String property;
    private String column;
    private String jdbcType;
    private String javaType;
    private String remarks;

    public String getPropertyMethodName() {
        return property.substring(0, 1).toUpperCase() + property.substring(1);
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
