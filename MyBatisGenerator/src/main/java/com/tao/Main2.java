package com.tao;

import com.tao.common.FreeMarkerUtil;
import com.tao.common.PropertyUtil;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2 {
    public static void main(String[] args) throws Exception {

        Logger LOGGER = LoggerFactory.getLogger(Main2.class);
        final String  TEMPLATE_FILE_NAME = "Mybatis-genertor-template.ftl";
        final String  OUT_FILE_NAME = "Mybatis-genertor-template-test.xml";

        /////////////////////////
        ////生成配置文件
        ////////////////////////

        //读取数据库配置文件
        String driverClass = PropertyUtil.getProperty("driverClass");
        String connectionURL =  PropertyUtil.getProperty("connectionURL");
        String userId =  PropertyUtil.getProperty("userId");
        String password =  PropertyUtil.getProperty("password");

        //读取导出数据位置
        String targetProject =  PropertyUtil.getProperty("targetProject");
        String pojotargetPackage =  PropertyUtil.getProperty("pojotargetPackage");
        String daotargetPackage =  PropertyUtil.getProperty("daotargetPackage");
        String mapperPackage =  PropertyUtil.getProperty("mapperPackage");

        LOGGER.info("读取数据完毕");

        //TODO:查询该数据库中的所有表（JDBC）



        //把数据存入Map中
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("driverClass",driverClass);
        map.put("connectionURL",connectionURL);
        map.put("userId",userId);
        map.put("password",password);
        map.put("targetProject",targetProject);
        map.put("pojotargetPackage",pojotargetPackage);
        map.put("daotargetPackage",daotargetPackage);
        map.put("mapperPackage",mapperPackage);


        LOGGER.info("存入数据至Map完毕，data："+map.toString());


        //模板路径
        String path = ClassLoader.getSystemResource("template").getPath();


        //模板输出路径，生成genertor配置文件
        String outFilePath = ClassLoader.getSystemResource("").getPath()+OUT_FILE_NAME;

        FreeMarkerUtil.makeFreeMarker(new File(path),TEMPLATE_FILE_NAME,outFilePath,map);


        LOGGER.info("模板生成完成，开始导出数据");
        ////////////////////////////////
        ////生导出文件代码
        ////////////////////////////////


        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定逆向工程配置文件

        File configFile = new File(outFilePath);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);

        LOGGER.info("生成完成");





    }
}
