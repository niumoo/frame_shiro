package net.codingme.ssm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.codingme.ssm.exception.CustomException;
import net.codingme.ssm.po.ActiveUser;
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
	 * 用户登陆
	 * 
	 * @param randomcode
	 *            验证码
	 * @param usercode
	 *            用户身份
	 * @param passsowrd
	 *            密码
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(String randomcode, String usercode, String password, HttpSession session) throws Exception {
		// 校验验证码，防止恶意测试
		// 从session中获取验证码
		String validateCode = (String) session.getAttribute("validateCode");

		// 输入的验证码和session中的验证码进行对比
		if (!randomcode.equals(validateCode)) {
			throw new CustomException("验证码输入错误！");
		}

		// 调用service校验用户账号和密码的正确性
		ActiveUser activeUser = sysService.authenticad(usercode, password);

		// 如果service校验通过，将用户身份记录到session
		session.setAttribute("activeUser", activeUser);
		
		// 重定向到商品查询页面
		return "redirect:/first.action";
	}

	// 用户退出
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception {

		// session失效
		session.invalidate();
		// 重定向到商品查询页面
		return "redirect:/first.action";
	}

}
