package com.test.frame.common.util;

import org.assertj.core.api.Assertions;
import org.testng.Assert;

/**   
*    
* 类  名  称：AtsAssertUtils   
* 创  建  人：wdc   
* 创建时间：2018年3月17日 下午2:39:26   
* 修改时间：2018年3月17日 下午2:39:26   
* 修改备注：   
* @version    
*    
*/ 
public class AtsAssertUtils extends Assertions {

	public static void assertNotEquals(String message, Object actual,
			Object expected) {
		Assert.assertNotEquals(actual, expected, message);
	}

	public static void assertEquals(String message, Object actual,
			Object expected) {
		Assert.assertEquals(actual, expected, message);
	}

	public static void assertEquals(String message, String actual,
			String expected) {
		Assert.assertEquals(actual, expected, message);
	}

	public static void assertNotEquals(String message, String actual,
			String expected) {
		Assert.assertNotEquals(actual, expected, message);
	}

	public static void assertNull(String message, Object object) {
		Assert.assertNull(object, message);
	}

	public static void assertNotNull(String message, Object object) {
		Assert.assertNotNull(object, message);
	}

	public static void assertTrue(String message, Boolean condition) {
		Assert.assertTrue(condition, message);
	}

	public static void assertFail(String message, Boolean condition) {
		Assert.assertFalse(condition, message);
	}
}
