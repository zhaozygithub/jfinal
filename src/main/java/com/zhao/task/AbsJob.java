package com.zhao.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.kit.LogKit;

public abstract class AbsJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		String name = this.getClass().getName();
		LogKit.info("Job Start:" + name);

		try {
			// 业务处理
			process(context);
		} catch (Exception e) {
			LogKit.info("业务执行异常：", e);
		}

		LogKit.info("Job End:" + name);
	}
	/**
	 * 业务处理
	 * 
	 * @param context JOB上下文
	 */
	protected abstract void process(JobExecutionContext context);

}
