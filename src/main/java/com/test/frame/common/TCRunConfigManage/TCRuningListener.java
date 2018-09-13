package com.test.frame.common.TCRunConfigManage;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.test.frame.common.logutil.TCExecutionLoggerRecord;


/**   
*    
* 类  名  称：TCRuningListener   
* 类  描  述：   用来配置测试用来执行的时候过程监听
* 创  建  人：wdc   
* @version    
*    
*/ 
public  class TCRuningListener implements ITestListener {

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		TCExecutionLoggerRecord.startTcRecord(result);
		
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		TCExecutionLoggerRecord.endSuccTcRecord(result);
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		TCExecutionLoggerRecord.endFailTcRecord(result);
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		TCExecutionLoggerRecord.endSkipTcRecord(result);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
//		TCExecutionLoggerRecord.initTcRecord(context,0);
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		TCExecutionLoggerRecord.endTcRecord(context);
	}

}
