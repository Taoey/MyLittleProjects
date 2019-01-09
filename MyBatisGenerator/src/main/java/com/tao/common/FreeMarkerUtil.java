package com.tao.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FreeMarkerUtil {


    /**
     * 根据模板建立一个模板文件
     * @param templateDirectory 模板文件目录
     * @param templateFileName  模板文件名
     * @param outFilePath       输出文件位置
     * @param map               map数据
     * @throws IOException
     * @throws TemplateException
     */
    public static void makeFreeMarker(File templateDirectory ,String templateFileName,String outFilePath, Map<String,Object> map) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(templateDirectory);
        configuration.setDefaultEncoding("utf-8");

        Template template = configuration.getTemplate(templateFileName);
        Writer out = new FileWriter(outFilePath);
        template.process(map, out);
        out.close();
    }
}
