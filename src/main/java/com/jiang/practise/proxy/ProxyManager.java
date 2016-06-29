package com.jiang.practise.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyManager {
	
	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {

			@Override
			public Object intercept(Object arg0, Method arg1, Object[] arg2,
					MethodProxy arg3) throws Throwable {
				// TODO Auto-generated method stub
				return new ProxyChain(targetClass,arg0,arg1,arg3,arg2,proxyList).doProxyChain();
			}
		
		});
		
	}

}
