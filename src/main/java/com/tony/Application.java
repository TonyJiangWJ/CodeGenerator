package com.tony;

import com.tony.config.ConfigInfo;
import com.tony.util.ConfigParser;
import com.tony.util.GenerateUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author jiangwj20966 2018/4/2
 */
public class Application {

    public static void main(String[] args) throws Exception {

        List<ConfigInfo> configInfos = ConfigParser.getInstance().getConfigs();
        if (CollectionUtils.isNotEmpty(configInfos)) {
            for (ConfigInfo configInfo : configInfos) {
                GenerateUtil.generateAll(configInfo);
            }
        }
    }


}
