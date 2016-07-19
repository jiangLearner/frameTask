package com.jiang.practise.frame.utils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jiang.practise.frame.annotation.FrameUrlAnnonation;

public class ClassHelper {

	private static final Set<Class<?>> CLASS_SET; 
	
	
	static {
		String basePackage = "com.jiang.practise.servlet";
		CLASS_SET = ClassUtils.getClassSet(basePackage);
	}

	public static void main(String[] args){
		for (Class<?> class1 : CLASS_SET) {
			System.out.println(class1.getName());
		}
	}
	
	public static Set<Class<?>> getClassSet() {
		return CLASS_SET;
	}
	
	
	public static Set<Class<?>> getControllerClassSet(){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (Class<?> cls : CLASS_SET) {
			FrameUrlAnnonation annotation = cls.getAnnotation(FrameUrlAnnonation.class);
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
