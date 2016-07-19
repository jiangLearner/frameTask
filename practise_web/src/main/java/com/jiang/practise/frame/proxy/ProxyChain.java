package com.jiang.practise.frame.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

public class ProxyChain {

	private Class<?> targetClass;
	private Object targetObject;
	private Method targetMethod;
	private MethodProxy methodProxy;
	private Object[] methodParams;
	private int proxyIndex = 0;
	private List<Proxy> proxyList = new ArrayList<Proxy>();
	
	public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList){
		this.methodParams = methodParams;
		this.methodProxy = methodProxy;
		this.setTargetClass(targetClass);
		this.setTargetMethod(targetMethod);
		this.targetObject = targetObject;
		this.proxyList = proxyList;
	};
	
	public Object doProxyChain() throws Throwable {
		Object methodResult;
		if(proxyIndex < proxyList.size()){
			methodResult = proxyList.get(proxyIndex++).doProxy(this);
		}else{
			methodResult = methodProxy.invokeSuper(targetObject, methodParams);
		}
		return methodResult;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public void setTargetMethod(Method targetMethod) {
		this.targetMethod = targetMethod;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(Object[] methodParams) {
		this.methodParams = methodParams;
	}
	
	
}
