package net.codingme.ssm.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.codingme.ssm.exception.CustomException;
import net.codingme.ssm.mapper.SysPermissionMapperCustom;
import net.codingme.ssm.mapper.SysUserMapper;
import net.codingme.ssm.po.ActiveUser;
import net.codingme.ssm.po.SysPermission;
import net.codingme.ssm.po.SysUser;
import net.codingme.ssm.po.SysUserExample;
import net.codingme.ssm.po.SysUserExample.Criteria;
import net.codingme.ssm.service.SysService;
import net.codingme.ssm.util.MD5;

/**
 * <p>Description：认证和授权的服务接口</p>
 * 
 * @author  NiuJinpeng
 * @date    2018年1月15日上午1:13:34
 * @version 1.0
 */

@Service
public class SysServiceImpl implements SysService{
	
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysPermissionMapperCustom sysPermissionMapperCustom;
	
	/**
	 * 认证
	 */
	@Override
	public ActiveUser authenticad(String userCode, String password) throws Exception {
		// 查询用户信息
		SysUser sysUser = this.findSysUserByUserCode(userCode);
		if(sysUser == null) {
			throw new CustomException("用户账号不存在！");
		}
		// 获取数据库密码
		String password_db = sysUser.getPassword();
		
		// 比对密码
		// 先加密密码
		String password_input_md5 = new MD5().getMD5ofStr(password);
		// 对比且忽略大小写
		if(!password_db.equalsIgnoreCase(password_input_md5)) {
			throw new CustomException("用户名或者密码错误！");
		}
		
		String userId = sysUser.getId();
		List<SysPermission> menus = this.findMenuListByUserId(userId);
		List<SysPermission> permissions = this.findPermissionListByUserId(userId);
		
		// 认真通过，返回用户信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUsercode(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());
		
		// 放入权限范围的菜单和url
		activeUser.setMenus(menus);
		activeUser.setPermissions(permissions);
		return activeUser;
		
	}
	
	//根据用户账号查询用户信息
	public SysUser findSysUserByUserCode(String userCode)throws Exception{
		SysUserExample sysUserExample = new SysUserExample();
		Criteria criteria = sysUserExample.createCriteria();
		criteria.andUsercodeEqualTo(userCode);
		
		List<SysUser> list = sysUserMapper.selectByExample(sysUserExample);
		if(list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据用户id查询用户权限的菜单
	 */
	@Override
	public List<SysPermission> findMenuListByUserId(String userid) throws Exception {
		return  sysPermissionMapperCustom.findMenuListByUserId(userid);
	}
	
	/**
	 * 根据用户id查询用户权限
	 */
	@Override
	public List<SysPermission> findPermissionListByUserId(String userid) throws Exception {
		return sysPermissionMapperCustom.findPermissionListByUserId(userid);
	}
	
	
	@Test
	public void test() {
		System.out.println(new MD5().getMD5ofStr("123"));
	}

}
