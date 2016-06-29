package com.jiang.practise;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2,
			MethodProxy arg3) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("前置代理");
		// 通过代理类调用父类中的方法
		Object result = arg3.invokeSuper(arg0, arg2);
		System.out.println("后置代理");
		return result;
	}

}
