package com.github.sunflowerlb.framework.core.dubbo.trace.model;

/**
 * <pre>
 * Created by lb on 14-9-29.下午2:26
 * </pre>
 */
public enum AppType {

    WEB("web", "web节点"),

    SERVICE("service", "服务端节点"), ;

    private String type;

    private String desc;

    AppType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
