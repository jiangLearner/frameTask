package org.practise.dbcp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.pactise.common.dbcp.ConnectUtils;
import org.pactise.common.dbcp.DataBaseConnection;

public class DBCPServiceImpl<T> implements DBCPService<T>{

	
	
	@Override
	public List<T> selectAll(String sql, T t) {
		// TODO Auto-generated method stub
		Connection connection = DataBaseConnection.getConnection();
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = ConnectUtils.getPrepareStatement(connection, sql, null);
			ResultSet executeResult = prepareStatement.executeQuery();
			List<T> objectList = ConnectUtils.getObjectList(executeResult, t.getClass());
			return objectList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConn(connection,prepareStatement);
		}
		return null;
	}

	@Override
	public void save(String sql, Object[] params) {
		// TODO Auto-generated method stub
		Connection connection = DataBaseConnection.getConnection();
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = ConnectUtils.getPrepareStatement(connection, sql, params);
			prepareStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConn(connection,prepareStatement);
		}
	}

	@Override
	public int update(String sql, Object[] params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String sql, Object[] params) {
		// TODO Auto-generated method stub
		
	}

	private void closeConn(Connection connection, PreparedStatement prepareStatement){
		try {
			connection.close();
			prepareStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
