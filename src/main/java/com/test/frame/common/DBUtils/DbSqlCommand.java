package com.test.frame.common.DBUtils;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;


/**   
 *    
 * 类  名  称：DbSqlCommand   
 * 类  描  述：   主要是提供针对数据库的增删查改操作
 * 创  建  人：wdc   
 * 创建时间：2015年2月12日 上午10:30:50   
 * 修  改  人：wdc   
 * 修改时间：2015年2月12日 上午10:30:50   
 * 修改备注：   
 * @version    
 *    
 */
public class DbSqlCommand {

	/**   
	*    
	* 方法名称：dbUpdate   
	* 方法描述：  数据库表更新操作：根据表明和SQL语句
	* 参数列表：@param dbName
	* 参数列表：@param dbSql
	* 返  回  值：int 
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public static int dbUpdate(String dbName, String dbSql) {
		return Db_operation.dbUpdate(dbName, dbSql, null);
	}

	/**   
	*    
	* 方法名称：dbInsert   
	* 方法描述：  数据库表插入操作：根据表明和SQL语句
	* 参数列表：@param dbName
	* 参数列表：@param dbSql
	* 返  回  值：int 
	* 创  建  人：ligang13009   
	* 创建时间：2015年2月12日 下午4:05:59   
	* 修  改  人：ligang13009   
	* 修改时间：2015年2月12日 下午4:05:59   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public static int dbInsert(String dbName, String dbSql) {
		return Db_operation.dbUpdate(dbName, dbSql, null);
	}
	
	/**   
	*    
	* 方法名称：dbDelete   
	* 方法描述：   数据库表删除操作：根据表明和SQL语句
	* 参数列表：@param dbName
	* 参数列表：@param dbSql
	* 参数列表：@return
	* 返  回  值：int 
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public static int dbDelete(String dbName, String dbSql) {
		return Db_operation.dbUpdate(dbName, dbSql, null);
	}

	/**   
	*    
	* 方法名称：dbSelect   
	* 方法描述：  数据库表查询操作：根据表明和SQL语句
	* 参数列表：@param dbName
	* 参数列表：@param dbSql
	* 参数列表：@return
	* 返  回  值：Map 
	* 创  建  人：wdc   
	* 创建时间：2015年2月12日 下午4:05:15   
	* 修  改  人：wdc   
	* 修改时间：2015年2月12日 下午4:05:15   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public static List<Map<String, String>> dbSelect(String dbName, String dbSql) {
		 return Db_operation.getResultMap(select(dbName, dbSql)); 
	}
	
	/**   
	*    
	* 方法名称：select   
	* 方法描述：   数据库表查询操作：根据表明和SQL语句
	* 参数列表：@param dbName
	* 参数列表：@param dbSql
	* 参数列表：@return
	* 返  回  值：ResultSet 
	* 创  建  人：wdc   
	* 修  改  人：wdc   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public static ResultSet select(String dbName, String dbSql) {
		 return Db_operation.dbSelect(dbName, dbSql, null); 
	}
	/**   
	*    
	* 方法名称：getDbColumnName   
	* 方法描述：  获取数据库的表结构信息
	* 参数列表：@param dbName
	* 参数列表：@param dbSql
	* 返  回  值：void 
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public static void getDbColumnName(String dbName, String dbSql){
		Db_operation.getDbColumnName(dbName, dbSql, null);
	}
	/**   
	*    
	* 方法名称：iteratesResultSet   
	* 方法描述：  遍历ResultSet结果数据
	* 参数列表：@param oResultSet
	* 返  回  值：void 
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public static void iteratesResultSet(ResultSet oResultSet) {
		Db_operation.iteratesResultSet(oResultSet);
	}
	/**   
	*    
	* 方法名称：getResultMap   
	* 方法描述：  将ResultSet转换成Map
	* 参数列表：@param oResultSet
	* 参数列表：@return
	* 返  回  值：Map<String,String> 
	* @version  
	* @throws  
	*    
	*/ 
	public static List<Map<String, String>> getResultMap(ResultSet oResultSet) {
		return Db_operation.getResultMap(oResultSet);
	}
}
