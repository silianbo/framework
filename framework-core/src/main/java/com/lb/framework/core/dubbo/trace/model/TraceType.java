package com.lb.framework.core.dubbo.trace.model;

/**
 * <pre>
 * Created by lb on 14-9-29.下午2:26
 * </pre>
 */
public enum TraceType {

    URL("url", "web请求"),

    CALL("call", "服务调用"),

    SERVICE("service", "服务端提供"),

    SQL("sql", "数据库操作"), ;

    private String type;

    private String desc;

    TraceType(String type, String desc) {
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
