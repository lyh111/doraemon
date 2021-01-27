package com.autosite.codegen.utils;

import com.autosite.codegen.common.base.BaseController;
import com.autosite.codegen.common.base.BaseEntity;
import com.autosite.codegen.common.base.BaseService;
import com.autosite.codegen.common.entity.GenConfig;
import com.autosite.common.collect.MapUtils;
import com.autosite.common.io.PropertiesUtils;
import com.autosite.common.lang.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.TemplateEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class GenUtils {

    public static void main(String[] args) throws IOException {
        Map<String,Map<String,Object>> fields = MapUtils.newHashMap();
        Map<String,Object> properties = MapUtils.newHashMap();
        properties.put("keyword","like");
        fields.put("table_name",properties);
        GenConfig conf = GenConfig.builder()
                .projectName("codegen")
                .author("lyh")
                .pkParent("com.autosite.codegen")
                .pkModuleName("gen1")
                .tbName("as_gen_table")
                .isExPrefix(true)
                .fields(fields)
                .build();
        GenUtils.builder(conf).build();
    }

    public static Builder builder(GenConfig genConfig){
        return new Builder(genConfig);
    }

    public static class Builder{

        private GlobalConfig globalConfig; // 全局配置
        private DataSourceConfig dataSourceConfig; // 数据源配置
        private PackageConfig packageConfig; // 包配置
        private InjectionConfig injectionConfig; // 自定义配置（xml路径配置、entity配置）
        private TemplateConfig templateConfig; // 模板配置
        private StrategyConfig strategyConfig; // 策略配置
        private GenConfig genConfig; // 代码生成所需个性化配置
        private AbstractTemplateEngine templateEngine; // 模板（默认beetl）

        public Builder(GenConfig genConfig){
            this.genConfig = genConfig;
        }

        public void build(){
            AutoGenerator mpg = new AutoGenerator();
            if(genConfig == null){
                throw new RuntimeException("自动构建代码时genConfig未配置");
            }
            if(templateEngine == null){
                buildDefaultTemplateEngine();
            }
            if(globalConfig == null){
                buildDefaultGlobalConfig();
            }
            if(dataSourceConfig == null){
                buildDefaultDataSourceConfig();
            }
            if(packageConfig == null){
                buildDefaultPackageConfig();
            }
            if(injectionConfig == null){
                buildDefaultInjectionConfig();
            }
            if(templateConfig == null){
                buildDefaultTemplateConfig();
            }
            if(strategyConfig == null){
                buildDefaultStrategyConfig();
            }
            mpg.setTemplateEngine(templateEngine);
            mpg.setGlobalConfig(globalConfig);
            mpg.setDataSource(dataSourceConfig);
            mpg.setPackageInfo(packageConfig);
            mpg.setCfg(injectionConfig);
            mpg.setTemplate(templateConfig);
            mpg.setStrategy(strategyConfig);
            mpg.execute();
        }

        public Builder genConfig(GenConfig genConfig){
            this.genConfig = genConfig;
            return this;
        }

        public Builder templateEngine(AbstractTemplateEngine templateEngine){
            this.templateEngine = templateEngine;
            return this;
        }

        public Builder globalConfig(GlobalConfig globalConfig){
            this.globalConfig = globalConfig;
            return this;
        }

        public Builder dataSourceConfig(DataSourceConfig dataSourceConfig){
            this.dataSourceConfig = dataSourceConfig;
            return this;
        }

        public Builder packageConfig(PackageConfig packageConfig){
            this.packageConfig = packageConfig;
            return this;
        }

        public Builder injectionConfig(InjectionConfig injectionConfig){
            this.injectionConfig = injectionConfig;
            return this;
        }

        public Builder templateConfig(TemplateConfig templateConfig){
            this.templateConfig = templateConfig;
            return this;
        }

        public Builder strategyConfig(StrategyConfig strategyConfig){
            this.strategyConfig = strategyConfig;
            return this;
        }

        // 默认模板引擎配置（beetl）
        public Builder buildDefaultTemplateEngine(){
            BeetlTemplateEngine tmp = new BeetlTemplateEngine();
            this.templateEngine = tmp;
            return this;
        }

        // 默认全局配置
        public Builder buildDefaultGlobalConfig(){
            GlobalConfig gc = new GlobalConfig();
            String projectPath = genConfig.getProjectPath();
            if(StringUtils.isBlank(projectPath)){
                projectPath = System.getProperty("user.dir"); // 项目根目录
            }
            String projectName = genConfig.getProjectName(); // 项目名
            String author = genConfig.getAuthor(); // 作者
            String outPath = new StringBuffer(projectPath).append("/")
                    .append(projectName).append("/src/main/java").toString();
            gc.setOutputDir(outPath)  //生成路径(一般都是生成在此项目的src/main/java下面)
                    .setAuthor(author) //设置作者
                    .setOpen(false)
                    .setFileOverride(true) //第二次生成会把第一次生成的覆盖掉
                    .setServiceImplName("%sService") // service层以service结尾
                    .setMapperName("%sDao") // dao层以Dao结尾
                    .setControllerName("%sController") // controller层以Controller结尾
                    .setBaseResultMap(true); //生成resultMap
            this.globalConfig = gc;
            return this;
        }

        // 默认数据源配置
        public Builder buildDefaultDataSourceConfig(){
            DataSourceConfig dsc = new DataSourceConfig();
            PropertiesUtils pro = PropertiesUtils.getInstance();
            String url = genConfig.getDsUrl(); // 数据源路径
            String driverName = genConfig.getDsDriverName(); // 数据源驱动
            String uname = genConfig.getDsUname(); // 数据源用户名
            String pass = genConfig.getDsPass(); // 数据源密码
            if (StringUtils.isBlank(url)) {
                url = pro.getProperty("spring.datasource.url");
                driverName = pro.getProperty("spring.datasource.driver");
                uname = pro.getProperty("spring.datasource.username");
                pass = pro.getProperty("spring.datasource.password");
            }
            dsc.setUrl(url)
                    .setDriverName(driverName)
                    .setUsername(uname)
                    .setPassword(pass);
            this.dataSourceConfig = dsc;
            return this;
        }

        // 默认包配置
        public Builder buildDefaultPackageConfig(){
            PackageConfig pc = new PackageConfig();
            String parent = genConfig.getPkParent(); // 所属包名
            String pkModuleName = genConfig.getPkModuleName(); // 模块名
            pc.setParent(parent)
                    .setModuleName(pkModuleName)
                    .setController("web")
                    .setMapper("dao");
            this.packageConfig = pc;
            return this;
        }

        // 默认自定义配置（xml、entity）
        public Builder buildDefaultInjectionConfig(){
            String projectPath = System.getProperty("user.dir"); // 项目根目录
            String projectName = genConfig.getProjectName(); // 项目名
            String pkModuleName = genConfig.getPkModuleName(); // 模块名
            Map<String,Map<String,Object>> fields = genConfig.getFields(); // 个性化的属性信息
            // 自定义xml文件的路径
            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {
                    // to do nothing
                }

                /**
                 * 在entity上加上自定义的注解
                 * @param tableInfo
                 */
                @Override
                public void initTableMap(TableInfo tableInfo) {
                    super.initTableMap(tableInfo);
                    List<TableField> list = tableInfo.getFields();
                    for(TableField field : list){
                        Map<String,Object> _field = MapUtils.getMap(fields,field.getColumnName());
                        String keyword = MapUtils.getString(_field,"keyword");
                        if(StringUtils.isNotBlank(keyword)){
                            Map<String,Object> map = MapUtils.newHashMap();
                            map.put("keyword",keyword); // 查询条件关键字
                            field.setCustomMap(map);
                        }
                    }
                }

            };
            // 自定义xml的输出位置
            String mapperTmpPath = "/templates/mapper.xml.btl";
            List<FileOutConfig> focList = new ArrayList<>();
            focList.add(new FileOutConfig(mapperTmpPath) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名及路径
                    return new StringBuffer(projectPath).append("/").append(projectName)
                            .append("/src/main/resources/mappings/")
                            .append(pkModuleName)
                            .append("/").append(tableInfo.getEntityName())
                            .append("Dao").append(StringPool.DOT_XML).toString();
                }
            });
            cfg.setFileOutConfigList(focList);
            this.injectionConfig = cfg;
            return this;
        }

        // 默认配置模板
        public Builder buildDefaultTemplateConfig(){
            TemplateConfig templateConfig = new TemplateConfig();
            // 使其默认的xml配置不生效
            templateConfig.setXml(null);
            this.templateConfig = templateConfig;
            return this;
        }

        // 默认策略配置
        public Builder buildDefaultStrategyConfig(){
            String tbName = genConfig.getTbName(); // 表名
            String tablePrefix = null;
            boolean isExPrefix = genConfig.getIsExPrefix(); // 是否去掉表前缀(如as_)
            if (isExPrefix) {
                tablePrefix = tbName.split("_")[0] + "_"; // （as_）
            }
            StrategyConfig strategy = new StrategyConfig();
            strategy.setNaming(NamingStrategy.underline_to_camel) // 将列明转为驼峰
                    .setColumnNaming(NamingStrategy.underline_to_camel)
                    .setSuperControllerClass(BaseController.class) // 配置controller的父类
                    .setSuperEntityClass(BaseEntity.class) // 配置entity的父类
                    .setSuperServiceImplClass(BaseService.class) // 配置serviceImpl的父类
                    .setEntityLombokModel(true) //使用lombok
                    .setInclude(tbName)  // 逆向工程使用的表   如果要生成多个,这里可以传入String[]
                    .setTablePrefix(tablePrefix); // 去掉表的前缀作为文件名
            this.strategyConfig = strategy;
            return this;
        }
    }
}
