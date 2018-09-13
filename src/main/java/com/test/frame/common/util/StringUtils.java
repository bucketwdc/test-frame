package com.test.frame.common.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StringUtils {
	public static final String EMPTY_STRING = "";

	public static String replaceSubString(String originalValue,
			String replacement, int start, int end) {
		if (isBlank(originalValue) || start < 0
				|| start > originalValue.length() || end < 0
				|| end > originalValue.length() || end <= start) {
			return originalValue;
		}
		String preStr = originalValue.substring(0, start);
		String lastStr = originalValue.substring(end, originalValue.length());
		String retStr = preStr + replacement + lastStr;
		return retStr;
	}

	/**
	 * 
	 * @param amount
	 *            12
	 * @return amount 1200
	 */
	public static String amountFormat(String amount) {

		if (null == amount || amount.equals("0"))
			return "0";
		return amount + "00";
	}

	public static String stringFormat(String str) {
		if (str.equals("null") || str.equals(""))
			str = "0";
		return str;

	}

	public static String stringNullFormat(String str) {
		if (str.equals("null") || str.equals(""))
			str = null;
		return str;

	}

	public static String amountSysFormat(String amount) {

		if (null == amount || amount.equals("0"))
			return "0.00";
		if (amount.length() >= 3) {
			String point = amount.substring(amount.length() - 2,
					amount.length());
			String integer = amount.substring(0, amount.length() - 2);
			amount = integer + "." + point;
		} else {
			amount = "0." + amount;
		}
		return amount;
	}

	/**
	 * <pre>
	 * StringUtil.isBlank(null)      = true
	 * StringUtil.isBlank("")        = true
	 * StringUtil.isBlank(" ")       = true
	 * StringUtil.isBlank("bob")     = false
	 * StringUtil.isBlank("  bob  ") = false
	 * </pre>
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <pre>
	 * StringUtil.isNotBlank(null)      = false
	 * StringUtil.isNotBlank("")        = false
	 * StringUtil.isNotBlank(" ")       = false
	 * StringUtil.isNotBlank("bob")     = true
	 * StringUtil.isNotBlank("  bob  ") = true
	 * </pre>
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * <pre>
	 * StringUtil.isBlankOrNull(null)      = true
	 * StringUtil.isBlankOrNull("null")    = true
	 * StringUtil.isBlankOrNull("")        = true
	 * StringUtil.isBlankOrNull(" ")       = true
	 * StringUtil.isBlankOrNull("bob")     = false
	 * StringUtil.isBlankOrNull("  bob  ") = false
	 * </pre>
	 */
	public static boolean isBlankOrNull(String str) {
		int strLen;
		if (str == null || str.equals("null") || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <pre>
	 * StringUtils.stringFirstCharToUpperCase(null)      = null
	 * StringUtils.stringFirstCharToUpperCase("null")    = "Null"
	 * StringUtils.stringFirstCharToUpperCase("")        = ""
	 * StringUtils.stringFirstCharToUpperCase("aBC")       = "ABC"
	 * </pre>
	 */
	public static String stringFirstCharToUpperCase(String str) {
		if (null == str || str.length() < 1) {
			return str;
		}
		String strt1 = str.substring(0, 1).toUpperCase();
		String strt2 = str.substring(1);
		str = strt1 + strt2;
		return str;

	}

	/**
	 * <pre>
	 * StringUtils.stringFirstCharToLowerCase(null)      = null
	 * StringUtils.stringFirstCharToLowerCase("NULL")    = "nULL"
	 * StringUtils.stringFirstCharToLowerCase("")        = ""
	 * StringUtils.stringFirstCharToLowerCase("Abc")     = "abc"
	 * </pre>
	 * 
	 * @author gang.lg
	 * 
	 */
	public static String stringFirstCharToLowerCase(String str) {
		if (null == str || str.length() < 1) {
			return str;
		}
		String strt1 = str.substring(0, 1).toLowerCase();
		String strt2 = str.substring(1);
		str = strt1 + strt2;
		return str;

	}

	/**
	 * @return
	 * @author gang.lg
	 */
	public static List<Integer> convertColumnRegex2Columns(String columnRegex) {
		List<Integer> listColumns = new ArrayList<Integer>();
		if (StringUtils.isBlank(columnRegex)
				|| !columnRegex
						.matches("^((\\d+)|(\\d+):(\\d+))(,((\\d+)|(\\d+):(\\d+)))*$")) {
			return listColumns;
		}
		String[] columnStrings = columnRegex.split(",");
		for (int i = 0; i < columnStrings.length; i++) {
			String[] columns = columnStrings[i].split(":");
			if (1 == columns.length) {
				listColumns.add(Integer.valueOf(columns[0]));
			} else if (2 == columns.length) {
				int from = Integer.valueOf(columns[0]);
				int temp = Integer.valueOf(columns[1]);
				int to = -1;
				if (from > temp) {
					to = from;
					from = temp;
				} else {
					to = temp;
				}
				for (int j = from; j <= to; j++) {
					listColumns.add(Integer.valueOf(j));
				}
			} else {
				return new ArrayList<Integer>();
			}
		}
		Collections.sort(listColumns, new IntegerCompare());
		return listColumns;
	}

	private static class IntegerCompare implements Comparator<Object> {

		public int compare(Object o1, Object o2) {
			Integer int1 = (Integer) o1;
			Integer int2 = (Integer) o2;
			return int1.intValue() - int2.intValue();
		}

	}

	/**
	 * 
	 * @param url
	 */
	public static String getTradeNo(String url) {

		if (null == url)
			return "";

		String[] tr = url.split("=");
		String tradeNo = tr[tr.length - 1];

		return tradeNo;
	}

	public static boolean isNull(String str) {
		return str == null;
	}

	/**
	 * @return
	 */
	public static String getRandomId() {
		return DateUtil.getLongDateString() + "-" + new Random().nextInt(1000);
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getParamList(String path) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		InputStream is = new StringUtils().getClass().getClassLoader()
				.getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		InputStream is2 = new StringUtils().getClass().getClassLoader()
				.getResourceAsStream(path);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));

		String line = "";
		String[] keys = null;

		try {
			int count = 0;
			while (StringUtils.isNotBlank(br2.readLine())) {
				count++;
			}
			count = count - 1;
			@SuppressWarnings("rawtypes")
			Map[] maps = new HashMap[count];
			String line1 = br.readLine();
			if (StringUtils.isNotBlank(line1))
				keys = line1.split(",");
			int t = 0;
			while ((line = br.readLine()) != null) {
				Map<String, String> map = new HashMap<String, String>();
				String[] strs = line.split(",");
				if (strs.length == keys.length) {
					for (int i = 0; i < keys.length; i++) {
						map.put(keys[i], strs[i]);
					}
				}
				maps[t] = map;
				list.add(maps[t]);
				t++;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 
	 * <pre>
	 * StringUtil.isNumeric(null)   = false
	 * StringUtil.isNumeric("")     = true
	 * StringUtil.isNumeric("  ")   = false
	 * StringUtil.isNumeric("123")  = true
	 * StringUtil.isNumeric("12 3") = false
	 * StringUtil.isNumeric("ab2c") = false
	 * StringUtil.isNumeric("12-3") = false
	 * StringUtil.isNumeric("12.3") = false
	 * </pre>
	 * 
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static boolean compareLarger(String arg1, String arg2) {
		if (Integer.parseInt(arg1) > Integer.parseInt(arg2)) {
			return true;
		}
		return false;
	}

	/**
	 * <pre>
	 * StringUtil.equals(null, null)   = true
	 * StringUtil.equals(null, "abc")  = false
	 * StringUtil.equals("abc", null)  = false
	 * StringUtil.equals("abc", "abc") = true
	 * StringUtil.equals("abc", "ABC") = false
	 * </pre>
	 * 
	 */
	public static boolean equals(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equals(str2);
	}

	/**
	 * <pre>
	 * StringUtil.isEmpty(null)      = false
	 * StringUtil.isEmpty("")        = false
	 * StringUtil.isEmpty(" ")       = true
	 * StringUtil.isEmpty("bob")     = true
	 * StringUtil.isEmpty("  bob  ") = true
	 * </pre>
	 * 
	 */
	public static boolean isNotEmpty(String str) {
		return ((str != null) && (str.length() > 0));
	}

	/**
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty("")        = true
	 * StringUtil.isEmpty(" ")       = false
	 * StringUtil.isEmpty("bob")     = false
	 * StringUtil.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	/**
	 * <pre>
	 * StringUtil.substringAfter(null, *)      = null
	 * StringUtil.substringAfter("", *)        = ""
	 * StringUtil.substringAfter(*, null)      = ""
	 * StringUtil.substringAfter("abc", "a")   = "bc"
	 * StringUtil.substringAfter("abcba", "b") = "cba"
	 * StringUtil.substringAfter("abc", "c")   = ""
	 * StringUtil.substringAfter("abc", "d")   = ""
	 * StringUtil.substringAfter("abc", "")    = "abc"
	 * </pre>
	 */
	public static String substringAfter(String str, String separator) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		if (separator == null) {
			return EMPTY_STRING;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return EMPTY_STRING;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * <pre>
	 * StringUtil.equalsIgnoreCase(null, null)   = true
	 * StringUtil.equalsIgnoreCase(null, "abc")  = false
	 * StringUtil.equalsIgnoreCase("abc", null)  = false
	 * StringUtil.equalsIgnoreCase("abc", "abc") = true
	 * StringUtil.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equalsIgnoreCase(str2);
	}

	/**
	 * <pre>
	 * StringUtil.toUpperCase(null)  = null
	 * StringUtil.toUpperCase("")    = ""
	 * StringUtil.toUpperCase("aBc") = "ABC"
	 * </pre>
	 */
	public static String toUpperCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toUpperCase();
	}

	/**
	 * <pre>
	 * StringUtil.toLowerCase(null)  = null
	 * StringUtil.toLowerCase("")    = ""
	 * StringUtil.toLowerCase("aBc") = "abc"
	 * </pre>
	 */
	public static String toLowerCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toLowerCase();
	}
}
