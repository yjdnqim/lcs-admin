package com.fh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.fh.util.JdbcUtil;


/**
 * @Description:传感器消息DAO
 * @Author wangwenwen
 * @Date 2017-10-19 上午9:38:04
 */
public class JDBCDao {
	
	
	
	
	
	/**
	 * 根据ID查询传感器信息
	 * @param SEN_ID
	 * @return
	 */
	public SensorBean findSensorById(String SEN_ID){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	//创建连接
		try {
		connection = JdbcUtil.createConnection();
	//创建sql
		String sql = " select SEN_ID,SEN_NAME,TERMINAL_ID,SEN_STATUS,SENTYPE_ID,COMMON_RATE,HIGHT_RATE,SEN_INFO from TB_SEN  where SEN_ID = ? ";
	//创建发送器
		preparedStatement = JdbcUtil.createPreparedStatement(connection, sql);
		preparedStatement.setString(1, SEN_ID);
		
		//利用发送器执行SQL并且获取结果集
			resultSet = preparedStatement.executeQuery();
			//遍历结果集
			while(resultSet.next()){
				SensorBean bean = new SensorBean();
				bean.setSEN_ID(resultSet.getString(1));
				bean.setSEN_NAME(resultSet.getString(2));
				bean.setTERMINAL_ID(resultSet.getString(3));
				bean.setSEN_STATUS(resultSet.getString(4));
				bean.setSENTYPE_ID(resultSet.getString(5));
				bean.setCOMMON_RATE(resultSet.getString(6));
				bean.setHIGHT_RATE(resultSet.getString(7));
				bean.setSEN_INFO(resultSet.getString(8));
				
				return bean;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			//关闭连接
			JdbcUtil.closeAll(connection, preparedStatement,resultSet );
		}
		
		return null;
	}
	
	
	/**
	 * 更新传感器数据最新数据
	 * @param bean
	 * @return
	 */
	public void updateSensor(SensorBean bean){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	//创建连接
		try {
		connection = JdbcUtil.createConnection();
		connection.setAutoCommit(false);
	//创建sql
		String sql = " update  TB_SEN set SEN_NAME = ?,TERMINAL_ID = ?,COMMON_RATE = ?,HIGHT_RATE = ?,SEN_STATUS = ?,SENTYPE_ID = ?,SEN_INFO = ?,SEN_ID = SEN_ID where SEN_ID = ? ";
	//创建发送器
		preparedStatement = JdbcUtil.createPreparedStatement(connection, sql);
		preparedStatement.setString(1, bean.getSEN_NAME());
		preparedStatement.setString(2, bean.getTERMINAL_ID());
		preparedStatement.setString(3, bean.getCOMMON_RATE());
		preparedStatement.setString(4, bean.getHIGHT_RATE());
		preparedStatement.setString(5, bean.getSEN_STATUS());
		preparedStatement.setString(6, bean.getSENTYPE_ID());
		preparedStatement.setString(7, bean.getSEN_INFO());
		preparedStatement.setString(8, bean.getSEN_ID());
		
		//利用发送器执行SQL并且获取结果集
		preparedStatement.executeUpdate();
		
		connection.commit(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//关闭连接
			JdbcUtil.closeAll(connection, preparedStatement,resultSet );
		}
	}
	
	
	
	//保存传感器历史数据
	public void saveSenSorInfo(SenSorInfo bean){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	//创建连接
		try {
		connection = JdbcUtil.createConnection();
		connection.setAutoCommit(false);
	//创建sql
		String sql = " insert into TB_SENMONITOR_INFO(MONITOR_INFO,MONITOR_TIME,SEN_ID,SEN_STATUS,MON_ID) values (?,?,?,?,?) ";
	//创建发送器
		preparedStatement = JdbcUtil.createPreparedStatement(connection, sql);
		preparedStatement.setString(1, bean.getMONITOR_INFO());
		preparedStatement.setLong(2, bean.getMONITOR_TIME());
		preparedStatement.setString(3, bean.getSEN_ID());
		preparedStatement.setString(4, bean.getSEN_STATUS());
		preparedStatement.setString(5, bean.getMON_ID());
		//利用发送器执行SQL并且获取结果集
		preparedStatement.execute();
		connection.commit(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//关闭连接
			JdbcUtil.closeAll(connection, preparedStatement,resultSet );
		}
	}
	
	
	
	
	
	
	

}
