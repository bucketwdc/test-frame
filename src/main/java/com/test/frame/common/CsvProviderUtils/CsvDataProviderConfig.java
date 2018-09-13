package com.test.frame.common.CsvProviderUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

import com.test.frame.common.logutil.TcRunLog;

import au.com.bytecode.opencsv.CSVReader;

/**   
*    
* 类  描  述：   使用OpenCsv进行CSV文件的读取操作
* 创  建  人：wdc   
* 创建时间：2018年2月15日 下午1:03:52   
* 修  改  人：wdc   
* 修改时间：2018年2月15日 下午1:03:52   
* 修改备注：   
* @version    
*    
*/
public class CsvDataProviderConfig implements Iterator<Object[]> {

	Class<?>[] methodParaType;
	CSVReader oCSVReader = null;
	Converter[] covertiUtils;
	String oCsvFilePath;
	Method errMethodPara;

	public CsvDataProviderConfig(Class<?> cls, Method method,
			String csvFilePath, String filePath) {
		//为了出错的时候提示错误文件路径
		errMethodPara = method;
		InputStream oInputStream = null;
		if (StringUtils.isBlank(filePath) || null == filePath) {
			csvFilePath = System.getProperty("user.dir") + "/" + csvFilePath;
		}
		TcRunLog.debug("当前的CSV文件地址是: "+csvFilePath+"-------------");
		File file = new File(csvFilePath);
		String ss = file.getAbsolutePath();
		try {
			oInputStream = new FileInputStream(file);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			TcRunLog.error(e.getStackTrace().toString());
		}
		// 第一行跳过去，不执行CSV第一行的数据获取
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(oInputStream,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		oCSVReader = new CSVReader(isr, ',', '\"', 1);
		// 获取方法的参数以及参数个数
		methodParaType = method.getParameterTypes();
		int methodParaNumber = methodParaType.length;
		// 参数类型转换
		covertiUtils = new Converter[methodParaNumber];
		for (int i = 0; i < covertiUtils.length; i++) {
			covertiUtils[i] = ConvertUtils.lookup(methodParaType[i]);
		}
	}

	String[] rowData;

	//循环遍历CSV数据文件的内容
	public boolean hasNext() {
		if (oCSVReader == null) {
			return false;
		}
		try {
			rowData = oCSVReader.readNext();
			if (null != rowData) {
				for (String str : rowData) {
					TcRunLog.debug("当前参数值是: " + str + "--------------");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			TcRunLog.error("读取测试用例对应的测试文件数据出错，请检查文件.");
		}
		// TODO Auto-generated method stub
		return rowData != null;
	}

	private String[] reReadCsvData() {
		if (rowData == null) {
			try {
				rowData = oCSVReader.readNext();
			} catch (IOException ioe) {
				TcRunLog.error("get next line error!");
				throw new RuntimeException(ioe);
			}
		}
		return rowData;
	}

	public void remove() {
		// TODO Auto-generated method stub

	}

	public CSVReader getReader() {
		// TODO Auto-generated method stub
		return oCSVReader;
	}

	/* 遍历传入的参数值
	 * @see java.util.Iterator#next()
	 */
	public Object[] next() {
		//定义参数值
		String[] paraValue;
		if (rowData != null) {
			paraValue = rowData;
		} else {
			//如果获取到的CSV数据为空，重新读取数据
			paraValue = reReadCsvData();
		}
		rowData = null;
		//获取所有的excel表格的参数内容
		Object[] args = judgeParaObjects(paraValue);
		return args;
	}

	/**   
	*    
	* 方法名称：judgeParaObjects   
	* 方法描述：  根据CSV的参数个数同方法的参数个数进行比对校验，校验通过后转换参数数据类型到Object[]对象
	* 参数列表：@param paraValue
	* 参数列表：@return
	* 返  回  值：Object[] 
	* 创  建  人：ligang13009   
	* 创建时间：2015年2月16日 下午2:09:41   
	* 修  改  人：wdc   
	* 修改时间：2015年2月16日 下午2:09:41   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/
	private Object[] judgeParaObjects(String[] paraValue) {
		int paraNumber = paraValue.length;
		int methodParaNumber = methodParaType.length;
		if (paraNumber != methodParaNumber) {
			TcRunLog.error("CSV文件【" + oCsvFilePath + "】的参数个数 [" + paraNumber
					+ "] \r\n与方法【" + errMethodPara + "】的参数个数 ["
					+ methodParaNumber + "] 不相等.请检查对应文件");
			return null;
		}

		//转换方法参数与CSV提供的参数进行数据匹配
		Object[] result = new Object[paraNumber];
		for (int i = 0; i < paraNumber; i++) {
			result[i] = covertiUtils[i]
					.convert(methodParaType[i], paraValue[i]);
		}
		return result;
	}

}
