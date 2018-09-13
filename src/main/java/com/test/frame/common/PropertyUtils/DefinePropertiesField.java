package com.test.frame.common.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.test.frame.common.logutil.TcRunLog;


/**   
*    
* 类  名  称：DefinePropertiesField   
* 类  描  述：   定义properties文件的字段信息：目前初步定义3个字段: SQL语句，file路径，表
* 创  建  人：wdc   
* 修改备注：   
* @version    
*    
*/
public class DefinePropertiesField {

	//数据初始化的时候使用的统一SQL语句
	private String[] TD_SQL;
	//数据初始化的时候使用的统一SQL语句与之对应的表
	private String[] TD_TableName;
	//数据初始化的时候使用的统一文件路径地址
	private String[] TD_FilesPath;

	public String[] getTD_SQL() {
		return TD_SQL;
	}

	public void setTD_SQL(String[] tD_SQL) {
		TD_SQL = tD_SQL;
	}

	public String[] getTD_TableName() {
		return TD_TableName;
	}

	public void setTD_TableName(String[] tD_TableName) {
		TD_TableName = tD_TableName;
	}

	public String[] getTD_FilesPath() {
		return TD_FilesPath;
	}

	public void setTD_FilesPath(String[] tD_FilesPath) {
		TD_FilesPath = tD_FilesPath;
	}

	/** 
	  * 获取属性名数组 
	  * */
	public static String[] getFiledName() {
		Field[] fields = DefinePropertiesField.class.getDeclaredFields();
		String[] fieldNames = new String[fields.length - 1];
		for (int i = 0; i < fieldNames.length; i++) {
			//结果从1开始 是排除logger这个对象
			fieldNames[i] = fields[i + 1].getName();
		}
		return fieldNames;
	}

	/** 
	 * 根据属性名获取属性值 
	 * */
	public Object getFieldValueByName(String fieldName) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = this.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(this, new Object[] {});
			return value;
		} catch (Exception e) {
			TcRunLog.error(e.getMessage());
			return null;
		}
	}
}
