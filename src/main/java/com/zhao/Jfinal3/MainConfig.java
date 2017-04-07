package com.zhao.Jfinal3;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.i18n.I18nInterceptor;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.zhao.Controller.EchartsCollertor;
import com.zhao.Controller.EngineController;
import com.zhao.Controller.FileController;
import com.zhao.Controller.I18nController;
import com.zhao.Controller.ShiroController;
import com.zhao.Controller.TaskController;
import com.zhao.model._MappingKit;
import com.zhao.plugin.QuartzPlugin;
import com.zhao.task.MyTask;

public class MainConfig extends JFinalConfig{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFinal.start("src/main/webapp", 80, "/",5);
	}

	
	@Override
	public void configRoute(Routes routes) {
		//页面路径配置
//		routes.setBaseViewPath("/WEB-INF/view");
		routes.add("/", EngineController.class);
		routes.add("/task", TaskController.class);
		routes.add("/up",FileController.class);
		routes.add("/i18n",I18nController.class);
		routes.add("/shiro",ShiroController.class);
		routes.add("/echarts",EchartsCollertor.class);
		
	}
	
	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		me.setDevMode(true);
//		me.setViewType(ViewType.FREE_MARKER);
		PropKit.use("db.config");
//		me.setViewType(ViewType.FREE_MARKER);
		me.setBaseUploadPath("e:\\汤姆猫\\a");
		me.setI18nDefaultBaseName("i18n");
	}

	@Override
	public void configEngine(Engine engine) {
		// TODO Auto-generated method stub
//		engine.addSharedFunction("\\Template\\layout.html");
		engine.addSharedFunction("\\Template\\Main.html");
		engine.addSharedFunction("\\Template\\js.html");
		engine.addSharedFunction("\\Template\\core.html");
		/*通过继承 Directive 并实现 exec方法，三行代码即实现一个#now 指令，可以
		向模板中输出当前日期，在使用前只需通过 me.addDirective(“now”, new Now()) 添加到模板引
		擎中即可。以下是在模板中使用该指令的例子：*/
//		engine.addDirective(directiveName, directive);
		
		
		
	}

	@Override
	public void configHandler(Handlers handlers) {
		// TODO Auto-generated method stub
		
		
		
		// 添加DruidHandler
		DruidStatViewHandler dvh = new DruidStatViewHandler("/druid");
		handlers.add(dvh);
	}

	@Override
	public void configInterceptor(Interceptors interceptors) {
		// TODO Auto-generated method stub
		
		interceptors.addGlobalActionInterceptor(new SessionInViewInterceptor(true));
		
		//配置国际化文件
		interceptors.add(new I18nInterceptor());
		
	}

	@Override
	public void configPlugin(Plugins plugins) {
		// TODO Auto-generated method stub
		ActiveRecordPlugin arp = addDataSource(plugins,"jfinal", JdbcUtils.MYSQL);
		arp.setBaseSqlTemplatePath(PathKit.getRootClassPath());
		arp.addSqlTemplate("demo.sql");
		plugins.add(arp);
		
		
        
		//第一种方式，每一分钟执行一次
//		Cron4jPlugin cp=new Cron4jPlugin();
//		cp.addTask("* * * * *",new MyTask());
//		plugins.add(cp);
//		
//
//		//第二种方式，从配置文件中读取
//		Cron4jPlugin cron4jPlugin=new Cron4jPlugin("cron4j.config");
//		plugins.add(cron4jPlugin);
		//添加quartz插件
		QuartzPlugin quartz = new QuartzPlugin();
		plugins.add(quartz);
		
		
		RedisPlugin redisPlugin=new RedisPlugin("bbs", "192.168.0.115");
		plugins.add(redisPlugin);
		
		
		EhCachePlugin ehCachePlugin=new EhCachePlugin();
		plugins.add(ehCachePlugin);
		
		
	}

	
	
	private ActiveRecordPlugin addDataSource(Plugins plugins, String datasource, String dbType) {
		// 添加数据源
		String url, user, pwd;
		url = PropKit.get(datasource + "_url");
		user = PropKit.get(datasource + "_user");
		pwd = PropKit.get(datasource + "_pwd");

		WallFilter wall = new WallFilter();
		wall.setDbType(dbType);

		DruidPlugin dp = new DruidPlugin(url, user, pwd);
		
		dp.addFilter(new StatFilter());
		dp.addFilter(wall);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(datasource, dp);
		// 方言
		arp.setDialect(new MysqlDialect());
		// 事务级别
		arp.setTransactionLevel(4);
		// 统一全部默认小写
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		// 是否显示SQL
		arp.setShowSql(true);
		System.out.println("load data source:" + url + "/" + user);

		
		_MappingKit.mapping(arp);
		
		
		plugins.add(dp).add(arp);

		return arp;
	}
	
	

}
