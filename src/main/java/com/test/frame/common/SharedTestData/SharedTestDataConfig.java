package com.test.frame.common.SharedTestData;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.test.frame.common.PropertyUtils.DefinePropertiesField;
import com.test.frame.common.PropertyUtils.PropertiesFieldEnum;
import com.test.frame.common.PropertyUtils.ReadPropertiesContents;
import com.test.frame.common.logutil.TcRunLog;
import com.test.frame.common.util.PrintXslFormat;


/**   
*    
* 类  名  称：SharedTestDataConfig   
* 类  描  述：   该类主要是用来进行测试的数据的准备工作，主要是针对很多测试用例都是用都一个操作文件的情况下的数据准备工作。
*          比如：N个测试用例都需要使用同一个初始化数据库的文件进行环境初始化，为了统一便利的管理，统一使用路径指引
*          数据统一存放。
*          比如用例执行完成后的数据库清理语句，统一放到一个地方和用例分离开。
* 创  建  人：wdc   
* @version    
*    
*/
public class SharedTestDataConfig {

	//定义内存中缓存的配置项内容HashMap集合,路径字段：配置文件内容（properties-->键值对）
	private static Map<String, HashMap<String, String>> oMemoryconfigCacheItem = new HashMap<String, HashMap<String, String>>();

	public static void clear(){oMemoryconfigCacheItem.clear();}
	
	/**   
	*    
	* 方法名称：initTestDataConfigs   
	* 方法描述：  主要是用来进行测试数据属性文件的配置初始化工作。
	*          完成配置文件的验证，数据读取。
	*          如果存在就不操作，不存在就把当前路径对应的配置文件信息读取到内存
	* 参数列表：@param propertiesFilePath
	* 返  回  值：void 
	* @throws  
	*    
	*/
	public static void initTestDataConfigs(String propertiesFilePath) {
		//验证测试用例执行后，内存中是否存在当前的配置路径属性键值对Map,不存在就添加进来
		if (oMemoryconfigCacheItem.containsKey(propertiesFilePath)) {
			return;
		}
		//定义一个配置文件内容的HaspMap
		HashMap<String, String> propertiesContents = new HashMap<String, String>();
		//根据路径开始读取配置文件properties中的文件内容
		propertiesContents = ReadPropertiesContents.readPropertiesContents(
				SharedTestDataConfig.class, propertiesFilePath);
		//把properties的内容添加到内存中
		oMemoryconfigCacheItem.put(propertiesFilePath, propertiesContents);

	}

	public static DefinePropertiesField initDefinePropertiesField(
			String propertiesFilePath) {

		DefinePropertiesField oDefinePropertiesField = new DefinePropertiesField();
		//初始化内存中配置文件信息内容
		initTestDataConfigs(propertiesFilePath);
		//定义一个配置文件对象，用来管理配置数据信息
		HashMap<String, String> oPropertiesField = oMemoryconfigCacheItem
				.get(propertiesFilePath);
		//获取对应的字段信息
		String td_sql = oPropertiesField.get(PropertiesFieldEnum.TD_SQL
				.getValue());
		String td_tbaleName = oPropertiesField
				.get(PropertiesFieldEnum.TD_TableName.getValue());
		String td_files = oPropertiesField.get(PropertiesFieldEnum.TD_FilesPath
				.getValue());
		for (Entry<String, String> entry : oPropertiesField.entrySet()) {
			if (matchcaseid("TD001", entry.getValue())) {
				//获取配置文件的标记td_
				final String str = getPrefix(entry.getKey());
				td_tbaleName = joinString(td_tbaleName,
						oPropertiesField.get(str + "TableName"));
				td_files = joinString(td_files,
						oPropertiesField.get(str + "FilesPath"));
				td_sql = joinString(td_sql, oPropertiesField.get(str + "SQL"));
				break;
			}
		}

		oDefinePropertiesField.setTD_FilesPath(getSplitedString(td_files));
		oDefinePropertiesField.setTD_TableName(getSplitedString(td_tbaleName));
		oDefinePropertiesField.setTD_SQL(getSplitedString(td_sql));
		if (TcRunLog.isDebugEnabled()) {
			PrintXslFormat.oPrintPropertiesField(oDefinePropertiesField);
		}
		return oDefinePropertiesField;

	}

	private static boolean matchcaseid(String caseid, String caseidConfig) {
		if (StringUtils.isBlank(caseidConfig) || StringUtils.isBlank(caseid))

			return false;

		if (caseid.equals(caseidConfig.toUpperCase()))
			return true;

		return false;
	}

	/**
	 * 将字符串以";"隔开
	 * 
	 * @param s
	 * @return
	 */
	private static String[] getSplitedString(String s) {
		return StringUtils.isEmpty(s) ? new String[] {} : s.split(";");
	}

	/**
	 * 返回s中第一个字符和第一个"_"之间的字符串
	 * 
	 * @param s
	 * @return
	 */
	private static String getPrefix(String s) {
		return s.substring(0, s.indexOf("_") + 1);
	}

	private static String joinString(String str1, String str2) {
		String str = "";
		if (StringUtils.isEmpty(str1)) {
			str = str2;
		}
		if (StringUtils.isEmpty(str2)) {
			str = str1;
		} else {
			str = str1 + ";" + str2;
		}
		return str;
	}

	/**
	 * 连接两个字符串
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getJoinedString(String s1, String s2) {
		return StringUtils.isEmpty(s1) ? s2 : StringUtils.isEmpty(s2) ? s1 : (s1
				+ ";" + s2);
	}
}
