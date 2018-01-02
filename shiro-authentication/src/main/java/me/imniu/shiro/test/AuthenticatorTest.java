package me.imniu.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 * 	认证测试，测试Authenticator 及 AuthenticationStrategy
 * 
 * FirstSuccessfulStrategy： 只要有一个 Realm 验证成功即可， 只返回第一个 Realm 身份验证
 * 						           成功的认证信息， 其他的忽略；
 * 
 * AtLeastOneSuccessfulStrategy： 只要有一个 Realm 验证成功即可， 和 FirstSuccessfulStrategy
 * 						                         不同， 返回所有 Realm 身份验证成功的认证信息；
 * 
 * AllSuccessfulStrategy： 所有 Realm 验证成功才算成功， 且返回所有 Realm 身份验证成功的
 * 						     认证信息， 如果有一个失败就失败了。
 * 
 * ModularRealmAuthenticator 默认使用 AtLeastOneSuccessfulStrategy 策略
 * 
 * 
 * @author NiuJinpeng
 * @date   2018年1月2日下午4:43:19
 */
public class AuthenticatorTest {
	
	/**
	 * 通用化登录逻辑
	 * @param configFile
	 */
	private void login(String configFile){
		// 1.获取securityManager工厂，此处使用ini配置文件初始化SecurityManager
		IniSecurityManagerFactory factory = new IniSecurityManagerFactory(configFile);
		
		// 2.得到securityManager实例，绑定给SecurityUtils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		
		//3、 得到 Subject 及创建用户名 /密码身份验证 Token（即用户身份/凭证）
		 Subject subject = SecurityUtils.getSubject();
		 UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		 subject.login(token);
	}
	
	/**
	 * AllSuccessfulStrategy： 所有 Realm 验证成功才算成功， 且返回所有 Realm 身份验证成功的
	 * 认证信息， 如果有一个失败就失败了。这里通过第一个，然后通过第三个realm，且第三个返回为name@163.com
	 * 所有输出2
	 */
	@Test(expected = UnknownAccountException.class)
	public void testAllSuccessfulStrategyWithSuccess(){
		login("classpath:shiro-authenticator-all-success.ini");
		Subject subject = SecurityUtils.getSubject();
		
		//得到一个身份集合，包含了Realm验证成功的身份信息
		PrincipalCollection principalCollection = subject.getPrincipals();
		System.out.println(principalCollection.asList().size());
	}
	
	/**
	 * AllSuccessfulStrategy： 所有 Realm 验证成功才算成功， 且返回所有 Realm 身份验证成功的
	 * 认证信息， 如果有一个失败就失败了。
	 * 这里可以通过第一个，但是不能通过第二个，所以没有成功
	 */
	@Test(expected = UnknownAccountException.class)
	public void testAllSuccessfulStrategyWithFail() {
		login("classpath:shiro-authenticator-all-fail.ini");
		// Subject subject = SecurityUtils.getSubject();
		//
		// //得到一个身份集合，包含了Realm验证成功的身份信息
		// PrincipalCollection principalCollection = subject.getPrincipals();
		// System.out.println(principalCollection.asList().size());
	}
	
	/**
	 *  只要有一个 Realm 验证成功即可， 只返回第一个 Realm 身份验证
     * 	成功的认证信息， 其他的忽略；所以，这里通过了1和3,但是size为1
	 */
	 @Test
	public void testFirstOneSuccessfulStrategyWithSuccess() {
		login("classpath:shiro-authenticator-first-success.ini");
		Subject subject = SecurityUtils.getSubject();

		// 得到一个身份集合，其包含了第一个Realm验证成功的身份信息
		PrincipalCollection principalCollection = subject.getPrincipals();
		Assert.assertEquals(1, principalCollection.asList().size());
	}

	/**
	 *  只要有一个 Realm 验证成功即可， 和 FirstSuccessfulStrategy
     * 	不同， 返回所有 Realm 身份验证成功的认证信息，所以这里通过了1，没有通过2，返回size为1
	 */
	@Test
	public void testAtLeastOneSuccessfulStrategyWithSuccess() {
		login("classpath:shiro-authenticator-atLeastOne-success.ini");
		Subject subject = SecurityUtils.getSubject();

		// 得到一个身份集合，其包含了Realm验证成功的身份信息
		PrincipalCollection principalCollection = subject.getPrincipals();
		Assert.assertEquals(1, principalCollection.asList().size());
	}
	
	
	/**
	 *  只要有一个 Realm 验证成功即可， 和 FirstSuccessfulStrategy
     * 	不同， 返回所有 Realm 身份验证成功的认证信息，所以这里通过了1，通过3，返回size为2
	 */
	@Test
    public void testAtLeastTwoStrategyWithSuccess() {
        login("classpath:shiro-authenticator-atLeastTwo-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，因为myRealm1和myRealm4返回的身份一样所以输出时只返回一个
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
    }
	
    @Test
    public void testOnlyOneStrategyWithSuccess() {
        login("classpath:shiro-authenticator-onlyone-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，因为myRealm1和myRealm4返回的身份一样所以输出时只返回一个
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
    }
	
	
	/**
	 * 退出时请解除绑定Subject到线程 否则对下次测试造成影响
	 * @throws Exception
	 */
	@After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();
    }

}
