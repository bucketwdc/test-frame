package com.test.frame.common.DBUtils;

/**   
 *    
 * 类  名  称：JdbcDriverType   
 * 类  描  述：   主要是用来枚举处oracle和mysql的驱动,实现带有构造器的枚举
 * 创  建  人：wdc   
 * @version    
 *    
 */
public enum JdbcDriverTypeEnum {
//	通过括号赋值,而且必须带有一个参构造器和一个属性跟方法，否则编译出错
	ORACLE("oracle.jdbc.driver.OracleDriver"),
	MYSQL("com.mysql.jdbc.Driver");
	
	private final String driverClass;

	private JdbcDriverTypeEnum(String driverClass) {
		this.driverClass = driverClass;
	}
	//构造器默认也只能是private, 从而保证构造函数只能在内部使用
	public String getDriverClass() {
		return driverClass;
	}

}
