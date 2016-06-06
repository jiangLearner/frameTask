package com.jiang.practise.servlet;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jiang.practise.frame.FramExcueInterface;

public class FrameExcuteTestServlete extends HttpServlet implements FramExcueInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Map<String,Method> urlToMethod= new HashMap<String,Method>();
	
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		String requestUrl = req.getPathInfo();
		
		Method method = getUrlMethod(requestUrl);
		if(null != method){
		//	method.get
		//	method.invoke(obj, args);
		}
	}



	private Method getUrlMethod(String requestUrl) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp){
		excute(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		excute(req, resp);
	 }
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		
		
		super.init();
	}
}
