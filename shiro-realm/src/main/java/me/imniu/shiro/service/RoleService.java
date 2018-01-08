package me.imniu.shiro.service;


import me.imniu.shiro.po.Role;

/**
 * Description:
 *
 * @author NiuJinpeng
 * @date   2018年1月4日下午2:59:26
 */
public interface RoleService {

	public Role createRole(Role role);

	public void deleteRole(Long roleId);

	// 添加角色-权限之间关系
	public void correlationPermissions(Long roleId, Long... permissionIds);

	// 移除角色-权限之间关系
	public void uncorrelationPermissions(Long roleId, Long... permissionIds);//
}
