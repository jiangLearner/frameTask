package org.pactise.common.dbcp;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectUtils {

	private static Logger logger = LoggerFactory.getLogger(ConnectUtils.class);

	public static PreparedStatement getPrepareStatement(Connection con,
			String sql, Object[] params) throws SQLException {
		PreparedStatement pre = null;
		try {
			pre = con.prepareStatement(sql);
			int i = 1;
			if (params != null) {
				for (Object param : params) {
					pre.setObject(i++, param);
				}
			}
		} catch (SQLException e) {
			logger.error("设置参数出错", e);
			throw e;
		}
		return pre;
	}

	public static List getObjectList(ResultSet set, Class cls) throws Exception {
		List retList = new ArrayList();
		ResultSetMetaData metaData = set.getMetaData();
		int columnCount = metaData.getColumnCount();

		while (set.next()) {
			Object obj = cls.newInstance();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				// String columnName = metaData.getColumnName(i);
				String javaFiledName = changeJavaFiledName(columnName);
				Field field = getField(cls, javaFiledName);
				if (field != null) {
					Object valObj = getValue(columnName, set, field.getType());
					field.set(obj, valObj);
				}
			}
			retList.add(obj);
		}
		return retList;
	}

	private static Object getValue(String columnName, ResultSet set,
			Class<?> fieldType) throws SQLException {
		Object ret = null;
		Object val = set.getObject(columnName);
		if (val != null) {
			if (val.getClass().getName().equals("java.sql.Timestamp")) {
				Timestamp timestamp = (Timestamp) val;
				ret = new Date(timestamp.getTime());
			} else {
				ret = val;
			}
			String typeName = fieldType.getName();
			if (typeName.equals("java.lang.Integer")) {
				if (val instanceof Boolean) {
					if ((Boolean) val) {
						ret = Integer.valueOf(1);
					} else {
						ret = Integer.valueOf(0);
					}
				} else {
					ret = Integer.valueOf(String.valueOf(ret));
				}
			}

			if (typeName.equals("java.lang.Long")) {
				ret = Long.valueOf(String.valueOf(ret));
			}

			if (typeName.equals("java.lang.String")) {
				ret = String.valueOf(val);
			}

		}
		return ret;
	}

	private static String changeJavaFiledName(String columnName) {
		return changeJavaFiledName(columnName, 0);
	}

	private static String changeJavaFiledName(String columnName, int leve) {
		StringBuffer buffer = new StringBuffer();
		if (leve < 10) {
			if (columnName != null) {
				int i = columnName.indexOf("_");
				if (i != -1) {
					String frontStr = columnName.substring(0, i);
					String afterStr = columnName.substring(i + 1,
							columnName.length());
					afterStr = afterStr.replaceFirst(afterStr.substring(0, 1),
							afterStr.substring(0, 1).toUpperCase());
					buffer.append(frontStr).append(
							changeJavaFiledName(afterStr, leve + 1));
				} else {
					buffer.append(columnName);
				}
			}
		}
		return buffer.toString();
	}

	public static Field getField(Class<?> cls, String fieldName) {
		Field ret = null;
		Class<?> tmp = cls;
		while (!"java.lang.Object".equals(tmp.getName())) {
			try {
				ret = tmp.getDeclaredField(fieldName);
				if (ret != null) {
					ret.setAccessible(true);
					break;
				}
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {
			}
			tmp = tmp.getSuperclass();
		}
		return ret;
	}

}
