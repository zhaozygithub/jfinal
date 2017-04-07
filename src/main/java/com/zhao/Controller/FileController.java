package com.zhao.Controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.zhao.interceptor.MyValidator;
import com.zhao.model.User;

public class FileController extends Controller {
	public void index() {

		render("/user/ueditor.html");
//		render("/user/upload1.html");
	}

	public void upload() {
		getFile();
//		List<UploadFile> uploadFiles = getFiles();
//		for (UploadFile uploadFile : uploadFiles) {
//			String file=uploadFile.getFileName();
//			System.out.println(file);
//		}

		renderText("上传成功");
	}

	@Before(MyValidator.class)
	public void test() {

		renderText("ok");
	}

	public void test1() {
		String name = getPara("trigger");
		renderText("FileController.test1" + name);
	}

	public void form() {
		User user = getModel(User.class, "");
		System.out.println(user);

		renderText("ok");
	}

}
