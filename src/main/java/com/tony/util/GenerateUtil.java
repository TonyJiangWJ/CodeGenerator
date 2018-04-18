package com.tony.util;

import com.tony.VelocityParser;
import com.tony.config.ConfigInfo;
import com.tony.model.TargetClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangwj20966 2018/4/18
 */
public class GenerateUtil {
    private static TargetClass targetClass;
    private static VelocityParser parser;
    private static ConfigInfo configInfo;
    private static String filePath = ConfigParser.getInstance().getFilePath();


    public static void generateAll(ConfigInfo configInfo) {
        targetClass = ColumnInfoGetUtil.getInstance().getClassInfoWithRemarksByTableName(configInfo);
        parser = new VelocityParser();
        GenerateUtil.configInfo = configInfo;
        generateModel();
        generateSQL();
    }

    private static void generateSQL() {
        Map<String, Object> params = new HashMap<>();
        params.put("target", targetClass);
        parser.writePage(params, "/sqlmap/ibatis_sqlmap.vm", filePath, "sqlMap/" + configInfo.getDesc() + "_SqlMap.xml");
    }

    private static void generateModel() {
        Map<String, Object> params = new HashMap<>();
        params.put("target", targetClass);
        parser.writePage(params, "/model/model.vm", filePath, "model/" + configInfo.getClassName() + ".java");
    }


}

