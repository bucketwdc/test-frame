package com.test.frame.common.configparautil;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.test.frame.common.logutil.TcRunLog;

/**   
 *    
 * 类  名  称：ConfigParaToMemoryImpl   
 * 类  描  述： 主要是用来定义Configration对象的实现，并对对象进行读写 ,用于数据的初始化 .
 * 创  建  人：wdc   
 * 创建时间：2015年2月9日 下午4:14:19   
 * 修  改  人：wdc   
 * 修改时间：2015年2月9日 下午4:14:19   
 * 修改备注：   
 * @version    
 *    
 */
public class ConfigrationImpl implements Configration {
	private Map<String, String> atsConfigMap = new HashMap<String, String>();
	
	/*
	 * 
	 * 在内存中定义一个用来存储配置对应的属性V属性值的Map
	 *
	 */
	public Map<String, String> getConfig() {
		if (TcRunLog.isDebugEnabled()) {
			TcRunLog.debug("getConfig() - start"); //$NON-NLS-1$
		}

		Map<String,String> oMap = new HashMap<String, String>();
		oMap.putAll(this.atsConfigMap);

		if (TcRunLog.isDebugEnabled()) {
			TcRunLog.debug("getConfig() - end"); //$NON-NLS-1$
		}
		return oMap;
	}
	/*
	 * 
	 * 在内存中定义一个用来存储配置对应的属性V属性值的Map
	 *
	 */
	public void setCofig(Map<String,String> map){
		if (TcRunLog.isDebugEnabled()) {
			TcRunLog.debug("setCofig(Map<String,String>) - start"); //$NON-NLS-1$
		}

		this.atsConfigMap.putAll(map);

		if (TcRunLog.isDebugEnabled()) {
			TcRunLog.debug("setCofig(Map<String,String>) - end"); //$NON-NLS-1$
		}
	}
	/*
	 * 
	 * 获取属性值，根据属性获取属性值
	 *
	 */
	
	public String getPropertyValue(String key) {
		if (TcRunLog.isDebugEnabled()) {
			TcRunLog.debug("getPropertyValue(String) - start"); //$NON-NLS-1$
		}

		String returnString = this.atsConfigMap.get(key);
		if (TcRunLog.isDebugEnabled()) {
			TcRunLog.debug("getPropertyValue(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}
	/*
	 * 设置属性对应的属性值
	 */
	public void setPropertyValue(String key,String value){
		if (TcRunLog.isDebugEnabled()) {
			TcRunLog.debug("setPropertyValue(String, String) - start"); //$NON-NLS-1$
		}

		this.atsConfigMap.put(key, value);

		if (TcRunLog.isDebugEnabled()) {
			TcRunLog.debug("setPropertyValue(String, String) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * 根据属性来获取属性值，非空即返回对应的属性值
	 */
	public String getPropertyValue(String key, String defaultValue) {
		if (TcRunLog.isDebugEnabled()) {
			TcRunLog.debug("getPropertyValue(String, String) - start"); //$NON-NLS-1$
		}

		String returnString = this.atsConfigMap.get(key) == null ? defaultValue : this.atsConfigMap.get(key);
		if (TcRunLog.isDebugEnabled()) {
			TcRunLog.debug("getPropertyValue(String, String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}
	/**   
	*    
	* 方法名称：iterateConfigration   
	* 方法描述：  根据传入的Configration对象进行结果数据的遍历
	* 参数列表：@param conf
	* 返  回  值：void 
	* 创  建  人：ligang13009   
	* 创建时间：2015年2月11日 上午10:06:03   
	* 修  改  人：ligang13009   
	* 修改时间：2015年2月11日 上午10:06:03   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/ 
	public static void iterateConfigration(Configration conf){
		Set<Map.Entry<String, String>> rs = conf.getConfig().entrySet();
		for (Entry<String, String> entry : rs) {
			TcRunLog.info(entry.getKey() +" : "+entry.getValue());
		}
	}
}
