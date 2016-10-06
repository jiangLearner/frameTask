package org.practise.common.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

	private static String proerty_file_path = "";
	private static Properties properties = new Properties();
	static {
		InputStream inputStream = Object.class.getResourceAsStream(proerty_file_path);
		try{
			properties.load(inputStream);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getPropertyValue(String proeprtyName,Class<T> ObjectClass){
		return (T) properties.get(proeprtyName);
	}
}
