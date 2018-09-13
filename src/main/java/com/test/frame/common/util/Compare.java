package com.test.frame.common.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Compare {

	public static boolean compare(List<List<Map<String, String>>> trueRes,
			List<List<Map<String, String>>> expectRes) {
		// TODO Auto-generated method stub
		Boolean flag = false;
		for (int i = 0; i < trueRes.size(); i++) {
			for (int j = 0; j < trueRes.get(i).size(); j++) {
				Map<String, String> map1 = trueRes.get(i).get(j);
				Map<String, String> map2 = expectRes.get(i).get(j);
				flag = comparMap(map1, map2);
			}

		}

		return flag;
	}

	public static Boolean comparMap(Map<String, String> map1,
			Map<String, String> map2) {
		// TODO Auto-generated method stub
		boolean flag = true;
		//逐行比对获得不同的字段信息
		Iterator<String> it = map2.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			//根据预期csv中的数据进行判断
			if (!map1.containsKey(key)) {
				AtsAssertUtils.assertEquals("预期结果中的字段：【" + key + "],在数据库中查询不到,请检查确认信息.",
						2, 1);
			}
			else {
				String val1=map1.get(key);
				String val2=map2.get(key);
				if(!StringUtils.equals(val1, val2)){
					flag = false;
					AtsAssertUtils.assertEquals(key + "的值和数据库中不一致。",
							map1.get(key), map2.get(key));
				}
			}
		}
		return flag;

	}

}
