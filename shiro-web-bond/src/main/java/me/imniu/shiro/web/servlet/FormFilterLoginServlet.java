package me.imniu.shiro.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;

/**
 * Description:表单验证方式
 *
 * @author NiuJinpeng
 * @date   2018年1月8日下午4:49:19
 */
@WebServlet(name = "formFilterLoginServlet", urlPatterns = "/formfilterlogin")
public class FormFilterLoginServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(FormFilterLoginServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorClassName = (String)req.getAttribute("shiroLoginFailure");
        
        logger.info("开始尝试进行formFilterLogin登录...............");

        if(UnknownAccountException.class.getName().equals(errorClassName)) {
            req.setAttribute("error", "用户名/密码错误");
        } else if(IncorrectCredentialsException.class.getName().equals(errorClassName)) {
            req.setAttribute("error", "用户名/密码错误");
        } else if(errorClassName != null) {
            req.setAttribute("error", "未知错误：" + errorClassName);
        }

        req.getRequestDispatcher("/WEB-INF/jsp/formfilterlogin.jsp").forward(req, resp);
    }
}