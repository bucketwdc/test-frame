package com.test.frame.common.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.test.frame.common.PropertyUtils.DefinePropertiesField;
import com.test.frame.common.logutil.TcRunLog;


/**   
*    
* 项目名称：ligang13009.hundsun.com   
* 类  名  称：PrintXslFormat   
* 类  描  述：   
* 创  建  人：ligang13009   
* 创建时间：2015年2月25日 下午2:35:45   
* 修  改  人：ligang13009   
* 修改时间：2015年2月25日 下午2:35:45   
* 修改备注：   
* @version    
*    
*/
public class PrintXslFormat {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void printResultSet(ResultSet oResultSet) {
		List row = new ArrayList();
		List<List> columnList = new ArrayList();
		int row_column = 0;
		try {
			ResultSetMetaData rsmd = oResultSet.getMetaData();
			int count = rsmd.getColumnCount();
			row_column = iteratorResult(oResultSet, row, columnList,
					row_column, rsmd, count);
			int columnNumber = row.size() / row_column;
			ConsoleTable t = new ConsoleTable(columnNumber, true);
			t.appendRow();
			for (int i = 0; i < columnNumber; i++) {
				t.appendColum(row.get(i));
			}
			for (int i = 0; i < row_column; i++) {
				t.appendRow();
				for (int ii = 0; ii < columnNumber; ii++) {
					t.appendColum(columnList.get(i).get(ii));
				}
			}
			row=null;
			TcRunLog.info("\r\n" + t);
		} catch (Exception e) {
			// TODO: handle exception
			TcRunLog.error("将ResultSet转换成Map失败:转换出现异常.\r\n" + e);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void printResultSetX(ResultSet oResultSet) {
		List row = new ArrayList();
		List<List> columnList = new ArrayList();
		int row_column = 0;
		try {
			ResultSetMetaData rsmd = oResultSet.getMetaData();
			int count = rsmd.getColumnCount();
			row_column = iteratorResult(oResultSet, row, columnList,
					row_column, rsmd, count);
			int columnNumber = row.size() / row_column;
			ConsoleTable t = new ConsoleTable(row_column + 1, false);
			for (int i = 0; i < columnNumber; i++) {
				t.appendRow();
				t.appendColum(row.get(i));
				for (int ii = 0; ii < row_column; ii++) {
					t.appendColum(columnList.get(ii).get(i));
				}
			}
			TcRunLog.info("\r\n" + t);
		} catch (Exception e) {
			// TODO: handle exception

			TcRunLog.error("printResultSet>>>将ResultSet转换成Map失败:转换出现异常.\r\n");
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static int iteratorResult(ResultSet oResultSet, List row,
			List<List> columnList, int row_column, ResultSetMetaData rsmd,
			int count) throws SQLException {
		while (oResultSet.next()) {
			List column = new ArrayList();
			for (int i = 1; i <= count; i++) {
				String key = rsmd.getColumnLabel(i);
				String value = oResultSet.getString(i);
				row.add(key);
				column.add(value);
			}
			row_column = row_column + 1;
			columnList.add(column);
		}
		return row_column;
	}

	public static void oPrintPropertiesField(
			DefinePropertiesField oDefinePropertiesField) {
		String[] rs = DefinePropertiesField.getFiledName();
		ConsoleTable t = new ConsoleTable(rs.length, true);
		for (int i = 0; i < rs.length; i++) {
			String[] x = (String[]) oDefinePropertiesField
					.getFieldValueByName(rs[i]);
			t.appendRow();
			t.appendColum(rs[i]);
			for (int ii = 0; ii < x.length; ii++) {
				t.appendColum(x[ii]);
			}
		}
		TcRunLog.debug("\r\n" + t);
	}

	/**   
	*    
	* 方法名称：iteratesMap   
	* 方法描述：  遍历Map
	* 参数列表：@param oMap
	* 返  回  值：void 
	* 创  建  人：ligang13009   
	* 创建时间：2015年2月12日 下午4:37:37   
	* 修  改  人：ligang13009   
	* 修改时间：2015年2月12日 下午4:37:37   
	* 修改备注：   
	* @version  
	* @throws  
	*    
	*/
	public static void iteratesMap(Map<String, String> oMap) {
		ConsoleTable t = new ConsoleTable(2, false);
		for (Entry<String, String> entry : oMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			t.appendRow();
			t.appendColum(key).appendColum(value);
		}
		TcRunLog.debug("\r\n" + t);
	}
}
