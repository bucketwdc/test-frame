package com.test.frame.test.spring;

import org.testng.annotations.Test;

public class TestDemo_NormalTest extends BaseTest{
	
	@Test(dataProvider = "oCsvDataProviderConfig")
	public void test_Normal(String caseId,int exp_code,String exp_msg){
		System.out.println("entityid="+"exp_code="+exp_msg);
		
		
	}
	
	
}
