package com.test.frame.common.configparautil;

import java.util.Map;

/**   
 *    
 * 类  名  称：ConfigParaToMemory   
 * 类  描  述：这个接口主要是用来定义Configration对象，并对对象进行读写
 * 创  建  人：wdc   
 * 创建时间：2015年2月9日 下午4:05:42   
 * 修  改  人：wdc   
 * 修改时间：2015年2月9日 下午4:05:42   
 * 修改备注：   
 * @version    
 *    
 */
public interface Configration {

	//在内存中定义一个用来存储配置对应的属性V属性值的Map
	public Map<String, String> getConfig();

	//设置属性对应的属性值
	public void setPropertyValue(String key, String value); 
	
	//获取属性值，根据属性获取属性值
	public String getPropertyValue(String key);

	//根据属性来获取属性值，非空即返回对应的属性值
	public String getPropertyValue(String key, String defaultValue);


}
