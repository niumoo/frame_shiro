package net.codingme.ssm.po;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description：用户记录登陆用户信息的类,
 * tomcat具有记录session的功能，这里实现序列化，在tomcat停止的时候会把
 * session序列化到磁盘，在重新启动的时候再读取session!
 * </p>
 * 
 * @author  NiuJinpeng
 * @date    2018年1月15日上午1:01:12
 */

public class ActiveUser implements Serializable {
	
	// 用户id，主键
	private String userid;
	// 用户账号
	private String usercode;
	// 用户名称
	private String username;
	// 菜单
	private List<SysPermission> menus;
	// 权限
	private List<SysPermission> permissions;
	
	
	public List<SysPermission> getMenus() {
		return menus;
	}
	public void setMenus(List<SysPermission> menus) {
		this.menus = menus;
	}
	public List<SysPermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

}
