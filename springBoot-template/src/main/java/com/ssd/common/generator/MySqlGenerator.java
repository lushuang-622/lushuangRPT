package com.ssd.common.generator;

import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * TODO
 *
 * @author lus
 * @Date 2021/1/8 0008 14
 */
public class MySqlGenerator {
    public static void main(String[] args) {
        // 代码生成器
        String jdbcUrl = "jdbcURL";
        String username = "root";
        String password = "password";
        CoderGeneratorPathVO coderGeneratorPathVO = new CoderGeneratorPathVO();
        coderGeneratorPathVO.setControllerOutputPath("controller绝对路径");
        coderGeneratorPathVO.setDaoOutputPath("dao绝对路径");
        coderGeneratorPathVO.setEntityOutputPath("entity绝对路径");
        coderGeneratorPathVO.setMapperOutputPath("mapper绝对路径");
        coderGeneratorPathVO.setProjectPath("项目绝对路径");
        coderGeneratorPathVO.setServiceOutputPath("service绝对路径");
        coderGeneratorPathVO.setXmlOutputPath("xml绝对路径");


        CoderGenerator mpg = CoderGenerator.builder(coderGeneratorPathVO)
                .dataSourceConfig(jdbcUrl, "com.mysql.jdbc.Driver", username, password)
                .strategyConfig(new CoderGeneratorTableVO("sys_user", ""))
                .globalConfig("author")
                .packageConfig("包名")
                .build();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
