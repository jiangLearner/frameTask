package com.jiang.practise.frame.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AspectProxy implements Proxy{

	private Logger logger = LoggerFactory.getLogger(AspectProxy.class);
	
	@Override
	public Object doProxy(ProxyChain proxyChain) {
		// TODO Auto-generated method stub
		Object result = null;
		
		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		
		begin();
		try{
			if(intercept(cls,method,params)){
				before(cls,method,params);
				result = proxyChain.doProxyChain();
				after(cls,method,params);
			}else{
				result = proxyChain.doProxyChain();
			}
		} catch(Exception e){
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			end();
		}
		
		
		return result;
	}

	public void end() {
		// TODO Auto-generated method stub
		
	}
	
	public void begin() {
		// TODO Auto-generated method stub
		
	}

	public abstract boolean intercept(Class<?> cls, Method method, Object[] params);
	public abstract void before(Class<?> cls, Method method, Object[] params);
	public abstract void after(Class<?> cls, Method method, Object[] params);

}
