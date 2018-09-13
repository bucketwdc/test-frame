package com.test.frame.common.DBPrepEnterPort;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.test.frame.common.DBUtils.DbSqlCommand;
import com.test.frame.common.logutil.TcRunLog;
import com.test.frame.common.util.DateUtil;


/**   
*    
* 类  名  称：CSVDataImportToDBEnterPort   
* 类  描  述：   CSV数据初始化相关操作
* 创  建  人：wdc   
* 创建时间：2015年3月17日 下午3:00:33   
* 修  改  人：wdc   
* 修改时间：2015年3月17日 下午3:00:33   
* 修改备注：   
* @version    
*    
*/ 
public class CSVDataImportToDBEnterPort {

	/**
	 * 将CSV文件导入到Oracle数据库中
	 * TestDataUtil.csvToOracle("INFO_TABLE.csv","INFO_TABLE",...); </li>
	 * 
	 * @param csvFile
	 * @param tableName
	 * @param ...args 用于替换CSV文件中的"?"， 例： String id="'TINGHE'"; String
	 *        date="sysdate"; TestDataUtil.csvToOracle("INFO_TABLE.csv",
	 *        "INFO_TABLE", id, date);
	 */
	public void csvToOracle(String csvFile, String tableName, String... args) {

		try {
			int argsIndex = 0;
			InputStream is = new StringUtils().getClass().getClassLoader()
					.getResourceAsStream(csvFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String preSql = "INSERT INTO " + tableName;
			String sql = null;
			String line = null;
			if ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				preSql += " (";
				while (st.hasMoreTokens()) {
					preSql = preSql + st.nextToken() + ",";
				}
				preSql = preSql.substring(0, preSql.length() - 1) + ")";
			}
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				sql = preSql + " VALUES (";
				while (st.hasMoreTokens()) {
					String currentToken = st.nextToken();
					if (currentToken.equals("?") || currentToken.equals("'?'")) {
						if (argsIndex < args.length) {
							currentToken = currentToken.replaceFirst("\\?",
									args[argsIndex++]);
						} else {
							throw new Exception("参数args个数不足,请检查");
						}
					}
					sql = sql + currentToken + ",";
				}
				sql = sql.substring(0, sql.length() - 1) + ")";
				TcRunLog.info("当前用例数据导入的sql语句是:  " + sql);
				int retVal = DbSqlCommand.dbUpdate(sql, tableName);
				if (retVal >= 1 && TcRunLog.isDebugEnabled()) {
					TcRunLog.debug("成功插入数据:" + sql);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量CSV文件导入Oracle数据库
	 * 
	 * #see <code>void csvToOracle(String csvFile, String tableName)</code>
	 * 
	 * @param csvFile
	 * @param tableName
	 */
	public void csvToOracle(String[] csvFile, String[] tableName) {
		int number = 0;
		while (number < csvFile.length) {
			csvToOracle(csvFile[number], tableName[number]);
			number++;
		}
	}

	/**
	 * 删除时间晚于当前时间24H之后的数据
	 * 
	 * @param tableName
	 * @param condition
	 */
	public void cleanDataAfterTomorrow(String tableName, String condition) {
		String outDate = DateUtil.getBeforeDayString(-1);
		String sql = "DELETE FROM " + tableName + " WHERE " + condition
				+ ">TO_DATE('" + outDate + "','yyyymmdd')";
		DbSqlCommand.dbDelete(sql, tableName);
	}

	/**
	 * 删除晚于当前系统时间的数据
	 * 
	 * @param tableName
	 * @param condition
	 */
	public void cleanDataByFormatDate(String tableName, String condition) {
		String outDate = DateUtil.getBeforeDayString(0);
		String sql = "DELETE FROM " + tableName + " WHERE " + condition + ">'"
				+ outDate + "'";
		DbSqlCommand.dbDelete(sql, tableName);
	}

	/**
	 * 删除晚于sysdate的数据 <li>delete from #tableName# where #condition#>sysdate</li>
	 * 
	 * @param tableName
	 * @param condition
	 */
	public void cleanDataBySysDate(String tableName, String condition) {
		String sql = "DELETE FROM " + tableName + " WHERE " + condition
				+ ">sysdate";
		DbSqlCommand.dbDelete(sql, tableName);
	}

	/**
	 * 删除晚于sysdate的数据 <li>delete from #tableName[i]# where
	 * #condition[i]#>sysdate</li>
	 * 
	 * @param tableName
	 * @param condition
	 */
	public void cleanDataBySysDate(String[] tableName, String[] condition) {
		int number = 0;
		while (number < tableName.length) {
			cleanDataBySysDate(tableName[number], condition[number]);
			number++;
		}
	}
}