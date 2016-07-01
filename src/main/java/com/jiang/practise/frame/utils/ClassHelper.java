package com.jiang.practise.frame.utils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.jiang.practise.frame.annotation.FrameActionAnnonation;

public class ClassHelper {

	private static final Set<Class<?>> CLASS_SET; 
	
	static {
		String basePackage = "";
		CLASS_SET = ClassUtils.getClassSet(basePackage);
	}

	public static Set<Class<?>> getClassSet() {
		return CLASS_SET;
	}
	
	
	public static Set<Class<?>> getControllerClassSet(){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (Class<?> cls : CLASS_SET) {
			FrameActionAnnonation annotation = cls.getAnnotation(FrameActionAnnonation.class);
			if(null != annotation){
				classes.add(cls);
			}
		}
		return classes;
	}
	
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (Class<?> cls : CLASS_SET){
			if(cls.isAnnotationPresent(annotationClass)){
				classes.add(cls);
			}
		}
		return classes;
	}
	
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (Class<?> cls : CLASS_SET){
			if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
				classes.add(cls);
			}
		}
		return classes;
	}
	
}
