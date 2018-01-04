package me.imniu.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import me.imniu.shiro.permission.BitPermission;

/**
 * Description:
 *
 * @author NiuJinpeng
 * @date   2018年1月3日下午3:45:28
 */
public class MyRealm extends AuthorizingRealm {
	
	/**
	 * 获取唯一的名字
	 */
	@Override
	public String getName() {
		return "myrealm1";
	}
	
	/**
	 * 根据用户身份获取授权信息
	 * principal:
	 * adj.  最重要的; 主要的; 资本的; 本金的;
	 *	n.  首长，负责人; 主要演员，主角; [法]委托人，当事人; 本金;
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("根据用户身份获取授权信息...............");
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addRole("role1");
		authorizationInfo.addRole("role2");
		authorizationInfo.addObjectPermission(new BitPermission("+user1+10"));
		authorizationInfo.addObjectPermission(new WildcardPermission("user1:*"));
		// 字符串权限会调用PermissionResilver进行解析
		authorizationInfo.addStringPermission("+user2+10");
		authorizationInfo.addStringPermission("user2:*");
		return authorizationInfo;
	}

	
	/**
	 * 获取身份验证信息
	 * 
	 * authentication： 证明，鉴定; 身份验证; 认证; 密押;
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("获取身份验证信息...............");
		// 获取用户名
		String username = (String) token.getPrincipal();
		// 获取密码
		String password = new String((char[]) token.getCredentials());
		// 判断用户名和密码
		if (!("zhang".equals(username))) {
			throw new UnknownAccountException();
		}
		if (!("123".equals(password))) {
			throw new UnknownAccountException();
		}
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现
		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
