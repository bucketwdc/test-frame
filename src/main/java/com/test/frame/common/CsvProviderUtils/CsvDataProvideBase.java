package com.test.frame.common.CsvProviderUtils;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;

import com.test.frame.common.PropertyUtils.PropertyConfig;
import com.test.frame.common.logutil.TcRunLog;
import com.test.frame.common.util.AtsAssertUtils;


/**   
 *    
 * 类  名  称：CsvDataProvideBase   
 * 类  描  述：   主要是用来数据量的初始化启动操作集合
 * 创  建  人：wdc   
 * 创建时间：2018年2月10日 下午2:53:18   
 * 修  改  人：wdc   
 * 修改备注：   
 * @version    
 *    
 */
public class CsvDataProvideBase extends AtsAssertUtils 
{

	public static String filePathUrl;

	public static String getFilePathUrl() {
		return filePathUrl;
	}

	public static void setFilePathUrl(String filePathUrl) {
		CsvDataProvideBase.filePathUrl = filePathUrl;
	}

	public CsvDataProvideBase() {
		if (StringUtils.isBlank(filePathUrl)) {
			filePathUrl = System.getProperty("user.dir") + "/";
		}
		setFilePathUrl(filePathUrl);
		PropertyConfig.initConfigs(filePathUrl+ "src/main/resources/");
	}

	public CsvDataProvideBase(String filePathUrl) {
		setFilePathUrl(filePathUrl);
		PropertyConfig.initConfigs(filePathUrl + "src/main/resources/");
	}

	/**   
	*    
	* 方法名称：getCsvTestDataProvider   
	* 方法描述：  通过相同的NameDataProvider进行数据参数的关联
	* 参数列表：@param oMethod
	* 参数列表：@return
	* 返  回  值：Iterator<Object[]> 
	* 创  建  人：wdc   
	* 创建时间：2015年2月13日 下午3:00:16   
	* 修  改  人：wdc   
	* 修改时间：2015年2月13日 下午3:00:16   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/
	@DataProvider(name = "oCsvDataProviderConfig")
	public static Iterator<Object[]> getCsvTestDataProvider(Method oMethod) {
		Class<?> oClass = oMethod.getDeclaringClass();
		Iterator<Object[]> oCsvDataProvider = getCsvTestDataProvider(oClass,
				oMethod, null);
		return oCsvDataProvider;
	}

	public Iterator<Object[]> getCsvTestData(Method oMethod) {
		Class<?> oClass = oMethod.getDeclaringClass();
		TcRunLog.info(" <----------->" + filePathUrl
				+ "<---------------->");
		Iterator<Object[]> oCsvDataProvider = getCsvTestDataProvider(oClass,
				oMethod, filePathUrl);
		return oCsvDataProvider;
	}

	protected static Iterator<Object[]> getCsvTestDataProvider(Class<?> oClass,
			Method oMethod, String filePath) {
		// TODO Auto-generated method stub
		Iterator<Object[]> rs = CsvDataProviderFilePathConfigClass
				.oCsvDataProviderFilePathConfigClass(oClass, oMethod, filePath);
		if (null == rs) {
			TcRunLog.error("\r\n             当前测试类：【"
					+ oClass.getName()
					+ "】"
					+ "\r\n             测  试 方 法: 【"
					+ oMethod.getName()
					+ "】"
					+ "\r\n             错  误 提 示: 请根据规范要求设置用户名以及目录结构：接口或用例为Packge，测试用例分为正常用例,"
					+ "\r\n             名称格式:\r\n                   正常用例---接口名称+NormalTest. "
					+ "\r\n                   异常用例---接口名称+AbormalTest.");
		}
		return rs;
	}

}
