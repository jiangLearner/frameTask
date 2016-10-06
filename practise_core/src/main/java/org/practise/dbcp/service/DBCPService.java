package org.practise.dbcp.service;

import java.util.List;

public interface DBCPService<T> {

	public List<T> selectAll(String sql, T t);
	
	public void save(String sql, Object[] params);
	
	public int update(String sql, Object[] params);
	
	public void delete(String sql,Object[] params);
	
}
