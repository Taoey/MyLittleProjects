package com.tao;

import com.tao.common.FreeMarkerUtil;
import com.tao.common.PropertyUtil;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main2 {
    public static void main(String[] args) throws IOException, TemplateException {

        final String  TEMPLATE_FILE_NAME = "Mybatis-genertor-template.ftl";
        final String  OUT_FILEPATH = "Mybatis-genertor-template-test.ftl";

        //读取数据库配置文件
        String driverClass = PropertyUtil.getProperty("driverClass");
        String connectionURL =  PropertyUtil.getProperty("connectionURL");
        String userId =  PropertyUtil.getProperty("userId");
        String password =  PropertyUtil.getProperty("password");

        //读取导出数据位置
        String targetProject =  PropertyUtil.getProperty("targetProject");
        String pojotargetPackage =  PropertyUtil.getProperty("pojotargetPackage");
        String daotargetPackage =  PropertyUtil.getProperty("daotargetPackage");

        Map<String,Object> map = new HashMap<String, Object>();

        map.put("driverClass",driverClass);
        URL template = Main2.class.getClassLoader().getResource("template");
        String path = template.getPath();
        FreeMarkerUtil.makeFreeMarker(new File(path),TEMPLATE_FILE_NAME,OUT_FILEPATH,map);






    }
}
