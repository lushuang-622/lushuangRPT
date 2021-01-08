package com.ssd.common.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 *  自动生成mybatisplus的相关代码
 *
 *
 * @author lus
 * @Date 2020/12/24 0024 09
 */
public class GeneratorCodeMain {
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) throws InterruptedException {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setFileOverride(true);// 文件是否覆盖
        gc.setActiveRecord(true);
        gc.setIdType(IdType.ASSIGN_UUID); //主键策略 实体类主键ID类型
        gc.setDateType(DateType.ONLY_DATE);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setOpen(false);// 生成完成后不弹出文件框
        gc.setSwagger2(true); // 是否开启swagger
        gc.setAuthor(scanner("作者"));

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("I%sDao");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://192.168.198.100:3306/template?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dsc.setUsername("root");
        dsc.setPassword("lus123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        String moduleName = scanner("模块包名");
        pc.setModuleName(moduleName);
        String packageParent = scanner("前置包名");
        pc.setParent(packageParent);
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            //自定义属性注入:abc
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            /**
             * entity2.java.ftl 自定义属性注入abc=${cfg.abc}
             * entity2.java.vm 自定义属性注入abc=$!{cfg.abc}
             */
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                map.put("packageParent", packageParent);
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        // 如果模板引擎是 freemarker
        // String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/"+moduleName+"/"+ tableInfo.getEntityName() + "Mapper"
                        + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        templateConfig.setService("templates/service2.java");
        templateConfig.setServiceImpl("templates/serviceImpl2.java");
        templateConfig.setController("templates/controller2.java");

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 此处可以修改为您的表前缀
        strategy.setTablePrefix(new String[] { "ssd_" });
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 字段生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 设置自定义实体根类型 默认com.baomidou.mybatisplus.extension.activerecord.Model<T>
        // strategy.setSuperEntityClass("com.gf.ps.model.BaseEntity");
        // 设置继承的父类字段
        // strategy.setSuperEntityColumns("id","modifiedBy","modifiedOn","createdBy","createdOn");
        // 设置自定义mapper文件集成文件
        // strategy.setSuperMapperClass("com.gf.ps.util.IMapper");
        // 设置是否为json格式返回controller 默认 true
        strategy.setRestControllerStyle(true);
        // 设置默认继承的controller
        // strategy.setSuperControllerClass("com.gf.ps.controller.BaseController");
        // 需要生成的表
        // strategy.setInclude(new String[] { "tb_employee" });
        // 采用手动输入表名方式
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // 排除生成的表
        // strategy.setExclude(new String[]{"test"});
        strategy.setEntityLombokModel(true);

        // 如果模板引擎是 freemarker
        // mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setStrategy(strategy);


        // 执行生成
        mpg.execute();
    }
}
