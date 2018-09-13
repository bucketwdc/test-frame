package com.test.frame.common.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.test.frame.common.logutil.TcRunLog;


 
/**   
*    
* 类  名  称：OracleConnect   
* 类  描  述：   主要是用来进行初始化数据库的配置以及数据库连接操作
* @version    
*    
*/ 
public class OracleConnect {

	public JdbcDriverTypeEnum oJdbcDriverTypeEnum = null;
	public static String dbUrl = null;// 连接串
	public static String dbNname = null;// 数据库访问用用户名
	public static String dbPassword = null;// 数据库访问用户对应密码
	public static String dbSchema = null;
	public static Connection conn = null;
	public static PreparedStatement stmt = null;
	public static ResultSet result = null;
	public  OracleConnect(String tableName){
		this(tableName, null);
	}
	/**
	*    
	* 方法名称：DBConn   
	* 方法描述：  加载数据连接设置，获取数据库类型。
	* 参数列表：
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public  OracleConnect(String tableName, String dbConfigKey){
		//读取数据库配置文件内容
		OracleDriverConfig oracleDriver = GetDbConfigInfo.oGetDbConfigInfo(
				tableName,dbConfigKey);
		//初始化数据库的配置信息
		initDBconfig(oracleDriver);
		//初始化数据库连接
		initDBConn();
		//检查数据库连接
		checkDbConn(tableName);
	}

	/**   
	*    
	* 方法名称：initDBconfig   
	* 方法描述：  初始化数据库配置，主要用来选择JDBC驱动，并动态加载对应的JDBC驱动
	* 参数列表：
	* 返  回  值：void 
	* @version  
	* @throws  
	*    
	*/ 
	public  void initDBconfig(OracleDriverConfig oracleDriver){
		dbUrl =oracleDriver.getDbUrl().toLowerCase();
		try {
			if (StringUtils.isBlank(dbUrl)) {
				throw new Exception("获取到的数据库配置URL地址为空，请检查数据库配置文件是否配置.");
		    }
			if(dbUrl.contains("mysql")){
				oJdbcDriverTypeEnum = JdbcDriverTypeEnum.MYSQL;
			}
	        if(dbUrl.contains("oracle")){
	        	oJdbcDriverTypeEnum = JdbcDriverTypeEnum.ORACLE;
			}
	        // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来
	        Class.forName(oJdbcDriverTypeEnum.getDriverClass());//动态加载JDBC驱动
	        //如果没有配置好jdbc的jar包，会导致java.lang.ClassNotFoundException: oracle.jdbc.driver.OracleDriver
	        dbNname =oracleDriver.getName();
	        dbPassword=oracleDriver.getPassword();
	        dbSchema=oracleDriver.getSchema();
	        if(!dbUrl.endsWith(dbSchema)){
	        	dbUrl=dbUrl+"/"+dbSchema;
	        }
	        
		} catch (Exception e) {
			new RuntimeException("数据库配置信息出错，请检查配置。");
		}
		
		
	}
	/**   
	*    
	* 方法名称：connDB   
	* 方法描述：  初始化数据库的连接操作
	* 参数列表：
	* 返  回  值：void 
	* @version  
	* @throws  
	*    
	*/ 
	public static Connection  initDBConn(){
		try {
			if(TcRunLog.isDebugEnabled()){
				TcRunLog.info("当前数据库配置项信息如下: dbUrl =【" +dbUrl +"】,dbNname=【"+dbNname+"】,dbPassword=【"+dbPassword+"】");
			}
			conn =DriverManager.getConnection(dbUrl, dbNname, dbPassword);
		} catch (SQLException e) {
			TcRunLog.error("数据库连接异常,请检查数据库配置信息.\r\n  ");
			closeDbConn();
			e.printStackTrace();
		}
		return conn;
		
	}

	/**   
	*    
	* 方法名称：closeDbConn   
	* 方法描述：  关闭所有打开的DB连接
	* 参数列表：
	* 返  回  值：void 
	* @throws  
	*    
	*/ 
	public static void closeDbConn() {
		try {
			if (result != null) {
				result.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			TcRunLog.error("关闭数据库连接出错. 错误信息如下： \r\n"+e);
		}
		
	}
	/**   
	*    
	* 方法名称：checkDbConn   
	* 方法描述：  检查数据库连接是否正确
	* 参数列表：@param dbName
	* 返  回  值：void 
	* 创  建  人：ligang13009   
	* 创建时间：2015年2月12日 下午4:37:13   
	* 修  改  人：ligang13009   
	* 修改时间：2015年2月12日 下午4:37:13   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public  void checkDbConn(String dbName){
		try {
			stmt =conn.prepareStatement("select * from "+ dbName);
			result =stmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			closeDbConn();
		} 
		
		 

	}
}
