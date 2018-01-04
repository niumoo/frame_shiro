package me.imniu.shiro.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 * 
 * @author NiuJinpeng
 * @date 2018年1月3日下午4:23:21
 */
public class AuthorizerTest extends BaseTest {

	@Test
	public void testIsPermitted() {
		login("classpath:shiro-authorizer.ini", "zhang", "123");
		// 判断拥有权限：user:create
//		Assert.assertTrue(subject().isPermitted("user1:update"));
//		System.out.println("-------------------");
//		Assert.assertTrue(subject().isPermitted("user2:update"));
		System.out.println("-------------------");
//		 通过二进制位的方式表示权限
		Assert.assertTrue(subject().isPermitted("+user1+2"));// 新增权限 ？？？？？？？？？？
		System.out.println("-------------------");
//		Assert.assertTrue(subject().isPermitted("+user1+8"));// 查看权限
//		System.out.println("-------------------");
//		Assert.assertTrue(subject().isPermitted("+user2+10"));// 新增及查看
//
//		Assert.assertFalse(subject().isPermitted("+user1+4"));// 没有删除权限  ？？？？？？？？？
//
//		Assert.assertTrue(subject().isPermitted("menu:view"));// 通过MyRolePermissionResolver解析得到的权限
	}
	

    @Test
    public void testIsPermitted2() {
        login("classpath:shiro-jdbc-authorizer.ini", "zhang", "123");
        //判断拥有权限：user:create
        Assert.assertTrue(subject().isPermitted("user1:update"));
        Assert.assertTrue(subject().isPermitted("user2:update"));
        //通过二进制位的方式表示权限
        Assert.assertTrue(subject().isPermitted("+user1+2"));//新增权限
        Assert.assertTrue(subject().isPermitted("+user1+8"));//查看权限
        Assert.assertTrue(subject().isPermitted("+user2+10"));//新增及查看

        Assert.assertFalse(subject().isPermitted("+user1+4"));//没有删除权限

        Assert.assertTrue(subject().isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
    }

}
