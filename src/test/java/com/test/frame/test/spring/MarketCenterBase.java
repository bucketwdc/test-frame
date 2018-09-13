package com.test.frame.test.spring;

import org.testng.annotations.Listeners;

import com.test.frame.common.CsvProviderUtils.CsvDataProvideBase;

@Listeners(com.test.frame.common.TCRunConfigManage.TCRuningListener.class)
public class MarketCenterBase extends CsvDataProvideBase{

	public MarketCenterBase(){
		super();
	}
}
