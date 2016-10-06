package org.pactise.common.dbcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.practise.common.property.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBaseConnection {

	private static String driver = PropertyUtils.getPropertyValue("dbcp_driver", String.class);
	private static String url = PropertyUtils.getPropertyValue("dbcp_url", String.class);
	private static String user = PropertyUtils.getPropertyValue("dbcp_user", String.class);
	private static String password = PropertyUtils.getPropertyValue("dbcp_password", String.class);
	private static Logger logger = LoggerFactory.getLogger(DataBaseConnection.class);
	
	public static Connection getConnection(){
		try {
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeCon(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("数据库连接关闭出错", e);
		}
	}
}
