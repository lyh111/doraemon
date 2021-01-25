package com.autosite.codegen.utils;

import com.autosite.codegen.common.base.BaseController;
import com.autosite.codegen.common.base.BaseEntity;
import com.autosite.codegen.common.entity.GenConfig;
import com.autosite.common.io.PropertiesUtils;
import com.autosite.common.lang.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenUtils {

    public static void main(String[] args) throws IOException {
        GenConfig conf = GenConfig.builder()
                .projectName("codegen")
                .author("lyh")
                .pkParent("com.autosite.codegen")
                .pkModuleName("gen1")
                .tbName("as_gen_table")
                .isExPrefix(true)
                .build();
        autoGenCode(conf);
    }


    public static void autoGenCode(GenConfig conf) throws IOException {
        AutoGenerator mpg = new AutoGenerator();
        // beetl模板配置
        BeetlTemplateEngine tmp = new BeetlTemplateEngine();
        mpg.setTemplateEngine(tmp);
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir"); // 项目根目录
        String projectName = conf.getProjectName(); // 项目名
        String author = conf.getAuthor(); // 作者
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
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        PropertiesUtils pro = PropertiesUtils.getInstance();
        String url = conf.getDsUrl();
        String driverName = conf.getDsDriverName();
        String uname = conf.getDsUname();
        String pass = conf.getDsPass();
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
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        String parent = conf.getPkParent();
        String pkModuleName = conf.getPkModuleName();
        pc.setParent(parent)
                .setModuleName(pkModuleName)
                .setController("web")
                .setMapper("dao");
        mpg.setPackageInfo(pc);

        // 自定义xml文件的路径
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        String templatePath = "/templates/mapper.xml.btl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return new StringBuffer(projectPath).append("/").append(projectName)
                        .append("/src/main/resources/mappings/")
                        .append(pc.getModuleName())
                        .append("/").append(tableInfo.getEntityName())
                        .append("Dao").append(StringPool.DOT_XML).toString();
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 配置模板（使其默认的xml配置不生效）
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        String tbName = conf.getTbName(); // 表名
        String tablePrefix = null;
        boolean isExPrefix = conf.getIsExPrefix();
        if (isExPrefix) {
            tablePrefix = tbName.split("_")[0] + "_";
        }
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setSuperControllerClass(BaseController.class)
                .setSuperEntityClass(BaseEntity.class)
                .setEntityLombokModel(true) //使用lombok
                .setInclude(tbName)  // 逆向工程使用的表   如果要生成多个,这里可以传入String[]
                .setTablePrefix(tablePrefix);
        mpg.setStrategy(strategy);
        // 执行
        mpg.execute();
    }
}
