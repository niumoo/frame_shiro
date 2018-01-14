package net.codingme.ssm.controller.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.codingme.ssm.po.ActiveUser;
import net.codingme.ssm.util.ResourcesUtil;

/**
 * <p>Description：编写认证拦截器实现用户的认证拦截</p>
 * 
 * @author  NiuJinpeng
 * @date    2018年1月15日上午9:41:54
 */
public class LoginInterceptor  implements HandlerInterceptor{
	
	
	/**
	 * 用于用户认证校验，用户权限 校验
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// 获取请求的url
		String url = request.getRequestURI();
		
		// 获取配置，判断是否为公开地址
		List<String> openUrls = ResourcesUtil.gekeyList("anonymousURL");
		for (String openUrl : openUrls) {
			// 如果是公开地址则放行
			if(url.indexOf(openUrl) >= 0 ) {
				return true;
			}
		}
		
		// 判断用户身份在session中是否存在，存在则放行
		HttpSession session = request.getSession();
		ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
		if( activeUser != null) {
			return true;
		}
		
		
		// 若执行到此处，则跳转到登陆页面进行身份认证
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		return false;
	}

	/**
	 * 在进入handle之后返回modelAndView之前执行
	 * 应用场景：在返回movelAndView时候加载公共资源，如菜单导航之类
	 * 也可以指定同意的视图
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	/**
	 * 执行Handler完成执行此方法
	 * 应用场景：统一异常处理，统一日志处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
}
