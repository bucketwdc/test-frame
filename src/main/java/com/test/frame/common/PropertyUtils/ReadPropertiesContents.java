package com.test.frame.common.PropertyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.test.frame.common.logutil.TcRunLog;


public class ReadPropertiesContents {

	/**   
	*    
	* 方法名称：readPropertiesContents   
	* 方法描述：  读取properties文件
	* 参数列表：@param cls
	* 参数列表：@param propertiesFilePath  传入参数fileName是要读取的资源文件的文件名如(file.properties)  
	* 参数列表：@return
	* 返  回  值：HashMap<String,String> 
	* 修改时间：2015年3月2日 上午10:09:13   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/
	public static HashMap<String, String> readPropertiesContents(Class<?> cls,
			String propertiesFilePath) {

		HashMap<String, String> oPropertiesContents = new HashMap<String, String>();
		Properties oProperties = new Properties();
		InputStream oInputStream = null;
		try {
			//读取properties文件的数据内容
			oInputStream = cls.getClassLoader().getResourceAsStream(
					propertiesFilePath);
			//得到当前类的路径，并把资源文件名作为输入流  
			oProperties.load(oInputStream);
			//得到资源文件中的所有key值  
			Enumeration<?> en = oProperties.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String value = upEncode(oProperties.getProperty(key),"utf-8");
				//输出资源文件中的key与value值  
				TcRunLog.debug("当前配置文件<" + propertiesFilePath + ">的内容是：【" + key
						+ " : " + value+" 】");
				oPropertiesContents.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
			TcRunLog.info("读取资源文件出错");
		} finally {
			try {
				if (null != oInputStream) {
					oInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				TcRunLog.info("关闭流失败");
			}
		}

		return oPropertiesContents;

	}
	private static String upEncode(String value,String encode){
		if(value==null || value=="" || StringUtils.isBlank(value)){
			return null;
		}
		try {
			value=new String(value.getBytes("ISO-8859-1"),encode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TcRunLog.info("properties文件内容编码转换错误");
		} 
		return value;
	}
}
