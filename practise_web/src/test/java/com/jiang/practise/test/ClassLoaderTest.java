package com.jiang.practise.test;

import java.util.Set;

import com.jiang.practise.frame.utils.ClassUtils;

public class ClassLoaderTest {

	
	public static void main(String[] args){
		
		Set<Class<?>> classSet = ClassUtils.getClassSet("com.jiang.practise.servlet");
		for (Class<?> class1 : classSet) {
			System.out.println("classPath = "+ class1.getCanonicalName());
		}
		
	}
}
