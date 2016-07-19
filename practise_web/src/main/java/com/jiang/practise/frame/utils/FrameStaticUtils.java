package com.jiang.practise.frame.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jiang.practise.frame.annotation.FrameActionAnnonation;
import com.jiang.practise.frame.annotation.FrameUrlAnnonation;


public class FrameStaticUtils {
	
	public static String URL_FILE_TYPE = "file";
	public static String URL_JAR_TYPE = "jar";
	
	public static Map<String,Object> urlToClassMap = new HashMap<String,Object>();
	
	public static Map<String,Method> actionToMethod = new HashMap<String,Method>();
	
	private static final Map<Class<?>, Object> proxyObject;


	static {
		Set<Class<?>> classes = ClassHelper.getControllerClassSet();
		proxyObject = AopHelper.initProxyObject();
		for (Class<?> cls : classes) {
			dealUrlClass(cls);
		}
		//dealUrlClass(com.jiang.practise.servlet.HelloServlet.class);
		
	}
	
	public static Object getRequestClass(String request){
		request = new StringBuilder(request).reverse().toString();
		String[] path = request.split("/",2);
		request = new StringBuilder(path[1]).reverse().toString();
		return urlToClassMap.get(request);
	}
	
	
	public static Method getRequestMethod(String request){
		String action = new StringBuilder(request).reverse().toString().split("/",2)[0];
		action = new StringBuilder(action).reverse().toString();
		return actionToMethod.get(action);
	}
	
	/*public static void scanPackage(String packageName){
		String packageDirName = packageName.replace('.', '/');
		List<String> fileNames = null; 
		URL url = Thread.currentThread().getContextClassLoader().getResource(packageDirName);
		if(url != null){
			String type = url.getProtocol();
			if(type.equals(URL_FILE_TYPE)){
	//			 fileNames = getClassNameByFile(url.getPath(), null, childPackage);  
            } else if (type.equals("jar")) {  
       //         fileNames = getClassNameByJar(url.getPath(), childPackage);  
            }  
		}
	}*/
	
	public static void dealUrlClass(Class<?> destClass){
		Annotation[] annotations = destClass.getAnnotations();
		if(null != annotations){
			for (int i = 0; i < annotations.length; i++) {
				Annotation annotation = annotations[i];
				if(annotation instanceof FrameUrlAnnonation){
					try {
						Object object = proxyObject.get(destClass);
						if(null == object){
							object = destClass.newInstance();
						}
						urlToClassMap.put(((FrameUrlAnnonation) annotation).urlPattern(), object);
						Method[] methods = destClass.getMethods();
						for (int j = 0; j < methods.length; j++) {
							Method method = methods[j];
							dealMethodClass(method);
						}
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
	}
	
	private static void dealMethodClass(Method method){
		Annotation[] annotations = method.getAnnotations();
		if(null != annotations){
			for (int i = 0; i < annotations.length; i++) {
				Annotation annotation = annotations[i];
				if(annotation instanceof FrameActionAnnonation){
					actionToMethod.put(((FrameActionAnnonation) annotation).actionName(), method);
				}
			}
		}
	}
}
