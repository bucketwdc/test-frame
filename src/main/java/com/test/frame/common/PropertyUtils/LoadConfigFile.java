package com.test.frame.common.PropertyUtils;

import org.apache.commons.lang3.StringUtils;

import com.test.frame.common.configparautil.Configration;
import com.test.frame.common.configparautil.ConfigrationImpl;


/**   
 *    
 * 类  名  称：LoadConfigFile   
 * 类  描  述： 在系统启动时候用来，加载需要用到的配置文件等
 * 创  建  人：wdc   
 * @version    
 *    
 */
public class LoadConfigFile {
	public static Configration oConfigration;
	//定义配置文件的路径
	private static final String Ats_config_url ="ATS_Config/";
	private static final String Ats_config_properties_name ="ats_config.properties";
	//定义配置文件的健名称
	private static final String Env_Key="Env_Key";
	private static final String Ext_Config="Ext_Config";
	/**   
	*    
	* 方法名称：oSystemLoadConfigFile   
	* 方法描述：  在系统启动时候，根据内存是否为空，来执行配置文件的读取
	* 参数列表：
	* 返  回  值：oConfigParaToMemory 
	* @version  
	* @throws  
	*    
	*/ 
	public static Configration oReadConfigFile(String filePath){
		if(oConfigration == null){
			oConfigration =new ConfigrationImpl();
			//加载外部文件
			int flag=0;
//			if(!StringUtils.isBlank(filePath) ||null!= filePath){
				filePath=filePath+Ats_config_url + Ats_config_properties_name;
//				flag=1;
//			}else {
//				filePath=Ats_config_url + Ats_config_properties_name;
//			}
			LoadPropertiesFile.oLoadPropertiesFile(filePath,flag);
			/*
			 * 读取加载到内存数据中的键值
			 * dbContent:数据库配置文件
			 * extFileContent：扩展属性文件，支持多文件以‘,’隔开
			 * envContent：环境对象--用来切换环境使用
			 * 
			 */
			String extFileContent=oConfigration.getPropertyValue(Ext_Config);
			String envContent =oConfigration.getPropertyValue(Env_Key);
			
			if(StringUtils.isNotBlank(extFileContent)){
				String[] oList =extFileContent.split(",");
				for (String string : oList) {
					LoadPropertiesFile.oLoadPropertiesFile(filePath+string,flag);
				}
			}
			if(StringUtils.isNotBlank(envContent)){
				LoadPropertiesFile.oLoadPropertiesFile(filePath+envContent,flag);
			}
		}
		
		return oConfigration;
		
	}

}
