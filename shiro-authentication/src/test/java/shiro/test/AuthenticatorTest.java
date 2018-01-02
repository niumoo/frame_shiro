package shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Description:
 * 	认证测试，测试Authenticator 及 AuthenticationStrategy
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
	 * 
	 */
	@Test
	public void testAllSuccessfulStrategyWithSuccess(){
		login("classpath:shiro-authenticator-all-success.ini");
		Subject subject = SecurityUtils.getSubject();
		
		//得到一个身份集合，包含了Realm验证成功的身份信息
		PrincipalCollection principalCollection = subject.getPrincipals();
		System.out.println(principalCollection.asList().size());
	}

}
