package net.codingme.ssm.service;

import java.util.List;

import net.codingme.ssm.po.ActiveUser;
import net.codingme.ssm.po.SysPermission;
import net.codingme.ssm.po.SysUser;

/**
 * <p>Description：认证授权服务接口</p>
 * 
 * <p>www.codingme.net</p>
 * @author  NiuJinpeng
 * @date    2018年1月15日上午1:11:16
 * @version 1.0
 */
public interface SysService {

	// 根据用户的身份和密码认证，如果通过，返回用户信息
	public ActiveUser authenticad(String userCode, String password) throws Exception;
	
	// 根据用户账号查询用户信息
	public SysUser findSysUserByUserCode(String userCode)throws Exception;
	
	//根据用户id获取权限菜单 
	List<SysPermission> findMenuListByUserId(String userid)throws Exception;
	
	//根据用户id获取权限
	List<SysPermission> findPermissionListByUserId(String userid)throws Exception;


}
