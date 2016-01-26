package com.github.sunflowerlb.framework.core.dubbo.trace.support;

import com.github.sunflowerlb.framework.core.dubbo.trace.model.Annotation;

/**
 * <pre>
 * Created by lb on 14-9-30.上午10:15
 * </pre>
 */
public abstract class Spans {

    /**
     * 生成span
     * 
     * @param type
     * @param endpoint
     * @param start
     * @return
     */
    public static Annotation genAnnotation(Annotation.AnnType type, long start) {
        Annotation annotation = new Annotation();
        annotation.setValue(type);
        annotation.setTimestamp(start);
        // annotation.setSize(size);
        return annotation;
    }
}
