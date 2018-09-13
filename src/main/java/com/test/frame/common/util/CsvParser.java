package com.test.frame.common.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.test.frame.common.logutil.TcRunLog;


public class CsvParser {

	private static Pattern pattern = Pattern.compile(",?\"([^\"]*)\"|[^,]*?,");

	public static void matchCsvReadLine(String oReadLine,
			Map<String, String> map, String[] temp) {
		//		String sequence = "7,风控管理组,风控管理组,0,1,8,0,0,1,'1,2,3,8,9,a,b,c,d,e,f,g,h',0,0,'A,B,C,D,E,F,G,H,I,J,K,L,M,N'";
		Matcher m = getMatcher(oReadLine);
		boolean found = false;
		int i = 0;
		while (m.find()) {
			found = true;
			String A = m.group();
			if (StringUtils.equals(A.substring(0, 1), "\"")) {
				if (A.length() != 1) {
					A = A.replace("\"", "").trim();
				}

			}
			if (A.endsWith(",")) {
				A = A.substring(0, A.length() - 1);
			}
			if (!(A.isEmpty() | A == "")) {
				map.put(temp[i], A);
				i++;
			}

		}
		if (!found) {
			TcRunLog.error("CSV数据正则匹配错误-----错误-------");
		}
		
	}

	private static Matcher getMatcher(String sequence) {
		return pattern.matcher(sequence);
	}

}
