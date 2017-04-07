package com.zhao.Jfinal3;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-1-26
 * <p>
 * Version: 1.0
 */
public abstract class BaseTest {
	
	private static String path = null;

	@Before
	public void setUp() throws Exception {
		path = "D:/mars/Jfinal3/shiroIni/";
	}

	@After
	public void tearDown() throws Exception {
		ThreadContext.unbindSubject();// 退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}

	protected void login(String iniName, String username, String pwd) {
		// TODO Auto-generated method stub
		// 1.获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
				Factory<SecurityManager> factory = new IniSecurityManagerFactory(path+iniName);
				// 2.获取SecurityManager实例，并绑定到SecurityUtils
				SecurityManager sm = factory.getInstance();
				SecurityUtils.setSecurityManager(sm);

				// 3.得到Subject
				Subject subject = SecurityUtils.getSubject();
				// 4.创建用户登录凭证
				UsernamePasswordToken token = new UsernamePasswordToken(username,pwd);
				// 5.登录，如果登录失败会抛出不同的异常，根据异常输出失败原因
				try {
					subject.login(token);
					// 6.判断是否成功登录
					assertEquals(true, subject.isAuthenticated());
					System.out.println("登录成功！！");
					// 7.注销用户
//					subject.logout();
				} catch (IncorrectCredentialsException e) {
					System.out.println("登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.");
				} catch (ExcessiveAttemptsException e) {
					System.out.println("登录失败次数过多");
				} catch (LockedAccountException e) {
					System.out.println("帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.");
				} catch (DisabledAccountException e) {
					System.out.println("帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.");
				} catch (ExpiredCredentialsException e) {
					System.out.println("帐号已过期. the account for username " + token.getPrincipal() + "  was expired.");
				} catch (UnknownAccountException e) {
					System.out.println("帐号不存在. There is no user with username of " + token.getPrincipal());
				}
				
	}


	public Subject subject() {
		return SecurityUtils.getSubject();
	}
}
