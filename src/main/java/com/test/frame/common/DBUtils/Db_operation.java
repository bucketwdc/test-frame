package com.test.frame.common.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**   
 *    
 * 类  名  称：Db_operation   
 * 类  描  述：   数据库的增删查改操作
 * 创  建  人：wdc   
 * 创建时间：2015年2月12日 上午10:25:05   
 * 修  改  人：wdc   
 * 修改时间：2015年2月12日 上午10:25:05   
 * 修改备注：   
 * @version    
 *    
 */
public class Db_operation extends OracleConnect {

	public Db_operation(String tableName) {
		super(tableName);
	}

	public static OracleConnect oOracleConnect = null;
	public static ResultSet oResultSet = null;
	private static PreparedStatement oPreparedStatement = null;
	public static Connection conn = null;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Db_operation.class);

	protected static Connection getConn(String dbName) {
		oOracleConnect = new OracleConnect(dbName);
		conn = OracleConnect.conn;
		return conn;
	}

	protected static ResultSet dbSelect(String dbName, String dbSql,
			String dbConfigKey) {
		try {
			oPreparedStatement = getConn(dbName).prepareStatement(dbSql);
			oResultSet = oPreparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			OracleConnect.closeDbConn();
			logger.error("dbSelect 查询----数据库操作出错,请检查数据库配置信息.\r\n  ");
		}
		return oResultSet;

	}

	protected static int dbUpdate(String dbName, String dbSql,
			String dbConfigKey) {
		// TODO Auto-generated method stub
		int res = 0;
		try {
			oPreparedStatement = getConn(dbName).prepareStatement(dbSql);
			res = oPreparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			OracleConnect.closeDbConn();
			logger.error("dbUpdate 更新操作----数据库操作出错,请检查数据库配置信息.\r\n  ");
		}
		return res;
	}

	/**   
	*    
	* 方法名称：getDbColumnName   
	* 方法描述：  获取表结构
	* 参数列表：@param dbName
	* 参数列表：@param dbSql
	* 参数列表：@param object
	* 返  回  值：void 
	* 创  建  人：wdc   
	* 创建时间：2015年2月12日 下午4:04:02   
	* 修  改  人：wdc   
	* 修改时间：2015年2月12日 下午4:04:02   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/
	protected static void getDbColumnName(String dbName, String dbSql,
			Object object) {
		// TODO Auto-generated method stub
		ResultSetMetaData metal;
		try {
			metal = oResultSet.getMetaData();
			int i = metal.getColumnCount();
			for (int j = 1; j <= i; j++) {
				logger.info(metal.getColumnName(j));
			}
			logger.info("\r\n");
		} catch (SQLException e) {
			logger.error("数据库结果遍历异常，请检查数据!");
		}

	}

	/**   
	*    
	* 方法名称：iteratesResultSet   
	* 方法描述：  根据ResultSet的数据进行结果输出遍历，主要是用于结果检查
	* 参数列表：@param oResultSet
	* 返  回  值：void 
	* 创  建  人：wdc   
	* 创建时间：2015年2月12日 下午3:01:58   
	* 修  改  人：wdc   
	* 修改时间：2015年2月12日 下午3:01:58   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/
	protected static void iteratesResultSet(ResultSet oResultSet) {
		ResultSetMetaData metal;
		String okey = null ;
		String val = null;
		long vall;
		try {
			metal = oResultSet.getMetaData();
			int i = metal.getColumnCount();
			while (oResultSet.next()) {
				for (int j = 1; j <= i; j++) {
					okey = metal.getColumnName(j);
					String rs = metal.getColumnClassName(j);
			        //format的格式可以任意  
					if(rs.equals("java.sql.Timestamp")){
						logger.info("当前返回的参数内容是： "+okey + ":" + oResultSet.getLong(j));
					}
					else if(rs.equals("java.sql.Date")){
						vall=oResultSet.getLong(j);
						logger.info("当前返回的参数内容是： "+okey + ":" + vall);
					}else{
					 	val = oResultSet.getString(j);
					 	logger.info("当前返回的参数内容是： "+okey + ":" + val);
					 	}
					} 
			}
			logger.info("\r\n");
		} catch (SQLException e) {
			logger.error("数据库结果遍历异常，请检查数据---iteratesResultSet!");
		}
	}

	/**   
	*    
	* 方法名称：getResultMap   
	* 方法描述：  将ResultSet转换成Map
	* 参数列表：@param oResultSet
	* 参数列表：@return
	* 返  回  值：Map<String,String> 
	* 创  建  人：wdc   
	* 创建时间：2015年2月12日 下午4:13:22   
	* 修  改  人：wdc   
	* 修改时间：2015年2月12日 下午4:13:22   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/
	protected static List<Map<String, String>> getResultMap(
			ResultSet oResultSet) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			ResultSetMetaData rsmd = oResultSet.getMetaData();
			int count = rsmd.getColumnCount();
			while (oResultSet.next()) {
				Map<String, String> hm = new HashMap<String, String>();
				for (int i = 1; i <= count; i++) {
					String key = rsmd.getColumnLabel(i);
					String value = null ;
					String rs = rsmd.getColumnClassName(i);
					if(rs.equals("java.sql.Timestamp") || rs.equals("java.sql.Date")){
						value= String.valueOf(oResultSet.getLong(i));
					}else{
						value=oResultSet.getString(i);
					}
					logger.info("当前返回的参数内容是： "+key + ":" + value);
					hm.put(key, value);
				}
				list.add(hm);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("将ResultSet转换成Map失败:转换出现异常.\r\n" + e);
		}

		return list;
	}

}
