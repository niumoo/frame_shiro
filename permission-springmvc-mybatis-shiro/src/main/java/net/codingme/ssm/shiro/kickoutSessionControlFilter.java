package net.codingme.ssm.shiro;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * <p>Description：并发登陆人数控制
 *    检测同一个账号的登陆情况，这里配置检测同一个账号在
 *    同一个时间只能有同一个账号进行登陆，多处登陆则踢出
 *    前者登陆状态
 * </p>
 * @author  NiuJinpeng
 * @date    2018年1月17日上午9:12:18
 */
public class kickoutSessionControlFilter extends AccessControlFilter {
	
	// 踢出后重定向的地址
	private String kickoutUrl;
	// 提示之后登陆的人吗？false则踢出之前登陆者，true则踢出之后登陆者
	private boolean kickoutAfter = false;
	// 同一个账号的最大会话人数
	private int maxSession = 1;
	
	private SessionManager sessionManager;
	private Cache<String,Deque<Serializable>> cache;
	
	/*
	 * 是否允许访问，允许返回true，否则false
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	/*
	 *  onAccessDenied：当访问拒绝时是否已经处理了
	 *  true：说明没有处理完毕，需要继续处理
	 *  falst:处理完毕，直接返回
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		
		if(!subject.isAuthenticated() && !subject.isRemembered()) {
			// 没有登陆，继续处理
			return true;
		}
		
		Session session = subject.getSession();
	    String username = (String) subject.getPrincipal();
	    Serializable sessionId = session.getId();
	    
	    //同步控制
	    Deque<Serializable> deque = cache.get(username);
	    if(deque == null ) {
	    	deque = new LinkedList<Serializable>();
	    	cache.put(username, deque);
	    }
	    
	    //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(request);
            WebUtils.issueRedirect(request, response, kickoutUrl);
            return false;
        }

        return true;
	}


	public String getKickoutUrl() {
		return kickoutUrl;
	}

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public boolean isKickoutAfter() {
		return kickoutAfter;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public int getMaxSession() {
		return maxSession;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public Cache<String, Deque<Serializable>> getCache() {
		return cache;
	}

	public void setCache(Cache<String, Deque<Serializable>> cache) {
		this.cache = cache;
	}

}
