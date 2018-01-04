package me.imniu.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * Description:
 * 		实现了permissionResolver接口，
 * 		根据权限字符是否以+开头来解析权限字符串为BitPermisson或者为
 * 		WildcardPermission
 *
 * @author NiuJinpeng
 * @date   2018年1月3日下午3:37:02
 */
public class BitAndWildPermissionResolver  implements PermissionResolver{


	@Override
    public Permission resolvePermission(String permissionString) {
		System.out.println("判断是否以+开头..................:"+permissionString);
        if(permissionString.startsWith("+")) {
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }

}
