package me.imniu.shiro.listener;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Description:
 *			shiro的 会话管理测试，这里实现了SessionListener，实现了所有的方法
 * @author NiuJinpeng
 * @date   2018年1月9日上午9:12:58
 */
public class ShiroSessionListener1 implements SessionListener {
	
	private static Logger logger = Logger.getLogger(ShiroSessionListener1.class);

	/**
	 * 会话创建时刻
	 */
	@Override
	public void onStart(Session session) {
		logger.info("会话已经创建："+session.getId());
	}

	/**
	 * 会话退出或者过期时刻
	 */
	@Override
	public void onStop(Session session) {
		logger.info("会话停止："+session.getId());
	}
	/**
	 * 会话过期时刻
	 */
	@Override
	public void onExpiration(Session session) {
		logger.info("会话过期时刻："+session.getId());
		
	}

}
