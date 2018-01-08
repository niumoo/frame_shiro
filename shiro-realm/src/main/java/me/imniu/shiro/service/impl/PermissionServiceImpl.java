package me.imniu.shiro.service.impl;

import me.imniu.shiro.dao.PermissionDao;
import me.imniu.shiro.dao.PermissionDaoImpl;
import me.imniu.shiro.po.Permission;
import me.imniu.shiro.service.PermissionService;

public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao = new PermissionDaoImpl();

    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }

}
