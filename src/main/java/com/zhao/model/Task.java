package com.zhao.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.zhao.model.base.BaseTask;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Task extends BaseTask<Task> {
	public static final Task dao = new Task().dao();
	
	/** 暂停 **/
	public static final int STATE_STOP = 0;
	/** 运行 **/
	public static final int STATE_START = 1;

	public List<Task> findAll(){
		return this.find("select * from task");
	}
	
	public List<Task> findByStart(){
		return this.find("select * from task where state = 2");
	}
	
	public int updateState(int id, int state){
		return Db.update("update task set state = ? where id = ?", state, id);
}
	}
