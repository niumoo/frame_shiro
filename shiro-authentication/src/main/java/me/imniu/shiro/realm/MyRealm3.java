package me.imniu.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

/**
 * Description:自定义的多Realm 配置
 *
 * @author NiuJinpeng
 * @date   2018年1月2日下午2:47:31
 */
public class MyRealm3 implements Realm{

	/**
	 * 获取唯一的名字
	 */
	@Override
	public String getName() {
		return "myrealm3";
	}

	/**
	 * 是否支持此token
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	/**
	 *  根据Token获取认证信息
	 */
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取用户名
		String username = (String) token.getPrincipal();
		// 获取密码
		String password = new String((char[])token.getCredentials()); 
		// 判断用户名和密码
		if(!("zhang".equals(username))){
			throw new UnknownAccountException();
		}
		if(!("123".equals(password))){
			throw new UnknownAccountException();
		}
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现
		return new SimpleAuthenticationInfo(username+"@163.com",password,getName());
	}

}
