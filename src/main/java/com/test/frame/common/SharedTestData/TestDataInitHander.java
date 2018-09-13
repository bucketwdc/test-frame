package com.test.frame.common.SharedTestData;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface TestDataInitHander {

	/**   
	*    
	* 方法名称：dbInsert   
	* 方法描述：  根据具体的csv中存储的表数据进行数据插入到数据库，文件和表一一对应
	* 参数列表：@param files
	* 参数列表：@param tableNames
	* 返  回  值：void 
	* @version  
	* @throws  
	*    
	*/ 
	public abstract void dbInsert(final String[] files,
			final String[] tableNames);
	
	/**   
	*    
	* 方法名称：dbUpdate   
	* 方法描述：  根据属性文件中的TD_SQL语句进行表数据的更新，SQL语句与表一一对应
	* 参数列表：@param sqls
	* 参数列表：@param tableNames
	* 参数列表：@return
	* 返  回  值：int 
	*    
	*/ 
	public abstract int[] dbUpdate(String[] sqls, String[] tableNames);
	
	public abstract List<List<Map<String, String>>> dbSelect(String[] sqls, String[] tableNames);

	public abstract List<ResultSet> dbSelectResultSet(String[] sqls,
			String[] tableNames);

	/**   
	*    
	* 方法名称：dbCheck   
	* 方法描述：  根据SQL句查询出来的数据同预期结果进行比较，主要是比较预期结果文件中存在的字段
	* 参数列表：@param sqls
	* 参数列表：@param tableNames
	* 参数列表：@param files
	* 参数列表：@return
	* 返  回  值：boolean 
	*    
	*/ 
	public abstract boolean dbCheck(String[] sqls, String[] tableNames,
			String[] files);
	public abstract boolean dbCheckMap(Map<String, String> oMap,String files);
}
