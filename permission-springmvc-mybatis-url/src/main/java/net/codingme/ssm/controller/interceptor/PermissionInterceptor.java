package net.codingme.ssm.controller.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.codingme.ssm.po.ActiveUser;
import net.codingme.ssm.po.SysPermission;
import net.codingme.ssm.util.ResourcesUtil;

/**
 * <p>Description：授权拦截器，在认证拦截器之后执行</p>
 * 
 * @author  NiuJinpeng
 * @date    2018年1月15日上午10:28:52
 */
public class PermissionInterceptor implements HandlerInterceptor {

	/**
	 * 用户用户认证授权
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// 得到请求的url
		String url = request.getRequestURI();

		// 获取配置，判断是否为公开地址
		List<String> openUrls = ResourcesUtil.gekeyList("anonymousURL");
		for (String openUrl : openUrls) {
			// 如果是公开地址则放行
			if (url.indexOf(openUrl) >= 0) {
				return true;
			}
		}
		
		// 从配置文件中获取公共访问地址（只要认证通过都可以访问的地址）
		List<String> commonUrls = ResourcesUtil.gekeyList("commonURL");
		for (String commonUrl : commonUrls) {
			if(url.indexOf(commonUrl) >= 0) {
				return true;
			}
		}
		
		// 判断用户身份在session中是否存在，存在则放行
		HttpSession session = request.getSession();
		ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
		// 从session获取权限范围
		List<SysPermission> permissions = activeUser.getPermissions();
		for (SysPermission sysPermission : permissions) {
			// 获取权限url进行验证
			String permissionUrl = sysPermission.getUrl();
			if(url.indexOf(permissionUrl) >= 0) {
				return true;
			}
		}
		
		// 若执行到此处，则跳转到登陆页面进行身份认证
		request.getRequestDispatcher("/WEB-INF/jsp/refuse.jsp").forward(request, response);
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	

}
