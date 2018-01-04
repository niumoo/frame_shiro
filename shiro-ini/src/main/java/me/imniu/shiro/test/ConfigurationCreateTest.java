package me.imniu.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description:通过配置创建SecurityUtils
 * 
 * IniSecurityManagerFactory 是创建 securityManager 的工厂， 其需要一个 ini 配置文件路径
 * 其支持“ classpath:” （类路径） 、 “ file:” （文件系统） 、 “url:” （网络） 三种路径格式
 * 默认是文件系统；
 * @author NiuJinpeng
 * @date   2018年1月4日上午10:26:16
 */
public class ConfigurationCreateTest {
	
	@Test
	public void testConfigurationCreate() {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro-config.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		
		// 将 SecurityManager 设置到 SecurityUtils 方便全局使用
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		
		subject.login(token);
		
		Assert.assertTrue(subject.isAuthenticated());
	}

}
