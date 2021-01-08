package com.ssd.common.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author lus
 * @Date 2021/1/8 0008 11
 */
public class CoderGenerator extends AutoGenerator {
    private CoderGenerator() {}

    public static CoderGeneratorBuilder builder(CoderGeneratorPathVO coderGeneratorPathVO) {
        return new CoderGeneratorBuilder(coderGeneratorPathVO);
    }

    public static class CoderGeneratorBuilder {

        private DataSourceConfig dataSourceConfig;
        private PackageConfig packageConfig;
        private GlobalConfig globalConfig;
        private InjectionConfig injectionConfig;
        private StrategyConfig strategyConfig;
//        private List<FileOutConfig> fileOutConfigList;

        // 定义不可修改的常量
        // 各层包名
        private static final String ENTITY_PATH = "/entity/";
        private static final String MAPPER_PATH = "/mapper/";
        private static final String XML_PATH = "/resources/mapper/";
        private static final String SERVICE_PATH = "/service/";
        private static final String SERVICE_IMPL_PATH = "/service/impl/";
        private static final String CONTROLLER_PATH = "/controller/";
        // 各层模版
        private static final String ENTITY_TEMPLATE = "templates/entity.java.ftl";
        private static final String XML_TEMPLATE = "templates/mapper.xml.ftl";
        private static final String MAPPER_TEMPLATE = "templates/mapper.java.ftl";
        private static final String SERVICE_TEMPLATE = "templates/service.java.ftl";
        private static final String SERVICE_IMPL_TEMPLATE = "templates/serviceImpl.java.ftl";
        private static final String CONTROLLER_TEMPLATE = "templates/controller.java.ftl";

        // 由用户动态设置
        // 各层文件输出到模块, 没有则置空
        private String projectPath;
        /** Entity.java, Mapper.java, Mapper.xml输出模块路径 */
        private String entityOutputPath;
        /** Entity.java, Mapper.java, Mapper.xml输出模块路径 */
        private String mapperOutputPath;
        /** Entity.java, Mapper.java, Mapper.xml输出模块路径 */
        private String daoOutputPath;
        /** mapper.xml输出模块路径(需要注意放置的位置:默认从模块/src/main下开始) */
        private String xmlOutputPath;
        /** IService.java, serviceImpl.java输出模块路径 */
        private String serviceOutputPath;
        /** Controller.java输出模块路径 */
        private String controllerOutputPath;

        private CoderGeneratorBuilder(CoderGeneratorPathVO coderGeneratorPathVO) {
            this.projectPath = coderGeneratorPathVO.getProjectPath();
            this.entityOutputPath = coderGeneratorPathVO.getEntityOutputPath();
            this.mapperOutputPath = coderGeneratorPathVO.getMapperOutputPath();
            this.daoOutputPath = coderGeneratorPathVO.getDaoOutputPath();
            this.xmlOutputPath = coderGeneratorPathVO.getXmlOutputPath();
            this.serviceOutputPath = coderGeneratorPathVO.getServiceOutputPath();
            this.controllerOutputPath = coderGeneratorPathVO.getControllerOutputPath();
            this.injectionConfig = injectionConfig();
//            this.fileOutConfigList = this.fileOutConfigList();
        }

        public static CoderGeneratorBuilder builder(CoderGeneratorPathVO coderGeneratorPathVO) {
            return new CoderGeneratorBuilder(coderGeneratorPathVO);
        }

        public CoderGeneratorBuilder dataSourceConfig(String jdbcUrl, String driverName, String username, String password) {
            dataSourceConfig = new DataSourceConfig();
            dataSourceConfig.setUrl(jdbcUrl);
            dataSourceConfig.setDriverName(driverName);
            dataSourceConfig.setUsername(username);
            dataSourceConfig.setPassword(password);
            return this;
        }

        public CoderGeneratorBuilder packageConfig(String parent) {
            String entity = ENTITY_PATH;
            String mapper = MAPPER_PATH;
            String xml = XML_PATH;
            String service = SERVICE_PATH;
            String serviceImpl = SERVICE_IMPL_PATH;
            String controller = CONTROLLER_PATH;

            packageConfig = new PackageConfig()
                    // 父包名
                    .setParent(parent.replace('/', '.').substring(1))
                    .setEntity(entity.replace('/', '.').substring(1, entity.length()-1))
                    .setMapper(mapper.replace('/', '.').substring(1, mapper.length()-1))
                    .setXml(xml.replace('/', '.').substring(1, xml.length()-1))
                    .setService(service.replace('/', '.').substring(1, service.length()-1))
                    .setServiceImpl(serviceImpl.replace('/', '.').substring(1, serviceImpl.length()-1))
                    .setController(controller.replace('/', '.').substring(1, controller.length()-1));
            return this;
        }

