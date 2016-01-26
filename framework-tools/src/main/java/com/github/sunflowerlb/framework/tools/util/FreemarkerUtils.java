package com.github.sunflowerlb.framework.tools.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 * freemarker的工具类
 * 
 * @author lb
 * 
 */
public class FreemarkerUtils {

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtils.class);
    private static final Configuration cfg = new Configuration();
    private static final Cache<String, Template> tempCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();
    
    static {
        try {
            init();
        } catch (IOException e) {
            logger.error("FreemarkerUtils init fail", e);
        }
    }

    private static void init() throws IOException {
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        
        URL url = FreemarkerUtils.class.getResource("/template");
        File templateDir = null;
        if (url == null) {
            // TODO
            return;
        }
        if (url.getPath() != null) {
            templateDir = new File(url.getPath());
        } else {
            templateDir = new File("template");
        }
        if (!templateDir.exists()) {
            throw new FileNotFoundException("freemark's template dir not exists, default dir name is template");
        } else {
            logger.info("found ftl template dir: {}", templateDir.getAbsolutePath());
        }
        // Specify the data source where the template files come from.
        cfg.setDirectoryForTemplateLoading(templateDir);
    }

    /**
     * 生成模板
     * 
     * @param templateName
     *            模板名称
     * @param data
     *            模板数据
     * @return 模板生成后的数据，若模板生成出错，则返回null
     */
    public static String getTemplateStr(String templateName, Map<String, Object> data) {
        try {
            Template template = cfg.getTemplate(templateName);
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            String content = writer.toString();
            return content;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 生成模板
     * 
     * @param name
     *            模板名称
     * @param content
     *            模板内容
     * @param data
     *            模板数据
     * @return 模板生成后的数据，若模板生成出错，则返回null
     */
    public static String getTemplateStrByContent(final String name, final String content, Map<String, Object> data) {
        try {
            Template template = tempCache.get(name, new Callable<Template>(){
                @Override
                public Template call() throws Exception {
                    Template template = new Template(name, content, cfg);
                    return template;
                }
            });
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            String retValue = writer.toString();
            return retValue;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
