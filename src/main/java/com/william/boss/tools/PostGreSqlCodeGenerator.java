package com.william.boss.tools;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.william.boss.orm.model.AbstractModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mybatis-plus代码生成器(用于生成entity) 3.4.1版本
 * @author john
 */
public class PostGreSqlCodeGenerator {

	public static void execute(String[] tables, String outputDir, String basePackage, String author) {

        //给生成器添加全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setFileOverride(true)
				.setActiveRecord(true)
				.setEnableCache(false)
				.setBaseResultMap(true)
				.setBaseColumnList(false)
				.setIdType(IdType.AUTO)
				.setAuthor(author)
				.setSwagger2(true)
				.setOutputDir(outputDir)
				.setOpen(false).setFileOverride(true);

        //给生成器添加数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.POSTGRE_SQL)
				.setDriverName("org.postgresql.Driver").setUsername("postgres").setPassword("admin")
				.setUrl("jdbc:postgresql://1.15.132.121:7003/summer_boss");

		//给生成器添加策略配置
		List<TableFill> tableFillList = new ArrayList<>();
		tableFillList.add(new TableFill("creator_id", FieldFill.INSERT));
		tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
		tableFillList.add(new TableFill("updater_id", FieldFill.INSERT_UPDATE));
		tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));

		StrategyConfig strategy = new StrategyConfig();
		strategy.setTablePrefix("summer").setNaming(NamingStrategy.underline_to_camel).setEntityLombokModel(true).setChainModel(true)
				.setSuperEntityClass(AbstractModel.class).setEntityColumnConstant(true)
				.setSuperEntityColumns("create_time", "update_time", "creator_id", "updater_id", "version", "is_del")
				.setInclude(tables).setTableFillList(tableFillList).setRestControllerStyle(true)
				.setVersionFieldName("version").setLogicDeleteFieldName("is_del")
				.setSuperServiceClass(IService.class).setSuperServiceImplClass(ServiceImpl.class);

		//给生成器添加包配置
		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(basePackage).setEntity("orm.model")
				.setMapper("orm.dao").setXml("orm.dao.mapping")
				.setService("service").setServiceImpl("service.impl").setController("controller");

		//给生成器添加参数注入配置
		InjectionConfig ic = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<>(10);
				map.put("abc","this is a updateTest");
				this.setMap(map);
			}
		};
		//创建代码生成器
		AutoGenerator mpg = new AutoGenerator();
		mpg.setGlobalConfig(gc).setDataSource(dsc).setStrategy(strategy).setPackageInfo(pc).setCfg(ic).execute();
		// 打印注入设置
		System.err.println(mpg.getCfg().getMap().get("abc"));
	}

	public static void main(String[] args) {
		String[] tables = new String[]{
				"summer_sys_data_auth",
				"summer_sys_department",
				"summer_sys_dict",
				"summer_sys_menu",
				"summer_sys_operation_log",
				"summer_platform_tenant_datasource",
				"summer_sys_role",
				"summer_sys_role_menu",
				"summer_sys_user_department",
				"summer_sys_user_role",
				"summer_platform_tenant",
				"summer_platform_db_resource",
				"summer_platform_user"
				 };
		String outputDir = "E:\\springboot\\summer_boss\\src\\main\\java";
		String basePackage = "com.william.boss";
		execute(tables,outputDir,basePackage, "john.wang");
	}
}