package me.imniu.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import junit.framework.Assert;

/**
 * Description:shiro的身份认证 测试
 *
 * @author NiuJinpeng
 * @date   2018年1月2日上午11:37:31
 */
public class LoginLogoutTest {
	
	/**
	 * 身份认证测试，读取shiro.ini中的内容作为身份凭据
	 */
	@Test
	public void testLoginLoginout(){
		// 1.获取SecurityManager工厂，使用ini配置文件初始化SecurityManager
		IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		
		// 2.得到SecurityManager示例，并绑定给SecurityUtils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		
		// 3.得到Subject以及创建用户名/密码身份验证Token（用户身份凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		
		try{
			// 4.登录认证
			subject.login(token);
			// 5.输出状态
			Assert.assertEquals(true,subject.isAuthenticated());
			System.out.println(subject.isAuthenticated());
		}catch (Exception e) {
			System.out.println("出现异常："+e.getClass());
		}
		
		// 6.登出
		subject.logout();
		
		// 7.输出状态
		System.out.println(subject.isAuthenticated());
		
	}
	
	/**
	 * 身份认证测试，使用自己编写的单realm认证
	 */
	@Test
	public void testCustomRealm(){
		// 1.获取SecurityManager工厂，使用ini配置文件初始化SecurityManager
		IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
		
		// 2.得到SecurityManager示例，并绑定给SecurityUtils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		
		// 3.得到Subject以及创建用户名/密码身份验证Token（用户身份凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		
		try{
			// 4.登录认证
			subject.login(token);
			// 5.输出状态
			Assert.assertEquals(true,subject.isAuthenticated());
			System.out.println(subject.isAuthenticated());
		}catch (Exception e) {
			System.out.println("出现异常："+e.getClass());
		}
		// 6.登出
		subject.logout();
		
		// 7.输出状态
		System.out.println(subject.isAuthenticated());
	}
	
	/**
	 * 身份认证测试，测试自己编写的多realm认证
	 */
	@Test
	public void testCustomMultiRealm(){
		// 1.获取SecurityManager工厂，使用ini配置文件初始化SecurityManager
		IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-multi-realm.ini");
		
		// 2.得到SecurityManager示例，并绑定给SecurityUtils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		
		// 3.得到Subject以及创建用户名/密码身份验证Token（用户身份凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("niu","123");
		
		try{
			// 4.登录认证
			subject.login(token);
			// 5.输出状态
			Assert.assertEquals(true,subject.isAuthenticated());
			System.out.println(subject.isAuthenticated());
		}catch (Exception e) {
			System.out.println("出现异常："+e.getClass());
		}
		// 6.登出
		subject.logout();
		
		// 7.输出状态
		System.out.println(subject.isAuthenticated());
	}
	
	/**
	 * 身份认证测试，使用数据库
	 */
	@Test
	public void testJDBCRealm(){
		// 1.获取SecurityManager工厂，使用ini配置文件初始化SecurityManager
		IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
		
		// 2.得到SecurityManager示例，并绑定给SecurityUtils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		
		// 3.得到Subject以及创建用户名/密码身份验证Token（用户身份凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		
		try{
			// 4.登录认证
			subject.login(token);
			// 5.输出状态
			Assert.assertEquals(true,subject.isAuthenticated());
			System.out.println(subject.isAuthenticated());
		}catch (Exception e) {
			System.out.println("出现异常："+e.getClass());
		}
		// 6.登出
		subject.logout();
		
		// 7.输出状态
		System.out.println(subject.isAuthenticated());
	}
	
	
}
