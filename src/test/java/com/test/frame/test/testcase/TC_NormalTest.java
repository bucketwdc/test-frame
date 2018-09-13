package com.test.frame.test.testcase;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.test.frame.common.DBUtils.DbSqlCommand;
import com.test.frame.common.TCRunConfigManage.TCRunnerBase;
import com.test.frame.common.logutil.TcRunLog;

public class TC_NormalTest extends TCRunnerBase {
	

	@Test(dataProvider = "oCsvDataProviderConfig")
	public static void test(String userName,String passWord) throws InterruptedException {
		TcRunLog.info(userName+"---- Str2: " + passWord);
		TcRunLog.debug(userName+"---- Str2: " + passWord);
		TcRunLog.error(userName+"---- Str2: " + passWord);
		String dbSql = "SELECT * from crm.t_user_info user where account_id = '15958112356' AND status = '01'";
		 List<Map<String, String>> db_UserInfoVo = DbSqlCommand.dbSelect("t_user_info", dbSql);
		System.out.println(db_UserInfoVo.isEmpty());
		System.out.println(db_UserInfoVo.get(0).get("ACCOUNT_ID"));
	}
}




//String dbSql="select  * from LIGANG.TACCOUNTGROUPTYPE t where t.c_group_type >1";
//String dbSql1="select  * from LIGANG.TACCOUNTGROUPTYPE t ";
//Map<String, String> oMap =DbSqlCommand.dbSelect("TACCOUNTGROUPTYPE", dbSql);
//MapUtils.iteratesMap(oMap);

//ResultSet ooo = DbSqlCommand.select("TACCOUNTGROUPTYPE", dbSql);
//PrintXslFormat.printResultSet(ooo);
//ResultSet oo0 = DbSqlCommand.select("TACCOUNTGROUPTYPE", dbSql1);
//PrintXslFormat.printResultSetX(oo0);
//ResultSet ooo0 = DbSqlCommand.select("TACCOUNTGROUPTYPE", dbSql1);
//PrintXslFormat.printResultSet(ooo0);