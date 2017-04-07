package com.zhao.interceptor;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.zhao.model.User;

public class MyValidator extends Validator {

	@Override
	protected void handleError(Controller paramController) {
		// TODO Auto-generated method stub
//		paramController.keepPara("name");
		paramController.keepModel(User.class);
		paramController.render("/user/upload.html");
		
	}

	@Override
	protected void validate(Controller paramController) {
		// TODO Auto-generated method stub
		validateInteger("age", "msg", "只能输入整数");
	}

}
