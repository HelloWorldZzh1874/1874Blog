package com.zzh.generator;

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
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Author zzh
 * @Date 2021/7/19 21:51
 * @Version 0.1
 * @Description Mybatis-Plus代码生成器
 **/
public class CodeGenerator {
    public static void main(String[] args) {
        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath);
        gc.setAuthor("zzh");
        //生成后是否打开资源管理器
        gc.setOpen(false);
        //重新生成时文件是否覆盖
        gc.setFileOverride(true);
        //去掉Service接口的首字母I
        gc.setServiceName("%sService");
        //主键策略
        gc.setIdType(IdType.ID_WORKER_STR);
        //定义生成的实体类中日期类型
        gc.setDateType(DateType.ONLY_DATE);
        //开启Swagger2模式
        gc.setSwagger2(true);

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/1874blog?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("mysqlzzh");
        dsc.setPassword("ybzzh20000129");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        /**
         * 包配置
         * 简单来讲 就是写绝对路径
         */
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.zzh");
        //指定生成文件导入的包
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        pc.setMapper("mapper");
        pc.setXml(null);
        //指定生成文件的绝对路径
        Map<String, String> pathInfo  = new HashMap<>();
        String parentPath = "\\src\\main\\java\\com\\zzh\\";
        String conStr ="\\blog-";

        String entityPath = projectPath.concat(conStr).concat("system").concat(parentPath).concat("entity");
        String mapperPath = projectPath.concat(conStr).concat("system").concat(parentPath).concat("mapper");
        String xmPath = projectPath.concat(conStr).concat("system").concat("\\src\\main\\resources\\mappers");
        String servicePath = projectPath.concat(conStr).concat("system").concat(parentPath).concat("service");
        String serviceImplPath = projectPath.concat(conStr).concat("system").concat(parentPath).concat("service\\impl");
        String controllerPath = projectPath.concat(conStr).concat("web").concat(parentPath).concat("controller");

        pathInfo.put("entity_path",entityPath);
        pathInfo.put("mapper_path",mapperPath);
        pathInfo.put("xml_path",xmPath);
        pathInfo.put("service_path",servicePath);
        pathInfo.put("service_impl_path",serviceImplPath);
        pathInfo.put("controller_path",controllerPath);
        pc.setPathInfo(pathInfo);
        mpg.setPackageInfo(pc);

        // 4.4 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        // String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath.concat(conStr).concat("system").concat("\\src\\main\\resources\\mappers\\") + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        //继承的Controller
        strategy.setSuperControllerClass("com.zzh.common.base.BaseController");
        //对那一张表生成代码
        strategy.setInclude(scanner("表名,多个英文逗号分割").split(","));
        //数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //生成实体时去掉表前缀
        strategy.setTablePrefix("tb_");

        //数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityLombokModel(true);

        //restful api风格控制器
        strategy.setRestControllerStyle(true);
        //url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(strategy);
        // 6、执行
        mpg.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
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
}
