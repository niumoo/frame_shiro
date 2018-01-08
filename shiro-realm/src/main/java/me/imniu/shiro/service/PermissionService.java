package me.imniu.shiro.service;

import me.imniu.shiro.po.Permission;

/**
 * Description:
 *
 * @author NiuJinpeng
 * @date   2018年1月4日下午2:59:26
 */
public interface PermissionService {
	
	/**
	 * 创建
	 * @param permission
	 * @return
	 */
	public Permission createPermission(Permission permission);
	
	/**
	 * 删除
	 * @param permissionId
	 */
	public void deletePermission(Long permissionId);

}
