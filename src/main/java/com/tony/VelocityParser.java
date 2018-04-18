package com.tony;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;

/**
 * @author jiangwj20966 2018/4/18
 */
public class VelocityParser {

    private Logger logger;

    public static void main(String[] args) {
        VelocityParser parser = new VelocityParser();
        parser.writePage(null, "sqlmap/ibatis_sqlmap.vm", "d:/testsql", "test.xml");
    }

    public VelocityParser() {
        init();
    }

    private VelocityEngine velocityEngine;

    private String templatePath = "/template/";

    /**
     * 初始化velocity引擎
     */
    private void init() {
        logger = LoggerFactory.getLogger(VelocityParser.class);
        try {
            Properties properties = new Properties();
            properties.setProperty(Velocity.RESOURCE_LOADER, "classpath");
            properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            properties.setProperty("classpath.resource.loader.description", "Velocity File Resource Loader");
            properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath);
            properties.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "true");
            properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
            properties.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
            properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
            properties.setProperty("directive.set.null.allowed", "true");

            velocityEngine = new VelocityEngine();
            velocityEngine.init(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 输出静态化文件
     *
     * @param params       赋值到模板的参数
     * @param templateName 模板路径名
     * @param targetPath   配置的目标文件路径
     * @param fileName     目标文件名称
     */
    public void writePage(Map<String, Object> params, String templateName, String targetPath, String fileName) {

        if (velocityEngine == null) {
            throw new IllegalStateException("velocityEngine未初始化");
        }
        if (StringUtils.isEmpty(targetPath)) {
            throw new IllegalStateException("静态化文件保存路径未配置");
        }
        FileOutputStream fileOutputStream = null;
        BufferedWriter writer = null;
        try {
            VelocityContext context = buildContext(params);
//            buildComplexAttribute(context);
//            setCommonObjects(context);
            String targetFilePath = targetPath + File.separator + fileName;
            File target = new File(targetFilePath);
            boolean flowFlag = true;
            if (!target.getParentFile().exists()) {
                flowFlag = target.getParentFile().mkdirs();
            }
            if (flowFlag) {
                Template template = velocityEngine.getTemplate(templatePath + templateName, "UTF-8");
                fileOutputStream = new FileOutputStream(target);
                writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
                template.merge(context, writer);
                logger.info("页面生成成功，targetPath:{}", target.getAbsolutePath());
            } else {
                logger.error("路径生成失败！");
                throw new IllegalStateException("生成静态化页面失败，路径生成失败！");
            }
        } catch (Exception e) {
            logger.error("页面生成异常", e);
            throw new IllegalStateException("生成静态化页面失败", e);
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (Exception e) {
                logger.error("关闭writer异常", e);
            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                logger.error("关闭文件输出流异常", e);
            }
        }
    }

    /**
     * 构建velocityContext
     *
     * @param params 传入不需额外处理的变量,velocity模板变量名作为key
     * @return velocityContext
     */
    private VelocityContext buildContext(Map<String, Object> params) {
        VelocityContext context = new VelocityContext();

        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                checkAndPutContext(context, entry.getKey(), entry.getValue());
            }
        }
        return context;
    }

    /**
     * 赋值变量，在变量名重复时打印警告日志
     *
     * @param context velocityContext上下文
     * @param key     变量名
     * @param value   内容
     */
    protected void checkAndPutContext(VelocityContext context, String key, Object value) {
        if (context.containsKey(key)) {
            logger.warn("velocityContext中已经存在键值：{}，原有内容将被覆盖", key);
        }
        context.put(key, value);
    }
}
