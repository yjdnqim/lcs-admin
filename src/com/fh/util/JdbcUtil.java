package com.fh.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 调用JDBC的公共类
 * @author Administrator
 *
 */
public class JdbcUtil {

	//声明一些变量存放配置信息
	private static String className = "";
	private static String url = "";
	private static String user = "";
	private static String password = "";

	/**
	 * 在类被加载的时候执行，而且只执行一次
	 */
	static {
		try {
			//获取配置文件信息
			Properties properties = new Properties();
			properties.load(JdbcUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties"));
			//开始获取配置的初始值
			String datatype = properties.getProperty("datatype");
			className = properties.getProperty("driverClassName");
			url = properties.getProperty("url");
			user = properties.getProperty("username");
			password = properties.getProperty("password");
			//加载驱动
			Class.forName(className);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建一个连接
	 * @return
	 */
	public static Connection createConnection() {
		//声明一个连接
		Connection connection = null;
		try {
			//获取一个连接
			connection = DriverManager.getConnection(url, user, password);
			//将事务的自动提交关闭，每次都需要手动提交
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.err.println("创建连接失败,请检查连接参数:url[" + url + "]user[" + user + "]password[" + password + "]");
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 创建发送器
	 * @param connection
	 * @return
	 */
	public static Statement createStatement(Connection connection) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	/**
	 * 创建预处理发送器
	 * @param connection
	 * @param sql
	 * @return
	 */
	public static PreparedStatement createPreparedStatement(Connection connection, CharSequence sql) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	/**
	 * 关闭连接释放资源
	 * @param connection
	 * @param statement
	 * @param resultSet
	 */
	public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将结果集合里面的数据存放至对象
	 * @param resultSet
	 * @param clazz
	 */
	public static <T> List<T> resultSet2List(ResultSet resultSet, Class<T> clazz) {
		//声明一个List
		List<T> list = new ArrayList<T>();
		try {
			//获取当前类所有的属性
			Field[] dfields = clazz.getDeclaredFields();
			//首先遍历resultSet
			while (resultSet.next()) {
				//创建一个对象
				T bean = clazz.getConstructor().newInstance();
				//遍历属性
				for (int i = 0; i < dfields.length; i++) {
					//获取属性的名字
					String fieldName = dfields[i].getName();
					//获取属性对应的值
					Object fieldValue = resultSet.getObject(fieldName);
					//获取属性的set方法
					String methodName = "set" + fieldName.toUpperCase().substring(0, 1) + fieldName.substring(1);
					//获取方法
					Method setMethod = clazz.getMethod(methodName, dfields[i].getType());
					//要将值设置给对象
					setMethod.invoke(bean, fieldValue);
				}
				//然后将对象添加到List
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 将结果集合里面的数据存放至对象
	 * @param resultSet
	 * @param clazz
	 */
	public static <T> T resultSet2bean(ResultSet resultSet, Class<T> clazz) {
		//声明一个List
		List<T> list = resultSet2List(resultSet, clazz);
		if (list != null && list.size() >= 1) {
			return list.get(0);
		}
		return null;
	}

}
