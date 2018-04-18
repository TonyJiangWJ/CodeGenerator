package com.tony.util;

import com.tony.config.ConfigInfo;
import com.tony.config.JdbcConfig;
import com.tony.model.ColumnField;
import com.tony.model.TargetClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

/**
 * @author jiangwj20966 2018/4/18
 */
public class ColumnInfoGetUtil {

    private static ColumnInfoGetUtil INSTANCE = null;
    private String url;
    private JdbcConfig config;
    private Connection connection;
    private Logger logger;
    private static final Pattern BIGINT_PATTERN = Pattern.compile("bigint", Pattern.CASE_INSENSITIVE);
    private static final Pattern INT_PATTERN = Pattern.compile("int", Pattern.CASE_INSENSITIVE);

    private ColumnInfoGetUtil() {
        init();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, config.getUserName(), config.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        config = ConfigParser.getInstance().getJdbcConfig();
        url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8", config.getHost(), config.getPort(), config.getDatabase());
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public static ColumnInfoGetUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ColumnInfoGetUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ColumnInfoGetUtil();
                }
            }
        }
        return INSTANCE;
    }

    public TargetClass getClassInfoByTableName(ConfigInfo config) {
        try {
            Statement statement = connection.createStatement();
            ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM " + config.getTableName()).getMetaData();
            int l = metaData.getColumnCount();
            TargetClass targetClass = new TargetClass();
            targetClass.setTableName(config.getTableName());
            targetClass.setPackageName(config.getPackageName());
            targetClass.setClassName(config.getClassName());
            targetClass.setDesc(config.getDesc());
            if (l > 0) {
                for (int i = 1; i <= l; i++) {
                    ColumnField field = new ColumnField();
                    field.setColumn(metaData.getColumnName(i));
                    field.setProperty(underScore2CamelCase(metaData.getColumnName(i)));
                    field.setJavaType(switchJavaType(metaData.getColumnTypeName(i)));
                    field.setJdbcType(switchJdbcType(metaData.getColumnTypeName(i)));
                    targetClass.getFieldList().add(field);
                }
            }
            return targetClass;
        } catch (SQLException e) {
            logger.error("获取数据库连接失败");
        }
        return null;
    }

    public TargetClass getClassInfoWithRemarksByTableName(ConfigInfo config) {
        try {
            ResultSet rs = connection.getMetaData().getColumns(null, connection.getSchema(), config.getTableName().toUpperCase(), "%");
            TargetClass targetClass = new TargetClass();
            targetClass.setTableName(config.getTableName());
            targetClass.setPackageName(config.getPackageName());
            targetClass.setClassName(config.getClassName());
            targetClass.setDesc(config.getDesc());
            while (rs.next()) {
                ColumnField field = new ColumnField();
                field.setColumn(rs.getString("COLUMN_NAME"));
                field.setProperty(underScore2CamelCase(rs.getString("COLUMN_NAME")));
                field.setJavaType(switchJavaType(rs.getString("TYPE_NAME")));
                field.setJdbcType(switchJdbcType(rs.getString("TYPE_NAME")));
                field.setRemarks(rs.getString("REMARKS"));
                targetClass.getFieldList().add(field);
            }
            return targetClass;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String underScore2CamelCase(String strs) {
        String[] elems = strs.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < elems.length; ++i) {
            elems[i] = elems[i].toLowerCase();
            if (i != 0) {
                elems[i] = elems[i].substring(0, 1).toUpperCase() + elems[i].substring(1);
            }
            sb.append(elems[i]);
        }

        return sb.toString();
    }

    private String switchJavaType(String source) {
        if (BIGINT_PATTERN.matcher(source).find()) {
            return "Long";
        }
        if (INT_PATTERN.matcher(source).find()) {
            return "Integer";
        }

        if ("varchar".equals(source.toLowerCase())) {
            return "String";
        }

        if ("char".equals(source.toLowerCase())) {
            return "String";
        }

        if ("text".equals(source.toLowerCase())) {
            return "String";
        }

        if ("timestamp".equals(source.toLowerCase())) {
            return "Date";
        }

        if ("date".equals(source.toLowerCase())) {
            return "Date";
        }
        if ("datetime".equals(source.toLowerCase())) {
            return "Date";
        }

        if ("float".equals(source.toLowerCase())) {
            return "Double";
        }

        if ("bit".equals(source.toLowerCase())) {
            return "Byte";
        }
        logger.error("no match javaType found source:{}", source);
        return source;
    }


    private String switchJdbcType(String source) {
        if (BIGINT_PATTERN.matcher(source).find()) {
            return "BIGINT";
        }
        if (INT_PATTERN.matcher(source).find()) {
            return "INTEGER";
        }

        if ("varchar".equals(source.toLowerCase())) {
            return "VARCHAR";
        }

        if ("char".equals(source.toLowerCase())) {
            return "VARCHAR";
        }

        if ("text".equals(source.toLowerCase())) {
            return "VARCHAR";
        }

        if ("timestamp".equals(source.toLowerCase())) {
            return "TIMESTAMP";
        }

        if ("date".equals(source.toLowerCase())) {
            return "TIMESTAMP";
        }
        if ("datetime".equals(source.toLowerCase())) {
            return "TIMESTAMP";
        }

        if ("float".equals(source.toLowerCase())) {
            return "DOUBLE";
        }

        if ("bit".equals(source.toLowerCase())) {
            return "BYTE";
        }

        logger.error("no match jdbcType found source:{}", source);
        return source;
    }
}
