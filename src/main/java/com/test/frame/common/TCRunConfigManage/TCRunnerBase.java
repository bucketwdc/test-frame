package com.test.frame.common.TCRunConfigManage;

import org.testng.annotations.Listeners;

import com.test.frame.common.CsvProviderUtils.CsvDataProvideBase;


/**   
*    
* 类  名  称：TCRunnerBase   
* 类  描  述：   测试用来执行时候依赖的基础类,集成数据的驱动类，以完成数据驱动
* 创  建  人：wdc   
*    
*/
@Listeners(TCRuningListener.class)
public class TCRunnerBase extends CsvDataProvideBase {
	public TCRunnerBase() {
		super();
	}

}
