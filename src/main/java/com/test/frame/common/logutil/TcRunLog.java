package com.test.frame.common.logutil;

import org.apache.log4j.Logger;

public class TcRunLog {
	public static final Logger info = Logger.getLogger("TestCaseRunInfoLogger");
	public static final Logger error = Logger.getLogger("TestCaseRunErrorLogger");
	public static final Logger debug = Logger.getLogger("TestCaseRuncDebugLogger");

	public TcRunLog() {
	}

	/**
	 * 一般情况记录到/logs/runTcInfo.txt
	 */
	public static void info(String infomation) {
		info.info(infomation);
	}

	/**
	 * 错误信息记录到/logs/runTcErrorLog.txt
	 */
	public static void error(String infomation) {
		error.error(infomation);
	}

	/**
	 * 错误信息记录到logs/configInfo/configLog.txt
	 */
	public static void debug(String infomation) {
		debug.info(infomation);
	}

	public static boolean isDebugEnabled() {
		// TODO Auto-generated method stub
		return info.isDebugEnabled();
	}
}
