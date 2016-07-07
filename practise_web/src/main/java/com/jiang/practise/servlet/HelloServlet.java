package com.jiang.practise.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jiang.practise.frame.annotation.FrameActionAnnonation;
import com.jiang.practise.frame.annotation.FrameUrlAnnonation;

@FrameUrlAnnonation(urlPattern="/hello")
public class HelloServlet   {


	@FrameActionAnnonation(actionName="helloWorld1")
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		 System.out.println("进入servlet2222222222222");
		 try {
			PrintWriter writer = resp.getWriter();
			writer.print("进入servlet2222222222222");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FrameActionAnnonation(actionName="helloWorld2")
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		 System.out.println("进入servlet333333333");
	 }
	
}
