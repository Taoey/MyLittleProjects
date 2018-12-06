package com.tao;

import org.apache.commons.io.FileUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.*;
import java.util.*;

public class Main {

    @Test
    public void main() throws Exception{
        config();

        deleteFiles();

        generator();
    }


    public static void config() throws Exception{
        //数据库配置文件
        String driverClass = getProperty("driverClass");
        String connectionURL = getProperty("connectionURL");
        String userId = getProperty("userId");
        String password = getProperty("password");

        //导出数据位置
        String targetProject = getProperty("targetProject");
        String pojotargetPackage = getProperty("pojotargetPackage");
        String daotargetPackage = getProperty("daotargetPackage");


        //TODO：安全配置，假设用户没有设置以上的配置信息

        //重新配置 mybatis-genertor.xml
        File f = new File("src/main/resources/mybatis-genertor.xml");
        Document document = null;
        SAXReader saxReader = new SAXReader();
        document = saxReader.read(f); // 读取XML文件,获得document对象

        //获取根节点
        Element rootElement = document.getRootElement();

        //设置数据库
        Element jdbcConnection = (Element)document.selectSingleNode("/generatorConfiguration/context/jdbcConnection");

        jdbcConnection.attribute("driverClass").setData(driverClass);
        jdbcConnection.attribute("connectionURL").setData(connectionURL);
        jdbcConnection.attribute("userId").setData(userId);
        jdbcConnection.attribute("password").setData(password);

        Element javaModelGenerator = (Element)document.selectSingleNode("/generatorConfiguration/context/javaModelGenerator");
        javaModelGenerator.attribute("targetProject").setData(targetProject);
        javaModelGenerator.attribute("targetPackage").setData(pojotargetPackage);


        Element sqlMapGenerator = (Element)document.selectSingleNode("/generatorConfiguration/context/sqlMapGenerator");
        sqlMapGenerator.attribute("targetProject").setData(targetProject);
        sqlMapGenerator.attribute("targetPackage").setData(daotargetPackage);

        Element javaClientGenerator = (Element)document.selectSingleNode("/generatorConfiguration/context/javaClientGenerator");
        javaClientGenerator.attribute("targetProject").setData(targetProject);
        javaClientGenerator.attribute("targetPackage").setData(daotargetPackage);


        XMLWriter writer = new XMLWriter(new FileWriter(f));
        writer.write(document);
        writer.flush();
        writer.close();
    }


    /**
     * 清空之前的文件
     * @throws Exception
     */
    public static void deleteFiles() throws Exception{
        File daoDirectory = new File("src/main/java/com/tao/dao");
        FileUtils.deleteDirectory(daoDirectory);
        daoDirectory.mkdir();

        File pojoDirectory = new File("src/main/java/com/tao/pojo");
        FileUtils.deleteDirectory(daoDirectory);
        pojoDirectory.mkdir();
    }

    /**
     * 读取配置文件
     * @param key
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String getProperty(String key) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.load(new FileReader(Main.class.getClassLoader().getResource("mybatis-genertor-conf.properties").getPath()));

        String value =  prop.getProperty(key);
        return value;
    }

    /**
     * 获取所有的配置
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Set<Map.Entry<Object, Object>> getAllProperty() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.load(new FileReader(Main.class.getClassLoader().getResource("mybatis-genertor-conf.properties").getPath()));
        Set<Map.Entry<Object, Object>> entries = prop.entrySet();
        return entries;
    }

    /**
     * 生成数据库导出文件
     * @throws Exception
     */
    public static void generator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定逆向工程配置文件
        File configFile = new File("E:\\projects\\longmao-work\\project\\MybatisGenertor\\SpringBootLearn\\src\\main\\resources\\mybatis-genertor.xml");

        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println("生成完成");
    }

    /**
     * 获取数据库表信息
     * @throws Exception
     */

    @Test
    public void test00() throws Exception{
        File f = new File("src/main/resources/mybatis-genertor.xml");
        String s = FileUtils.readFileToString(f);
        System.out.println(s);
    }

    @Test
    public void test01() throws IOException {
        String driverClass = getProperty("driverClass");
        System.out.println(driverClass);

    }

    @Test
    public void test03() throws Exception{
        config();
    }

    @Test
    public void test02() throws Exception{
        Properties prop = new Properties();
        prop.load(new FileReader(Main.class.getClassLoader().getResource("mybatis-genertor-conf.properties").getPath()));
        Set<Map.Entry<Object, Object>> entries = prop.entrySet();
    }

    @Test
    public void test04() throws Exception{
        deleteFiles();
    }
}