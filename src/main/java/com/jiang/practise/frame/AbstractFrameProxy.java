package com.jiang.practise.frame;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


public abstract class AbstractFrameProxy implements MethodInterceptor{

	 private Object target; 
	
	 /** 
     * 创建代理对象 
     *  
     * @param target 
     * @return 
     */  
    public Object getInstance(Object target) {  
        this.target = target;  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(this.target.getClass());  
        // 回调方法  
        enhancer.setCallback(this);  
        // 创建代理对象  
        return enhancer.create();  
    }  
	
	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2,
			MethodProxy arg3) throws Throwable {
		// TODO Auto-generated method stub
		Object oo = null;
		if(excuteProxy(arg0,arg1,arg2)){
			beforExcute(arg0,arg1,arg2);
			oo = arg3.invokeSuper(arg0, arg2);
			afterExcute(arg0,arg1,arg2);
			
		}else{
	        oo = arg3.invokeSuper(arg0, arg2);
		}
        
		return oo;
	}

	protected abstract void afterExcute(Object object, Method method, Object[] parameters);

	protected abstract void beforExcute(Object object, Method method, Object[] parameters);

	protected abstract boolean excuteProxy(Object object, Method method, Object[] parameters);

}
