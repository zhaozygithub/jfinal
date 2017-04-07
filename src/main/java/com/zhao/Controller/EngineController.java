package com.zhao.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.json.Json;
import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.zhao.model.User;

public class EngineController extends Controller {

	public void index() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "tom");
		map.put("age", 12);
		map.put("address", "郑州");

		setAttr("map", map);

		String[] arrs = { "a1", "a2", "a3" };

		setAttr("arrs", arrs);

		setAttr("key", "value");

		List<String> list = new ArrayList<>();
		list.add("java");
		list.add("js");
		list.add("eova");

		setAttr("list", list);
		
		setSessionAttr("sess",list);
		
		render("/Template/1.html");
//		render("/user/freeMarker.html");
		 render("user/engine.html");
		// redirect("/up/test1",true);
	}

	@Before(CacheInterceptor.class)
	@CacheName("sysCache")
	public void find() {
		List<Record> list2 = Db.find(Db.getSqlPara("zhao.findUserById2", JMap.create("id", 2)));

		// 配置ehcache
		List<Record> ehcache = CacheKit.get("sysCache", "key");
		if (ehcache == null) {
			List<Record> list = Db.find(Db.getSql("zhao.findUserById", new User()), 1);
			CacheKit.put("sysCache", "key", list);
		}

		// 获取redis
		Cache cache = Redis.use("bbs");
		// 插入一条键值对
		cache.set("redis", list2.get(0).get("user_name"));

		setAttr("user", list2.get(0));

		render("/user/engine.html");
	}

	public void redis() {
		// 直接从redis通过key取值。所以需要先运行一次this.find()
		setAttr("redisKey", Redis.use("bbs").get("redis"));

		// 直接从ehcache里面取值
		List<Record> ehcache = CacheKit.get("sysCache", "key");
		System.out.println(ehcache);
		render("/user/engine.html");
	}

	public void table() {
		TableMapping tableMapping=TableMapping.me();
		Table table=tableMapping.getTable(User.class);
		Map<String, Class<?>> map=table.getColumnTypeMap();
		Set<String> keyset =  map.keySet();
		for (String string : keyset) {
			System.out.println(string);
		}
		renderText("table");
	}
	/**
	 * 输出表格模板
	 * */
	public void view() {
		String tableName="users_role";
		String queryField="id,role_name,username,password,password_salt";
		String[] columns_={"ID","角色名","用户名","密码","盐密码"};
	

		List<Object> list3=Db.query("SELECT "+queryField+" FROM "+tableName);

		setAttr("columns", columns_);
		setAttr("data", list3);
		
		render("/Template/1.html");
		
//		renderText("view");
	}

}
