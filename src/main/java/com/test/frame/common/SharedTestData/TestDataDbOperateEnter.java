package com.test.frame.common.SharedTestData;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.test.frame.common.PropertyUtils.DefinePropertiesField;
import com.test.frame.common.util.AtsAssertUtils;


/**   
*    
* 类  名  称：TestDataDbOperateEnter   
* 类  描  述：   主要是用来进行测试数据与数据库之间的操作：CSV数据的插入，数据的更新，数据的比较等
* 创  建  人：wdc   
* @version    
*    
*/
public class TestDataDbOperateEnter {

	private static TestDataInitHander oTestDataInitHanderImpl = new TestDataInitHanderImpl();
	static String[] files = null;
	static String[] tableNames = null;
	static String[] sqls = null;

	public static void dbInsert(String filePath) {
		judgeParaNameSame(filePath, 0);
		//进行插入操作
		oTestDataInitHanderImpl.dbInsert(files, tableNames);
	}

	public static int[] dbUpdate(String filePath) {
		judgeParaNameSame(filePath, 1);
		int[] updateRunRes = oTestDataInitHanderImpl.dbUpdate(sqls, tableNames);
		return updateRunRes;
	}

	public static List<List<Map<String, String>>> dbSelect(String filePath) {
		// TODO Auto-generated method stub
		judgeParaNameSame(filePath, 1);
		List<List<Map<String, String>>> oList = oTestDataInitHanderImpl
				.dbSelect(sqls, tableNames);
		return oList;
	}

	public static List<ResultSet> dbSelectResultSet(String filePath) {
		// TODO Auto-generated method stub
		judgeParaNameSame(filePath, 1);
		List<ResultSet> oList = oTestDataInitHanderImpl.dbSelectResultSet(sqls,
				tableNames);
		return oList;
	}

	public static Boolean dbCheck(String filePath) {
		// TODO Auto-generated method stub
		judgeParaNameSame(filePath, 3);
		oTestDataInitHanderImpl.dbCheck(sqls, tableNames, files);
		return false;
	}

	/**   
	*    
	* 方法名称：judgeParaNameSame   
	* 方法描述：  用来判断参数是否一致，插入判断文件个数和表个数，更新判断sql与表个数
	*         0表示是插入判断，1是更新判断
	* 参数列表：@param filePath
	* 参数列表：@param num 
	* 返  回  值：void 
	* @throws  
	*    
	*/
	private static void judgeParaNameSame(String filePath, int num) {
		if (StringUtils.isBlank(filePath))
			return;
		DefinePropertiesField oDefinePropertiesField = SharedTestDataConfig
				.initDefinePropertiesField(filePath);
		//获取Properties文件中配置项目值的参数个数是否一致
		sqls = oDefinePropertiesField.getTD_SQL();
		files = oDefinePropertiesField.getTD_FilesPath();
		tableNames = oDefinePropertiesField.getTD_TableName();
		if (num == 1)
			AtsAssertUtils.assertEquals(
					"属性文件配置错误,TD_SQL与TD_TableName配置的参数个数不一致，请检查文件数据.",
					sqls.length, tableNames.length);
		else if (num == 0) {
			AtsAssertUtils.assertEquals(
					"属性文件配置错误,TD_FilesPath与TD_TableName配置的参数个数不一致，请检查文件数据.",
					files.length, tableNames.length);
		} else if (num == 3) {
			AtsAssertUtils.assertEquals(
					"属性文件配置错误,TD_FilesPath与TD_TableName配置的参数个数不一致，请检查文件数据.",
					files.length, tableNames.length);
			AtsAssertUtils.assertEquals(
					"属性文件配置错误,TD_SQL与TD_TableName配置的参数个数不一致，请检查文件数据.",
					sqls.length, tableNames.length);
		} else {
			AtsAssertUtils.assertEquals("输入参数错误，只能输入0或者1: 0表示是插入判断，1是更新判断", 1,
					2);
		}

	}

}
