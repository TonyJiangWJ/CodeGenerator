package com.tony.util;

import com.tony.VelocityParser;
import com.tony.config.ConfigInfo;
import com.tony.config.TemplatePathConfig;
import com.tony.model.TargetClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangwj20966 2018/4/18
 */
public class GenerateUtil {
    private static String filePath = ConfigParser.getInstance().getFilePath();


    public static void generateAll(ConfigInfo configInfo) {
        TargetClass targetClass = ColumnInfoGetUtil.getInstance().getClassInfoWithRemarksByTableName(configInfo);
        VelocityParser parser = new VelocityParser();
        Map<String, Object> params = new HashMap<>(1);
        params.put("target", targetClass);
        for (TemplatePathConfig config : TemplateConfigParser.getInstance().getTemplatePathConfigs()) {
            parser.writePage(params, config.getTemplateName(), filePath, getFileName(config, configInfo));
        }
    }

    private static String getFileName(TemplatePathConfig config, ConfigInfo configInfo) {
        if ("desc".equals(config.getReplaceWith())) {
            return config.getTargetFileName().replace(config.getReplacement(), configInfo.getDesc());
        } else {
            return config.getTargetFileName().replace(config.getReplacement(), configInfo.getClassName());
        }
    }

}

