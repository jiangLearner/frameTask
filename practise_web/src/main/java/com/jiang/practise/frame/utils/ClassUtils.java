package com.jiang.practise.frame.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {
	
	public static ClassLoader getClassLoader(){
		return Thread.currentThread().getContextClassLoader();
	}

	public static Class<?> loadClass(String className){
		Class<?> cls = null;
		try {
			cls = Class.forName(className,false,getClassLoader());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cls;
	}
	
	public static Set<Class<?>> getClassSet(String packageName){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		try {
			Enumeration<URL> resources = getClassLoader().getResources(packageName.replace(".", "/"));
			while (resources.hasMoreElements()) {
				URL url = (URL) resources.nextElement();
				if(url != null){
					String protocol = url.getProtocol();
					if(protocol.equals("file")){
						String packagePath = url.getPath();
						addClass(classes,packagePath,packageName);
					}else if(protocol.equals("jar")){
						JarURLConnection openConnection = (JarURLConnection) url.openConnection();
						if(openConnection != null){
							JarFile jarFile = openConnection.getJarFile();
							if(null != jarFile){
								Enumeration<JarEntry> entries = jarFile.entries();
								while (entries.hasMoreElements()) {
									 JarEntry nextElement = entries.nextElement();
									 String name = nextElement.getName();
									 if(name.endsWith(".class")){
										 String className = name.substring(0, name.lastIndexOf("."));
										 doAddClass(classes,className);
									 }
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return classes;
	}

	private static void doAddClass(Set<Class<?>> classes, String className) {
		// TODO Auto-generated method stub
		Class<?> loadClass = loadClass(className);
		classes.add(loadClass);
	}

	private static void addClass(Set<Class<?>> classes, String packagePath,
			String packageName) {
		// TODO Auto-generated method stub
		File[] listFiles = new File(packagePath).listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return (pathname.isFile() && pathname.getName().endsWith(".class")) || pathname.isDirectory();
			}
		});
		if(null != listFiles){
			for (File file : listFiles) {
				String fileName = file.getName();
				if(file.isFile()){
					String className = fileName.substring(0,fileName.lastIndexOf("."));
					className = packageName+ "." + className;
					doAddClass(classes, className);
				} else {
					String subPackagePath = fileName;
					subPackagePath = packagePath+ "/" + subPackagePath;
					String subPackageName = fileName;
					subPackageName =  packageName+ "." + subPackageName;
					addClass(classes, subPackagePath, subPackageName);
				}
			}
		}
	}
}
