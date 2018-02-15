package net.codingme.ssm.shiro;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import net.codingme.ssm.po.ActiveUser;
import net.codingme.ssm.po.SysPermission;
import net.codingme.ssm.po.SysUser;
import net.codingme.ssm.service.SysService;

/**
 * <p>Description：自定义的realm</p>
 * 
 * @author  NiuJinpeng
 * @date    2018年1月15日上午11:40:07
 */
public class CustomRealm  extends AuthorizingRealm{
	
	private Logger logger = Logger.getLogger(CustomRealm.class);
	
	
	// 注入service
	@Autowired
	private SysService sysService;
	
	private String name = "CustomRealm";
	
	// 用于授权,没有连接数据库的方法
	/*@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 1.从principals获取身份信息
		String userCode  = (String) principals.getPrimaryPrincipal();
		
		// 2.根据身份信息获取权限信息
		// 连接数据库...
		// 模拟从数据库中获取到权限
		ArrayList<String> permissions = new ArrayList<String>();
		permissions.add("user:create");
		permissions.add("items:add");
		
		// 3.返回授权信息
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 把查询到的授权信息添加到 authorizationInfo对象中
		authorizationInfo.addStringPermissions(permissions);
		
		return authorizationInfo;
	}*/

	// 用于认证，没有链接数据库的方法
	/*@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 1.从toker中去除身份信息
		String userCode = (String) token.getPrincipal();
		
		// 2.根据用户输入的userCode从数据库查询出信息
		// ....
		// 模拟查询出密码
		String password = "111111";
		
		// 查询不到返回Null
		
		// 查询到信息返回认证信息AuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userCode,password,this.getName());
		return simpleAuthenticationInfo;
	}*/

	
	// realm授权，从数据库中查询权限
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		//从 principals获取主身份信息
		//将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
		ActiveUser activeUser =  (ActiveUser) principals.getPrimaryPrincipal();
		
		//根据身份信息获取权限信息
		//从数据库获取到权限数据
		List<SysPermission> permissionList = null;
		try {
			permissionList = sysService.findPermissionListByUserId(activeUser.getUserid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//单独定一个集合对象 
		List<String> permissions = new ArrayList<String>();
		if(permissionList!=null){
			for(SysPermission sysPermission:permissionList){
				//将数据库中的权限标签 符放入集合
				permissions.add(sysPermission.getPercode());
			}
		}
		
		//查到权限数据，返回授权信息(要包括 上边的permissions)
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//将上边查询到授权信息填充到simpleAuthorizationInfo对象中
		simpleAuthorizationInfo.addStringPermissions(permissions);
		logger.info("realm的授权获取.............");
		return simpleAuthorizationInfo;
	}
	
	// realm认证，从数据库查询用户信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 1.从token中去除用户名
		String userCode = (String) token.getPrincipal();

		// 2.根据用户输入的userCode从数据库中查询用户信息
		SysUser sysUser = null;

		try {
			sysUser = sysService.findSysUserByUserCode(userCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3.如果查询 不到返回Null
		if (sysUser == null) {
			return null;
		}
		// 获取数据库密码
		String password = sysUser.getPassword();
		// salt，盐
		String salt = sysUser.getSalt();

		// 4.根据用户id获取菜单
		List<SysPermission> menus = null;
		try {
			menus = sysService.findMenuListByUserId(sysUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 5.存储当前用户信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());
		activeUser.setMenus(menus);
		
		// 6.设置认证信息
		SimpleAuthenticationInfo simpleAuthenticationInfo = 
				new SimpleAuthenticationInfo(activeUser,
						password,ByteSource.Util.bytes(salt),this.getName());
		logger.info("realm的认证信息.............");
		return simpleAuthenticationInfo;
	}
	
	//清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
