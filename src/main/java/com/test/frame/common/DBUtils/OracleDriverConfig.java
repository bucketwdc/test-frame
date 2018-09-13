package com.test.frame.common.DBUtils;

/**
 * 
 * 数据库配置信息主要是包含ojdbc的信息 ：
 * @version
 * 
 */
public class OracleDriverConfig {

	private String oracleDriver = "oracle.jdbc.driver.OracleDriver"; // 驱动
	private String dbUrl;// 连接串
	private String name;// 数据库访问用用户名
	private String password;// 数据库访问用户对应密码
	private String schema;

	public String getOracleDriver() {
		return oracleDriver;
	}

	public void setOracleDriver(String oracleDriver) {
		this.oracleDriver = oracleDriver;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public static String printDriverInfo(OracleDriverConfig odc) {
		String oralceDriverInfo = "数据库配置信息如下： \r\n        数据库连接串：" + odc.getDbUrl()
				+ "  \r\n        数据库访问名:  " + odc.getName() + "  \r\n        数据库访问密码： "
				+ odc.getPassword() + "\r\n        数据库对应Schema: " + odc.getSchema();
		return oralceDriverInfo;
	}

}
