package me.imniu.shiro.test;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Description:
 * 		不使用配置文件
 *
 * @author NiuJinpeng
 * @date   2018年1月4日上午10:15:52
 */
public class NonConfigurationCreateTest {
	
	@Test
	public void testNonConfigurationCreate(){
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		
		// 设置authenticator验证
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		// 设置策略
		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		
		securityManager.setAuthenticator(authenticator);
		
		// 设置authorizer 认证
		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
		authorizer.setPermissionResolver(new WildcardPermissionResolver());
		securityManager.setAuthorizer(authorizer);
		
		// 设置Realm
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://192.168.43.228:3306/shiro");
		ds.setUsername("root");
		ds.setPassword("123");
		
		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(ds);
		jdbcRealm.setPermissionsLookupEnabled(true);
		securityManager.setRealms(Arrays.asList((Realm) jdbcRealm));
		
		//将 SecurityManager 设置到 SecurityUtils 方便全局使用
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		subject.login(token);
		Assert.assertTrue(subject.isAuthenticated());
		
		
	}
}
