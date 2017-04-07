package com.zhao.Controller;

import java.util.Date;

import com.jfinal.core.Controller;
import com.jfinal.i18n.I18n;
import com.jfinal.i18n.Res;

public class I18nController extends Controller {
	public void index() {
		render("/user/i18n.html");
	}
	
	public void toen() {
		// 通过locale参数en_US得到对应的Res对象
		Res resEn = I18n.use("en_US");
		// 直接获取数据
		String msgEn = resEn.get("msg");
		// 获取数据并使用参数格式化
		String msgEnFormat = resEn.format("msg", "james", new Date());
		setAttr("msg", msgEnFormat);
		render("/user/i18n.html");
	}
	public void tozh() {
		// 通过locale参数zh_CN得到对应的Res对象
		Res resZh = I18n.use("zh_CN");
		// 直接获取数据
		String msgZh = resZh.get("msg");
		// 获取数据并使用参数格式化
		String msgZhFormat = resZh.format("msg", "詹波", new Date());
		setAttr("msg", msgZhFormat);
		render("/user/i18n.html");
	}
}