        /**
         * 策略配置
         */
        public CoderGeneratorBuilder strategyConfig(CoderGeneratorTableVO coderGeneratorTableVO) {
            this.strategyConfig = new StrategyConfig()
                    // 表名生成策略：下划线连转驼峰
                    .setNaming(NamingStrategy.underline_to_camel)
                    // 表字段生成策略：下划线连转驼峰
                    .setColumnNaming(NamingStrategy.underline_to_camel)
                    // 需要生成的表
                    .setInclude(coderGeneratorTableVO.getTableName())
                    // 生成controller
                    .setRestControllerStyle(true)
                    // 去除表前缀
                    .setTablePrefix(coderGeneratorTableVO.getEntityPrefix())
                    // controller映射地址：驼峰转连字符
                    .setControllerMappingHyphenStyle(true)
                    // 是否启用builder 模式
                    .setEntityBuilderModel(true)
                    // 是否为lombok模型; 需要lombok依赖
                    .setEntityLombokModel(true)
                    // 生成实体类字段注解
                    .setEntityTableFieldAnnotationEnable(true);
            return this;
        }

        /**
         * 全局配置
         */
        public CoderGeneratorBuilder globalConfig(String author) {
            this.globalConfig = new GlobalConfig()
                    // 打开文件
                    .setOpen(false)
                    // 文件覆盖
                    .setFileOverride(true)
                    // 开启activeRecord模式
                    .setActiveRecord(true)
                    // XML ResultMap: mapper.xml生成查询映射结果
                    .setBaseResultMap(true)
                    // XML ColumnList: mapper.xml生成查询结果列
                    .setBaseColumnList(true)
                    // swagger注解; 须添加swagger依赖
                    .setSwagger2(true)
                    // 作者
                    .setAuthor(author);
            return this;
        }

        public CoderGenerator build() {
            CoderGenerator coderGenerator = new CoderGenerator();
            // 置空模版
            coderGenerator.setTemplate(templateConfig());
            if (dataSourceConfig != null) {
                coderGenerator.setDataSource(dataSourceConfig);
            }
            if (packageConfig != null) {
                coderGenerator.setPackageInfo(packageConfig);
            }
            if (globalConfig != null) {
                coderGenerator.setGlobalConfig(globalConfig);
            }
            if (strategyConfig != null) {
                coderGenerator.setStrategy(strategyConfig);
            }
            coderGenerator.setCfg(injectionConfig);
            return coderGenerator;
        }

        /**
         * 模板配置
         */
        private TemplateConfig templateConfig() {
            return new TemplateConfig()
                    // 置空后方便使用自定义输出位置
                    .setEntity(null)
                    .setXml(null)
                    .setMapper(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setController(null);
        }

        /**
         * 自定义配置
         */
        private  InjectionConfig injectionConfig() {
            return new InjectionConfig() {
                @Override
                public void initMap() {
                    // 注入配置
                }
            }
                    // 判断是否创建文件
                    .setFileCreate(new IFileCreate() {
                        @Override
                        public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {

                            // 检查文件目录，不存在自动递归创建
                            checkDir(filePath);

                            // 指定需要覆盖的文件
                            // 文件结尾名字参照 全局配置 中对各层文件的命名,未修改为默认值
                            if (isExists(filePath) && (!filePath.endsWith("Mapper.xml") && !filePath.endsWith("Dao.java") && !filePath.endsWith("Mapper.java"))) {
                                return false;
                            }

                            return true;
                        }
                    })
                    // 自定义输出文件
                    .setFileOutConfigList(fileOutConfigList());
        }

        /**
         * 自定义输出文件配置
         */
        private List<FileOutConfig> fileOutConfigList() {
            List<FileOutConfig> list = new ArrayList<>();

            // 实体类文件输出
            list.add(new FileOutConfig(ENTITY_TEMPLATE) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return entityOutputPath + tableInfo.getEntityName() + StringPool.DOT_JAVA;
                }
            });
            // mapper xml文件输出
            list.add(new FileOutConfig(XML_TEMPLATE) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return xmlOutputPath + tableInfo.getMapperName() + StringPool.DOT_XML;
                }
            });
            // mapper文件输出
            list.add(new FileOutConfig(MAPPER_TEMPLATE) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return mapperOutputPath + tableInfo.getMapperName() + StringPool.DOT_JAVA;
                }
            });
            // service文件输出
            list.add(new FileOutConfig(SERVICE_TEMPLATE) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return serviceOutputPath + tableInfo.getServiceName() + StringPool.DOT_JAVA;
                }
            });
            // service impl文件输出
            list.add(new FileOutConfig(SERVICE_IMPL_TEMPLATE) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return serviceOutputPath + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
                }
            });
            // controller文件输出
            list.add(new FileOutConfig(CONTROLLER_TEMPLATE) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return controllerOutputPath + tableInfo.getControllerName() + StringPool.DOT_JAVA;
                }
            });

            return list;
        }

        /**
         * 判断文件是否存在
         * @param path 路径
         * @return
         */
        private static boolean isExists(String path) {
            File file = new File(path);
            return file.exists();
        }
    }
}
