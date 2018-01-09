package me.imniu.shiro.listener;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 * Description:
 *			只想监听某一个事件，则使用适配器模式。继承SessionListenerAdapter
 * @author NiuJinpeng
 * @date   2018年1月9日上午9:17:41
 */
public class ShiroSessionListener2 extends SessionListenerAdapter{
	
	private static Logger logger = Logger.getLogger(ShiroSessionListener2.class);
	
	@Override
	public void onStart(Session session) {
		logger.info("会话已经创建："+session.getId());
	}
}
