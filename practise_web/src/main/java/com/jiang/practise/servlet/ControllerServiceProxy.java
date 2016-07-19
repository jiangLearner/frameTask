package com.jiang.practise.servlet;

import java.lang.reflect.Method;

import com.jiang.practise.frame.annotation.Aspect;
import com.jiang.practise.frame.annotation.FrameUrlAnnonation;
import com.jiang.practise.frame.proxy.AspectProxy;

@Aspect(FrameUrlAnnonation.class)
public class ControllerServiceProxy extends AspectProxy{

	@Override
	public boolean intercept(Class<?> cls, Method method, Object[] params) {
		// TODO Auto-generated method stub
		System.out.println("判断是否应该执行HelloService服务");
		return true;
	}

	@Override
	public void before(Class<?> cls, Method method, Object[] params) {
		// TODO Auto-generated method stub
		System.out.println("执行before()服务");
	}

	@Override
	public void after(Class<?> cls, Method method, Object[] params) {
		// TODO Auto-generated method stub
		System.out.println("执行after()服务");
	}

}
