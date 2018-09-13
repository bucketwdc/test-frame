package com.test.frame.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**   
*    
* 类  名  称：ConsoleTable   
* 类  描  述：   生成控制台表格
* 创  建  人：wdc   
* 创建时间：2015年2月25日 下午2:23:26   
* 修  改  人：wdc   
* 修改时间：2015年2月25日 下午2:23:26   
* 修改备注：   
* @version    
*    
*/
public class ConsoleTable {

	private List<List<?>> rows = new ArrayList<List<?>>();

	private int colum;//列

	private int rowLen=20;
	private int[] columLen;//列长度
	private static int margin = 2;//左偏移

	public ConsoleTable(int colum, boolean printHeader) {
		this.colum = colum;
		this.columLen = new int[colum];
	}

	@SuppressWarnings("rawtypes")
	public void appendRow() {
		List row = new ArrayList(colum);
		rows.add(row);
	}

	public static int max(int[] values) {
		int max = values[0];
		for (int v : values) {
			if (max < v)
				max = v;
		}
		return max;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ConsoleTable appendColum(Object value) {
		if (value == null) {
			value = "NULL";
		}
		List row = rows.get(rows.size() - 1);
		row.add(value);
		int len = value.toString().getBytes().length;
	
		if (columLen[row.size() - 1] < len) {
			columLen[row.size() - 1] = len;
		}
		for (int i = 0; i < columLen.length-1; i++) {
			columLen[i] = max(columLen);
		}
		return this;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();

		int sumlen = rowLen*(rows.get(0).size());
//		for (int len : columLen) {
//			sumlen += len;
//		}
		int le=sumlen + margin * colum+ (colum - 1);
		buf.append("|")
				.append(printChar('-', le)).append("|\r\n");
		for (int ii = 0; ii < rows.size(); ii++) {
			List<?> row = rows.get(ii);
			for (int i = 0; i < colum; i++) {
				String o = "";
				if (i < row.size()) {
					o = row.get(i).toString();
				}
				int lenValue = o.getBytes().length;
				int x;
				if (isChineseChar(o)) {
					x = lenValue/2;
				} else {
					x = o.length();
				}
				if(x>=15){
					o =o.toString().substring(0, 12)+"...";
					x=o.toString().getBytes().length;
				}
				buf.append('|').append(printChar('\0', margin)).append(o);
				buf.append(printChar('\0', rowLen-margin-x));
			}
			buf.append("|\r\n");
			buf.append("|")
					.append(printChar('-', le)).append("|\r\n");
		}
		return buf.toString();
	}

	private String printChar(char c, int len) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < len; i++) {
			buf.append(c);
		}
		return buf.toString();
	}

	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}
}
