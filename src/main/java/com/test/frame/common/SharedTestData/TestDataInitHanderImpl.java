package com.test.frame.common.SharedTestData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.test.frame.common.DBUtils.DbSqlCommand;
import com.test.frame.common.logutil.TcRunLog;
import com.test.frame.common.util.AtsAssertUtils;
import com.test.frame.common.util.Compare;
import com.test.frame.common.util.CsvParser;


public class TestDataInitHanderImpl implements TestDataInitHander {

	public void dbInsert(String[] files, String[] tableNames) {
		// TODO Auto-generated method stub
		//根据文件以及对应的数据库进行数据的插入操作--循环插入属性文件中所有的CSV文件数据
		for (int i = 0; i < files.length; i++) {
			dbInsert(files[i], tableNames[i]);
		}

	}

	/**   
	*    
	* 方法名称：dbInsert   
	* 方法描述：  读取csv文件内容，进行拼接，然后进行插入
	* 参数列表：@param filePath
	* 参数列表：@param tableName
	* 返  回  值：void 
	*    
	*/
	private void dbInsert(String filePath, String tableName) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = loadCsvTestData(filePath);
			String dbSql = jointSQL(tableName, br);
			DbSqlCommand.dbInsert(tableName, dbSql);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private BufferedReader loadCsvTestData(String filePath) {
		BufferedReader br = null;
		try { //根据文件路径加载CSV文件
			InputStream oInputStream = this.getClass().getClassLoader()
					.getResourceAsStream(filePath);
			//读取当前文件内容:值中如果有中文的话，需要使用csv文件对应的编码格式
			InputStreamReader oFileInputStream = new InputStreamReader(
					oInputStream, "utf-8");
			br = new BufferedReader(oFileInputStream);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return br;
	}

	/**   
	*    
	* 方法名称：jointSQL   
	* 方法描述： 拼接SQL语句
	*         SQL INSERT INTO 语句:	INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
	* 参数列表：@param tableName
	* 参数列表：@param br
	* 参数列表：@return
	* 参数列表：@throws IOException
	* 返  回  值：String 
	* @throws  
	*    
	*/
	private String jointSQL(String tableName, BufferedReader br)
			throws IOException {
		//拼接SQL语句：INSERT INTO table_name 
		StringBuffer oStringBuffer = new StringBuffer("INSERT INTO ")
				.append(tableName);

		String line = null;
		String preSql = null;
		//获取所有的CSV表中字段信息，进行拼接：(列1, 列2,...) 
		if ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ",");
			oStringBuffer.append(" (");
			while (st.hasMoreTokens()) {
				String currString = st.nextToken();
				currString = currString.replaceAll("\"", "");
				oStringBuffer.append(currString).append(",");
			}
			preSql = oStringBuffer.substring(0, oStringBuffer.length() - 1)
					+ ")";
		}
		//拼接  VALUES (值1, 值2,....)
		StringBuffer sqlBuffer = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sqlBuffer.append(preSql).append(" VALUES (");
			StringTokenizer st = new StringTokenizer(line, ",");
			while (st.hasMoreTokens()) {
				String nextToken = st.nextToken();
				nextToken = nextToken.replaceAll("\"", "\'");
				sqlBuffer.append(nextToken).append(",");
			}
		}
		//最终的SQL语句如下
		String sql = sqlBuffer.substring(0, sqlBuffer.length() - 1) + (")");
		TcRunLog.info("当前生成的SQL语句是: \r\n -------->>>  " + sql + "\n");
		return sql;
	}

	public int[] dbUpdate(String[] sqls, String[] tableNames) {
		// TODO Auto-generated method stub
		int[] updateRes = new int[sqls.length];
		for (int i = 0; i < sqls.length; i++) {
			int rs = DbSqlCommand.dbUpdate(tableNames[i], sqls[i]);
			if (rs == 0) {
				TcRunLog.info("第 【" + i + "】个SQL语句: " + sqls[i]
						+ "  Update动作执行失败。");
			}
			updateRes[i] = rs;
		}
		return updateRes;
	}

	public List<List<Map<String, String>>> dbSelect(String[] sqls,
			String[] tableNames) {
		List<List<Map<String, String>>> oList = new ArrayList<List<Map<String, String>>>();
		// TODO Auto-generated method stub
		for (int i = 0; i < sqls.length; i++) {
			List<Map<String, String>> res = DbSqlCommand.dbSelect(
					tableNames[i], sqls[i]);
			oList.add(res);
		}
		return oList;
	}

	public List<ResultSet> dbSelectResultSet(String[] sqls, String[] tableNames) {
		List<ResultSet> oList = new ArrayList<ResultSet>();
		// TODO Auto-generated method stub
		for (int i = 0; i < sqls.length; i++) {
			ResultSet res = DbSqlCommand.select(tableNames[i], sqls[i]);
			oList.add(res);
		}
		return oList;
	}

	public boolean dbCheck(String[] sqls, String[] tableNames, String[] files) {
		// TODO Auto-generated method stub
		//根据SQL语句获取所有的返回真实数据
		List<List<Map<String, String>>> trueRes = dbSelect(sqls, tableNames);
		//获取预期文件中的预期数据CSV
		List<List<Map<String, String>>> expectRes = getCsvData(files);
		if (trueRes == expectRes) {
			AtsAssertUtils.assertEquals("数据库查询出来的结果", 1, 1);
			return true;
		} else {
			//条数不一致的话，进行逐个字段比较，主要是通过预期结果中的字段进行比较
			return Compare.compare(trueRes, expectRes);
		}
	}

	public List<List<Map<String, String>>> getCsvData(String[] files) {
		// TODO Auto-generated method stub
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String, String>>>();
		try {
			for (String filePath : files) {
				getCsvData(list, filePath);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private void getCsvData(List<List<Map<String, String>>> list,
			String filePath) throws IOException {
		List<Map<String, String>> oList = new ArrayList<Map<String, String>>();
		BufferedReader br = loadCsvTestData(filePath);
		String line = null;
		//获得csv上面数据字段信息
		//定义一个Arraylist来有序的存储字段
		String[] temp = null;
		if ((line = br.readLine()) != null) {
			line = line.replaceAll("\"", "");
			temp = line.split(",");
		}
		while ((line = br.readLine()) != null) {
			Map<String, String> oMap = new HashMap<String, String>();
			CsvParser.matchCsvReadLine(line, oMap, temp);
			oList.add(oMap);
		}
		list.add(oList);
	}

	public boolean dbCheckMap(Map<String, String> oMap, String fileString) {
		// TODO Auto-generated method stub
		Map<String, String> map=getCsvData(fileString);
//		PrintXslFormat.iteratesMap(map);
		return Compare.comparMap(oMap, map);
	}

	private Map<String, String> getCsvData(String fileString) {
		Map<String, String> oMap = new HashMap<String, String>();
		try {
			BufferedReader br = loadCsvTestData(fileString);
			String line = null;
			//获得csv上面数据字段信息
			//定义一个Arraylist来有序的存储字段
			String[] temp = null;
			if ((line = br.readLine()) != null) {
				line = line.replaceAll("\"", "");
				temp = line.split(",");
			}
			while ((line = br.readLine()) != null) {
				CsvParser.matchCsvReadLine(line, oMap, temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oMap;
	}
}
