package com.test.frame.test.testcase;

import org.testng.annotations.Test;

import com.test.frame.common.TCRunConfigManage.TCRunnerBase;

public class TestExample_NormalTest extends TCRunnerBase {

	@Test(dataProvider = "oCsvDataProviderConfig")
	public void oTestExample(String filePath) {
		//调用业务脚本
		new InsertDBService().dbInsert(filePath);
		
	}

}
