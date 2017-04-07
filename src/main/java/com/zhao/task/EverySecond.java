package com.zhao.task;

import org.quartz.JobExecutionContext;

public class EverySecond extends AbsJob {

	@Override
	protected void process(JobExecutionContext context) {
		// TODO Auto-generated method stub
		System.out.println("每5s执行一次");
	}

}
