package com.test.frame.test.testcase;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.test.frame.common.PropertyUtils.ReadPropertiesContents;
import com.test.frame.common.util.PrintXslFormat;

public class ReadPropertiesContentsTest {

	@Test
	public void test() {

		HashMap<String, String> res = ReadPropertiesContents
				.readPropertiesContents(ReadPropertiesContentsTest.class, "dbCheck.properties");

		PrintXslFormat.iteratesMap(res);
	}

}
