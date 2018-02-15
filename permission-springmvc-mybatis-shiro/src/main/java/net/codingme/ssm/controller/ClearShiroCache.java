package net.codingme.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.codingme.ssm.shiro.CustomRealm;

/**
 * <p>Description：手动调用controller清空缓存信息</p>
 * 
 * @author  NiuJinpeng
 * @date    2018年1月16日下午2:33:57
 */
@Controller
public class ClearShiroCache {
	
	@Autowired
	private CustomRealm customRealm;
	
	@RequestMapping("/clearShiroCache")
	public String clearShiroCache() {
		
		//清除缓存，将来正常开发要在service调用customRealm.clearCached()进行清空
		customRealm.clearCached();
		return "success";
	}

}
