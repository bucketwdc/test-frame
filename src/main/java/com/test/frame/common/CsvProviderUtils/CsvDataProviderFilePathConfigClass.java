package com.test.frame.common.CsvProviderUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;

import com.test.frame.common.PropertyUtils.PropertyConfig;

/**   
*    
* 类  名  称：CsvDataProviderFilePathConfigClass   
* 类  描  述：   主要是用来进行测试用例对应测试文件路径的设定相关信息内容
* 创  建  人：wdc   
* 创建时间：2015年2月13日 上午10:22:30   
* 修  改  人：wdc   
* 修改时间：2015年2月13日 上午10:22:30   
* 修改备注：   
* @version    
*    
*/ 
public class CsvDataProviderFilePathConfigClass {
	/**   
	*    
	* 方法名称：CsvDataProviderFilePathConfigClass   
	* 方法描述：  初始化测试环境的测试数据文件,根据类_方法指定文件路径及名称来获取对应的测试数据文件是否存在
	* 参数列表：@param oClass
	* 参数列表：@param oMethod
	* 参数列表：@return
	* 返  回  值：Iterator<Object[]> 
	* 创  建  人：wdc   
	* 创建时间：2015年2月13日 下午1:50:40   
	* 修  改  人：ligang13009   
	* 修改时间：2015年2月13日 下午1:50:40   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public static Iterator<Object[]> oCsvDataProviderFilePathConfigClass(
			Class<?> oClass, Method oMethod,String filePath) {
		// TODO Auto-generated method stub
		// 获取类名以及方法
		String className = oClass.getSimpleName();
		String tcPackge = "";
		// 获取父类的Packge名称
		if (className.endsWith("_NormalTest")) {
			className = className.split("_NormalTest")[0];
			tcPackge = "NormalTest";
		} else if (className.endsWith("_AbormalTest")) {
			className = className.split("_AbormalTest")[0];
			tcPackge = "AbormalTest";
		} else {
			return null;
		}
		String oMethodName = oMethod.getName();
		// 定义测试用例对应的测试数据目录位置名称
		String testData = "TestCase-Data/";
		// 初始化文件名称和文件路径，主要是用来对测试用例测试数据的区别对待，方便管理
		String tcDirCsv = testData + className + "/" + tcPackge + "/"
				+ oMethodName + ".csv";
		// 判断当前对应的测试文件是否存在，不存在就创建目录结构文件,默认文件路径是存在src/test/resources/
		String csvFilePath =  tcDirCsv;
		if(filePath !=null){
			csvFilePath=filePath+csvFilePath;
		}
		File oFile = new File(csvFilePath);
		
		if (!oFile.exists()) {
			CsvFileOperationUtils.createCsvFile(csvFilePath);
		}
		
		PropertyConfig.testConfigs.setPropertyValue("CsvFilePath", csvFilePath);
		CsvDataProviderConfig cdpc = new CsvDataProviderConfig(oClass, oMethod,
				csvFilePath,filePath);
		return cdpc;
	}

	public static String[] getCsvFilePara(Class<?> oClass, Method oMethod,String tcDirCsv){
		
		CsvDataProviderConfig oCsvDataProviderConfig =new CsvDataProviderConfig(oClass, oMethod, tcDirCsv, null);
		oCsvDataProviderConfig.getReader();
		return null;
		
	}
}
