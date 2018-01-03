package me.imniu.shiro.test;


import java.util.Arrays;

import org.junit.Test;

import junit.framework.Assert;

/**
 * Description:认证测试
 *
 * @author NiuJinpeng
 * @date   2018年1月3日上午9:26:14
 */
public class RoleTest extends BaseTest {
	
	/**
	 * 测试是否拥有某个权限
	 */
	@Test
	public void testHasRole(){
		login("classpath:shiro-role.ini","zhang","123");
		// 判断是否拥有觉得：role1
		Assert.assertTrue(subject().hasRole("role1"));
		
		// 判断拥有角色：role1 and role2
		Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1","role2")));
		
		// 判断拥有角色：role1 and role2 and role3
		boolean[] result = subject().hasRoles(Arrays.asList("role1","role2","role3"));
		Assert.assertEquals(true,result[0]);
		Assert.assertEquals(true,result[1]);
		Assert.assertEquals(false,result[2]);
	}

	/**
	 *  检查是否拥有某个/某些权限
	 *  checkRole/checkRoles 和 hasRole/hasAllRoles 
	 *  在为真时候，没有任何提示，为假时候，会抛出异常
	 */
	@Test
	public void testCheckRole() {
		login("classpath:shiro-role.ini", "niu", "123");
		subject().checkRole("role1");
		subject().checkRoles("role1", "role3");
		// subject().checkRoles("role1","role2");
	}
	
}
