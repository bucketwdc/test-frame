package com.test.frame.test.testcase;

import org.testng.annotations.Test;

import com.test.frame.common.SharedTestData.SharedTestDataConfig;

public class SharedTestDataConfigTest {

  @Test
  public void initTestDataConfigs() {
	  
	  SharedTestDataConfig.initDefinePropertiesField("deldb.properties");
	  
  }
}
