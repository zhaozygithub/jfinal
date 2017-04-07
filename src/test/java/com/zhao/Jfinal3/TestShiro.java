package com.zhao.Jfinal3;



import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class TestShiro extends BaseTest{
	

	@Test
	public void testIsPermitted() {
	login("shiro-role.ini", "zhang", "123");
	//判断拥有权限：user:create
	Assert.assertTrue(subject().isPermitted("user:create"));
	//判断拥有权限：user:update and user:delete
	Assert.assertTrue(subject().isPermittedAll("user:update", "user:delete"));
	//判断没有权限：user:view
	Assert.assertFalse(subject().isPermitted("user:view"));
	}

	
	@Test
	public void testHasRole() {
	login("shiro-role.ini", "zhao", "123");
	Assert.assertTrue(subject().hasRole("role1"));
	//判断拥有角色：role1 and role2
	Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
	//判断拥有角色：role1 and role2 and !role3
	boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
	Assert.assertEquals(true, result[0]);
	Assert.assertEquals(true, result[1]);
	Assert.assertEquals(false, result[2]);
	}

	@Test
	public void testjdbcRealm() {
		login("realm.ini", "zhang", "123");
	}
	
	@Test
	public void test() {
		login("shiro.ini", "zhao", "123");
	}

}
