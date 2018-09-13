package com.test.frame.test.testcase;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.test.frame.common.SharedTestData.TestDataDbOperateEnter;
import com.test.frame.common.SharedTestData.TestDataInitHanderImpl;
import com.test.frame.common.TCRunConfigManage.TCRunnerBase;
import com.test.frame.common.util.PrintXslFormat;

public class TestDataDbOperateEnterTest extends TCRunnerBase {

	//执行数据的插入
	public void dbInsert() {
		String filePath = "InsertDb.properties";
		TestDataDbOperateEnter.dbInsert(filePath);
	}

	public void dbSelect() {
		String filePath = "select.properties";
		List<ResultSet> oList = TestDataDbOperateEnter
				.dbSelectResultSet(filePath);
		for (ResultSet resultSet : oList) {
			ResultSet oResultSet = resultSet;
			PrintXslFormat.printResultSetX(oResultSet);
		}
	}

	public void dbUpdate() {
		String filePath = "updateDb.properties";
		int[] updateRunRes = TestDataDbOperateEnter.dbUpdate(filePath);
		for (int i : updateRunRes) {
			System.out.println(i);
		}
	}

	public void dbCheck() {
		String filePath = "dbCheck.properties";
		TestDataDbOperateEnter.dbCheck(filePath);
	}

	@Test
	public void dbDelete() {
		String filePath = "delete.properties";
		TestDataDbOperateEnter.dbUpdate(filePath);
	}

	public void dbCheckMap() {
		String filePath = "\\TestCase-Data\\FuncAmCombinfoQry_769002\\NormalTest\\dbCheck.csv";
		Map<String, String> oMap = new HashMap<String, String>();
		oMap.put("aa", "bb");
		new TestDataInitHanderImpl().dbCheckMap(oMap, filePath);
	}
}
