package com.zhao.Controller;

import java.io.IOException;

import org.quartz.JobKey;

import com.jfinal.core.Controller;
import com.zhao.model.Task;
import com.zhao.plugin.QuartzPlugin;

public class TaskController extends Controller {
	public void index() {
//		Record record = Db.findById("user", 1);
//		System.out.println(record.getStr("user_name"));
		render("/user/ds.html");
	}
	
	public void start() throws IOException {
		String id2 = getPara("name");
		int id=Integer.parseInt(id2);

		Task task = Task.dao.findById(id);

		try {
			String className = task.getStr("clazz");

			// 恢复任务
			JobKey jobKey = JobKey.jobKey(className, className);
			QuartzPlugin.scheduler.resumeJob(jobKey);

			Task.dao.updateState(id, Task.STATE_START);
		} catch (Exception e) {
			e.printStackTrace();
			renderJson("任务启动失败！");
		}
		renderText("启动成功");
	}
	
	// 暂停任务
		public void stop() {
			String id2 = getPara("stop");
			int id=Integer.parseInt(id2);

			Task task = Task.dao.findById(id);

			try {
				String className = task.getStr("clazz");

				// 暂停任务
				JobKey jobKey = JobKey.jobKey(className, className);
				QuartzPlugin.scheduler.pauseJob(jobKey);

				Task.dao.updateState(id, Task.STATE_STOP);
			} catch (Exception e) {
				e.printStackTrace();
				renderJson("任务停止失败！");
			}

			renderText("stop成功");
		}

		// 立即运行一次任务
		public void trigger() {
			String id2 = getPara("trigger");
			int id=Integer.parseInt(id2);

			Task task = Task.dao.findById(id);

			try {
				String className = task.getStr("clazz");

				// 立即触发一次
				JobKey jobKey = JobKey.jobKey(className, className);
				QuartzPlugin.scheduler.triggerJob(jobKey);

			} catch (Exception e) {
				e.printStackTrace();
				renderJson("任务停止失败！");
			}

			renderText("立即运行一次成功");
		}
	
	
}
