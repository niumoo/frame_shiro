package net.codingme.ssm.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.codingme.ssm.exception.CustomException;
import net.codingme.ssm.service.SysService;

/**
 * <p>Description：登陆前端控制器</p>
 * 
 * @author  NiuJinpeng
 * @date    2018年1月15日上午8:44:59
 */
@Controller
public class LoginController {

	@Autowired
	private SysService sysService;

	/**
	 * 用户登陆,登陆的提交地址和applicationContext-shiro.xml中配置的loginurl一致
	 * 
	 * @param randomcode
	 * @param usercode
	 * @param password
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request) throws Exception {

		// 如果登陆失败，则从request中获取到认证异常信息，shiroLoginFailure就是shiro
		// 异常类的全限定名,注意，这里使用getAttribute来获取而不是用getParameter
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		
		// 根据shiro返回的异常类路径判断抛出指定异常信息
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				throw new CustomException("账号不存在");
				
			} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				throw new CustomException("用户名/密码错误");
				
			} else if("randomCodeError".equals(exceptionClassName)) {
				throw new CustomException("验证码不正确");
				
			}else {
				// 若以上异常都不匹配则提示未知错误
				throw new Exception();
			}
		}
		// 这个方法不处理登陆成功，shiro认证成功会自动的跳转到上一个请求路径
		// 如果登陆失败，则还是跳转到login页面
//		return "redirect:first.action";
		return "login";
	}

	
	/**
	 * 并发登陆控制，别处登陆后强制退出的重定向地址
	 */
	@RequestMapping("/forceLogout")
	public void forceLogout() {
		try {
			throw new CustomException("您的账号已在别处登陆！");
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}
	// 用户退出,这里注释掉，使用shiro的logout代替
	/*
	 * @RequestMapping("/logout") public String logout(HttpSession session) throws
	 * Exception {
	 * 
	 * // session失效 session.invalidate(); // 重定向到商品查询页面 return
	 * "redirect:/first.action"; }
	 */

}
