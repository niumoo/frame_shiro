package me.imniu.shiro.permission;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * Description:
 *		根据角色字符串来解析得到权限集合
 * @author NiuJinpeng
 * @date   2018年1月3日下午3:40:11
 */
public class MyRolePermissionResolver implements RolePermissionResolver {

	/**
	 * 如果用户拥有role1，那么就返回一个menu.*  的权限
	 */
	  @Override
	    public Collection<Permission> resolvePermissionsInRole(String roleString) {
		  System.out.println("正要根据角色字符串解析权限："+roleString);
	        if("role1".equals(roleString)) {
	        	 System.out.println("根据角色字符串来解析得到权限集合..............."+roleString);
	        	List<Permission> asList = Arrays.asList((Permission)new WildcardPermission("menu:*"));
	        	
	            return Arrays.asList((Permission)new WildcardPermission("menu:*"));
	        }
	        return null;
	    }
}
