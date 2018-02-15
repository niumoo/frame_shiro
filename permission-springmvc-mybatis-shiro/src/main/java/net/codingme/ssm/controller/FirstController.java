package net.codingme.ssm.controller;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.codingme.ssm.po.ActiveUser;


@Controller
public class FirstController {
	
	//系统首页
	@RequestMapping("/first")
	public String first(Model model)throws Exception{
		
		// 从shiro的session中获取activeUser
		Subject subject = SecurityUtils.getSubject();
		// 取出身份信息
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		model.addAttribute(activeUser);
		return "/first";
	}
	
	//欢迎页面
	@RequestMapping("/welcome")
	public String welcome(Model model)throws Exception{
		
		return "/welcome";
		
	}
}	
