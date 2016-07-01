package com.jiang.practise.frame.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jiang.practise.frame.annotation.Aspect;
import com.jiang.practise.frame.proxy.AspectProxy;
import com.jiang.practise.frame.proxy.Proxy;

public class AopHelper {
	
	
	public static Set<Class<?>> createTargetClassSet(Aspect aspect){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		Class<? extends Annotation> annotation = aspect.value();
		if(annotation != null && !annotation.equals(Aspect.class)){
			classes.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return classes;
	}

	private static Map<Class<?>,Set<Class<?>>> createProxyMap() {
		Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for (Class<?> cls : proxyClassSet) {
			if(cls.isAnnotationPresent(Aspect.class)){
				Aspect aspect = cls.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				proxyMap.put(cls, targetClassSet);
			}
		}
		return proxyMap;
	}
	
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception{
		
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
			Class<?> proxyClass = entry.getKey();
			Set<Class<?>> targetClassSet = entry.getValue();
			for (Class<?> cls : targetClassSet) {
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if(targetMap.containsKey(cls)){
					targetMap.get(cls).add(proxy);
				}else{
					List<Proxy> list = new ArrayList<Proxy>();
					list.add(proxy);
					targetMap.put(cls, list);
				}
			}
		}
		return targetMap;
	}
}
