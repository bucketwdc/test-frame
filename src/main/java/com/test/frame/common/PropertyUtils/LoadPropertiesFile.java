package com.test.frame.common.PropertyUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.test.frame.common.configparautil.Configration;
import com.test.frame.common.logutil.TcRunLog;

/**   
 *    
 * 类  名  称：ReadFromConfig   
 * 类  描  述：   从配置文件中加载配置项内容
 * 创  建  人：wdc   
 * @version    
 *    
 */
public class LoadPropertiesFile {
	private static Configration oConfigration = LoadConfigFile.oConfigration;

	/**   
	*    
	* 方法名称：oReadFromConfig   
	* 方法描述：  主要是用来加载读取properties文件
	* 参数列表：@param fileName  文件名称*.properties格式
	* 返  回  值：void 
	* @version  
	* @throws  
	*    
	*/
	public static void oLoadPropertiesFile(String fileName,int flag) {
		TcRunLog.info("加载配置文件 ：【 " + fileName + " 】");
		InputStream oInputStream = null ;
		//	生成properties对象 
		Properties oProperties = new Properties();
//		if (flag==0) {
//			/*
//			 * 
//			 * 在当前执行的进程下动态加载配置文件内容 获得当前线程的ClassLoader
//			 */
//			ClassLoader classLoad_currrent = Thread.currentThread()
//					.getContextClassLoader();
//			/*
//			 * 根据文件名称获取当前文件的本地绝对路径
//			 * 得到的当前ClassPath的绝对URI路径
//			 * 
//			 */
//			URL getUrlPath = classLoad_currrent.getResource(fileName);
//
//			if (getUrlPath == null) {
//				TcRunLog.error("配置文件 ：【" + fileName + " 】未找到！");
//				return;
//			}
//			try {
//				oInputStream = getUrlPath.openStream();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}else {
//			 try {
//				oInputStream = new FileInputStream(fileName);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} 
		
		 try {
				oInputStream = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//从指定路径加载信息到Properties 
		try {
			
			oProperties.load(oInputStream);
		} catch (Exception e) {
			TcRunLog.error("指定文件不存在！ [" + fileName + "] ,错误详情：  ["
					+ e.getMessage() + "]");
			return;
		}
		/*
		 * 遍历properties文件里面的内容
		 * Properties 继承于 Hashtable，entrySet()是Hashtable的方法，
		 * 返回此 Hashtable 中所包含的键的 Set 视图。
		 * 此 collection 中每个元素都是一个 Map.Entry
		 * 
		 */
		Set<Map.Entry<Object, Object>> content = oProperties.entrySet();
		for (Entry<Object, Object> entry : content) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			TcRunLog.debug(key + "------------key:value--------" + value);
			//把键值内容写入到内存文件中
			oConfigration.setPropertyValue(key.toString(), value.toString());
		}

	}

}
