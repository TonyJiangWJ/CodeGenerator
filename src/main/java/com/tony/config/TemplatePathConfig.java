package com.tony.config;

/**
 * @author jiangwj20966 2018/4/18
 */
public class TemplatePathConfig {
    private String type;
    private String templateName;
    private String targetFileName;
    private String replaceWith;
    private String replacement;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public String getReplaceWith() {
        return replaceWith;
    }

    public void setReplaceWith(String replaceWith) {
        this.replaceWith = replaceWith;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }
}
