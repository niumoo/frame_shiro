package me.imniu.shiro.service.impl;

import java.util.Set;

import me.imniu.shiro.dao.UserDao;
import me.imniu.shiro.dao.UserDaoImpl;
import me.imniu.shiro.po.User;
import me.imniu.shiro.service.UserService;
import me.imniu.shiro.util.PasswordHelper;

/**
 * Description:
 *
 * @author NiuJinpeng
 * @date   2018年1月4日下午3:09:02
 */
public class UserServiceImpl implements UserService {
	
	private PasswordHelper passwordHelper = new PasswordHelper();
	private UserDao userDao = new UserDaoImpl();
	
	/**
	 * 创建账户
	 */
	@Override
	public User createUser(User user) {
		// 加密密码
		passwordHelper.encryPassword(user);
		return userDao.createUser(user);
	}

	/**
	 * 更新密码
	 */
	@Override
	public void changePassword(Long userId, String newPassword) {
		User user = userDao.findOne(userId);
		user.setPassword(newPassword);
		passwordHelper.encryPassword(user);
		userDao.updateUser(user);
	}

	/**
	 * 添加用户-角色关系
	 */
	@Override
	public void correlationRoles(Long userId, Long... roleIds) {
		userDao.correlationRoles(userId, roleIds);
	}
	/**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void uncorrelationRoles(Long userId, Long... roleIds) {
        userDao.uncorrelationRoles(userId, roleIds);
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username) {
        return userDao.findRoles(username);
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username) {
        return userDao.findPermissions(username);
    }

}
