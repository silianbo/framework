package com.github.sunflowerlb.framework.core.dubbo.trace.model;

/**
 * <pre>
 * Created by lb on 14-9-29.下午4:49
 * </pre>
 */
public enum IdTypeEnums {

    SPAN_ID(1, "生成SPAN的ID"),

    TRACE_ID(2, "生成TRACE的ID");

    private int    type;

    private String desc;

    IdTypeEnums(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
