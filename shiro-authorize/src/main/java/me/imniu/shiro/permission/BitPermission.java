package me.imniu.shiro.permission;

import org.apache.shiro.authz.Permission;

import com.alibaba.druid.util.StringUtils;

/**
 * Description:
 *
 * @author NiuJinpeng
 * @date   2018年1月3日下午2:51:36
 */
public class BitPermission implements Permission {
	
	// 资源标识位
	private String resourceIdentify;
	// 权限位
	private int permissionBit;
	// 实例ID
	private String instanceId;
	/**
	 *  规则
	 *    +资源字符串+权限位+实例ID
	 *
	 *  以+开头 中间通过+分割
	 *
	 *  权限：
	 *     0 表示所有权限
	 *     1 新增 0001
	 *     2 修改 0010
	 *     4 删除 0100
	 *     8 查看 1000
	 *
	 *  如 +user+10 表示对资源user拥有修改/查看权限
	 *
	 *  不考虑一些异常情况
	 *
	 */
	public BitPermission(String permissionString){
		System.out.println("构造BitPermission..................:"+permissionString);
		String[] array=permissionString.split("\\+");
		if(array.length < 1 ){
			resourceIdentify = array[1];
		}
		if(StringUtils.isEmpty(resourceIdentify)){
			resourceIdentify = "*";
		}
		if(array.length > 2){
			permissionBit = Integer.valueOf(array[2]);
		}
		if(array.length > 3){
			instanceId = array[3];
		}
		if(StringUtils.isEmpty(instanceId)){
			instanceId = "*";
		}
	}
	

	/**
	 * 用于判断权限匹配
	 */
	@Override
	public boolean implies(Permission p) {
		System.out.println("判断权限匹配..................");
		
		if(!(p instanceof BitPermission)){
			return false;
		}
		BitPermission other = (BitPermission)p;
		if(!("*".equals(this.resourceIdentify) ||
			   this.resourceIdentify.equals(other.resourceIdentify))) {
			 return false;
		}
		
		if(!(this.permissionBit == 0 ||
				(this.permissionBit & other.permissionBit)!=0)){
			return false;
		}
		
		if(!("*".equals(this.instanceId) ||
				this.instanceId.equals(other.instanceId))){
			return false;
		}
		System.out.println("判断权限通过！！！");
		return true;
	}

}
