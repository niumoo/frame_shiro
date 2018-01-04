package me.imniu.shiro.test;



import org.apache.shiro.authz.permission.WildcardPermission;
import org.junit.Test;

import junit.framework.Assert;

/**
 * Description:认证测试
 *
 * @author NiuJinpeng
 * @date   2018年1月3日上午9:26:14
 */
public class PermissionTest extends BaseTest {
	
	/**
	 * 测试是否拥有某个/某些权限
	 */
	@Test
	public void testIsPermitted(){
		login("classpath:shiro-permission.ini","zhang","123");
		// 判断是否拥有权限：user:create
		Assert.assertTrue(subject().isPermitted("user:create"));
		
		// 判断是否拥有权限：user:create，user:delete
		Assert.assertTrue(subject().isPermittedAll("user:update","user:delete"));
		
		// 判断是否拥有权限：user:view （这个并没有）
		Assert.assertFalse(subject().isPermitted("user:view"));
		
	}

	/**
	 *  检查是否拥有某个/某些权限
	 *  checkRole/checkRoles 和 hasRole/hasAllRoles 
	 *  在为真时候，没有任何提示，为假时候，会抛出异常
	 */
	@Test
	public void testCheckPermission() {
		login("classpath:shiro-permission.ini", "zhang", "123");
		// 断言拥有权限：user:create
		subject().checkPermission("user:create");
		
		// 断言拥有权限：user:delete user:update
		subject().checkPermissions("user:create","user:delete");

		// 断言拥有权限：user:create
//		subject().checkPermission("user:create");

	}
	
	

    @Test
    public void testWildcardPermission1() {
        login("classpath:shiro-permission.ini", "li", "123");

        subject().checkPermissions("system:user:update", "system:user:delete");
        subject().checkPermissions("system:user:update,delete");
    }

    @Test
    public void testWildcardPermission2() {
        login("classpath:shiro-permission.ini", "li", "123");
        subject().checkPermissions("system:user:create,delete,update:view");

        subject().checkPermissions("system:user:*");
        subject().checkPermissions("system:user");
    }

    @Test
    public void testWildcardPermission3() {
        login("classpath:shiro-permission.ini", "li", "123");
        subject().checkPermissions("user:view");

        subject().checkPermissions("system:user:view");
    }

    @Test
    public void testWildcardPermission4() {
        login("classpath:shiro-permission.ini", "li", "123");
        subject().checkPermissions("user:view:1");

        subject().checkPermissions("user:delete,update:1");
        subject().checkPermissions("user:update:1", "user:delete:1");

        subject().checkPermissions("user:update:1", "user:delete:1", "user:view:1");

        subject().checkPermissions("user:auth:1", "user:auth:2");

    }

    @Test
    public void testWildcardPermission5() {
        login("classpath:shiro-permission.ini", "li", "123");
        subject().checkPermissions("menu:view:1");

        subject().checkPermissions("organization");
        subject().checkPermissions("organization:view");
        subject().checkPermissions("organization:view:1");

    }


    @Test
    public void testWildcardPermission6() {
        login("classpath:shiro-permission.ini", "li", "123");
        subject().checkPermission("menu:view:1");
        subject().checkPermission(new WildcardPermission("menu:view:1"));
    }
	
	
}
