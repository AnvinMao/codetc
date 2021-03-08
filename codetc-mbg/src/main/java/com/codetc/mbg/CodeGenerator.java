package com.codetc.mbg;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis-Plus-Generator 代码生成器
 * 配置参考 https://mp.baomidou.com/guide/generator.html
 */
public class CodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/codetc_db?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("12345678");
        generator.setDataSource(dsc);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/codetc-mbg/src/main/java");
        gc.setFileOverride(false);
        gc.setOpen(false);
        generator.setGlobalConfig(gc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.codetc.mbg");
        generator.setPackageInfo(pc);

        // 自定义配置
        String xmlTpl = "/templates/mapper.xml.ftl";
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(xmlTpl) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/codetc-mbg/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });


        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);

        // 配置模板
        TemplateConfig tc = new TemplateConfig();
        tc.setController(null);
        tc.setService(null);
        tc.setServiceImpl(null);
        tc.setXml(null);
        generator.setTemplate(tc);

        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);
        sc.setControllerMappingHyphenStyle(true);
        generator.setStrategy(sc);

        // 模板引擎
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }
}
