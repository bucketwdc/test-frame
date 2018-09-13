package com.test.frame.common.PropertyUtils;

import com.test.frame.common.DBUtils.GetDbConfigInfo;
import com.test.frame.common.configparautil.Configration;

/**   
 *    
 * 类  名  称：PropertyConfig   
 * 类  描  述：读取系统配置参数
 * 创  建  人：wdc   
 * @version    
 *    
 */
public class PropertyConfig {
	/** 配置项缓存 */
	public static Configration testConfigs = null;
	
	public  static void initConfigs(String filePath){
		// 将配置项入缓存，只加载一次
		if (null == testConfigs) {
			testConfigs = LoadConfigFile.oReadConfigFile(filePath);
		}
		//数据库配置的加载
		if(null !=testConfigs){
			testConfigs.setPropertyValue("Env_Path",filePath);
			GetDbConfigInfo.readDBconfInfo(testConfigs);
		}
		
	}
}
