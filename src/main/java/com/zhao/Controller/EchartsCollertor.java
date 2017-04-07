package com.zhao.Controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class EchartsCollertor extends Controller {

	public void index() {
		List<Record> list=Db.find("SELECT COUNT(id) value,left(create_time,10) name FROM echarts GROUP BY left(create_time,10)");
		setAttr("data", list);
		render("echarts.html");
//		render("BaiduEcharts.html");
	}

	

}
