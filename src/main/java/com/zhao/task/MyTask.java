package com.zhao.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTask implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Date date=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String da=simpleDateFormat.format(date);
		System.out.println("每分钟执行一次:"+da);
	}

}
