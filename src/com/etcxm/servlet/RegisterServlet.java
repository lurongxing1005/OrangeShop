package com.etcxm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etcxm.dao.UserDao;
import com.etcxm.entity.User;
import com.etcxm.utils.StringUtils;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	/**
	 *  	
	 */
	private static final long serialVersionUID = 1L;
	private UserDao dao = new UserDao();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String passwordRepeat = req.getParameter("passwordRepeat");
		System.out.println("用户名="+username+",密码="+password+",重复密码="+passwordRepeat);
		try {
			
			User user = dao.checkRegister(username);
			
			if (!StringUtils.hasLength(username)||!StringUtils.hasLength(password)) { // 不能为空
				req.setAttribute("errorMsg", "请填写完整!");
				req.getRequestDispatcher("/home/register.jsp").forward(req, resp);
				return;
			} 
			if (user != null) { // 用户名已被使用
				req.setAttribute("errorMsg", "该用户名已存在，请重新输入!");
				req.getRequestDispatcher("/home/register.jsp").forward(req, resp);
				return;
			} 
			if (!password.equals(passwordRepeat)) { // 重复密码不同
				req.setAttribute("errorMsg", "两次密码不一样，请重新输入!");
				req.getRequestDispatcher("/home/register.jsp").forward(req, resp);
				return;
			} 
			
			// 合法，允许注册--添加至数据库
				user = new User(null, username, password);
				boolean flag = dao.addUser(user);
				if(flag){ //注册成功
					resp.sendRedirect("/shoppingPro/home/login.jsp");
					
				
			}
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
