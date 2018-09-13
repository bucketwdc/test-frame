package com.test.frame.common.CsvProviderUtils;

import java.io.File;
import java.io.IOException;

import com.test.frame.common.logutil.TcRunLog;


/**   
 *    
 * 类  名  称：CsvFileOperationUtils   
 * 类  描  述：   CSV文件操作管理
 * 创  建  人：wdc   
 * 创建时间：2015年2月13日 下午3:53:09   
 * 修  改  人：wdc   
 * 修改时间：2015年2月13日 下午3:53:09   
 * 修改备注：   
 * @version    
 *    
 */
public class CsvFileOperationUtils {

	public static boolean createCsvFile(String csvFilePath) {
		// TODO Auto-generated method stub
//		csvFilePath="D:\\workspace\\ligang13009.hundsun.com\\"+csvFilePath;
		File oFile = new File(csvFilePath);
		try {
			if(!oFile.getParentFile().exists())
				oFile.getParentFile().mkdirs();
			oFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			TcRunLog.error("创建测试用例对应的测试文件失败,文件路径名称如下: \r\n                 "+csvFilePath+"\r\n错误信息如下: "+e);
			return false;
		}
		return true;
	}

}
