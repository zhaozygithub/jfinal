package com.zhao.Jfinal3;

import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;

public class GeneratorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Prop p = PropKit.use("db.config");
		DruidPlugin dSource=new DruidPlugin(p.get("jfinal_url"), p.get("jfinal_user"), p.get("jfinal_pwd"));
		dSource.start();
		DataSource dataSource=dSource.getDataSource();
		
		// base model 所使用的包名
		String baseModelPackageName = "com.zhao.model.base";
		// base model 文件保存路径
		String baseModelOutputDir = PathKit.getWebRootPath() + "/../src/com/zhao/model/base";
		
		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.zhao.model";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";
		
		// 创建生成器
		Generator gernerator = new Generator(dataSource, baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		// 设置数据库方言
		gernerator.setDialect(new MysqlDialect());
		// 添加不需要生成的表名
//				gernerator.addExcludedTable("adv");
		// 设置是否在 Model 中生成 dao 对象
		gernerator.setGenerateDaoInModel(true);
		// 设置是否生成字典文件
		gernerator.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
//				gernerator.setRemovedTableNamePrefixes("t_");
		// 生成
		gernerator.generate();
	}

}
