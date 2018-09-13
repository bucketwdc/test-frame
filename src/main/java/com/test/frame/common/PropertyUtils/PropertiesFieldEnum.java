package com.test.frame.common.PropertyUtils;

/**   
*    
* 项目名称：HomsAts   
* 类  名  称：PropertiesFieldEnum   
* 类  描  述：   定义一个枚举用来管理共享的标签信息
* @version    
*    
*/ 
public enum PropertiesFieldEnum {

	TD_SQL("TD_SQL"), TD_TableName("TD_TableName"), TD_FilesPath("TD_FilesPath");

	private String PropertiesFieldEnum;

	private PropertiesFieldEnum(String rss) {
		this.PropertiesFieldEnum = rss;
	}

	public String getValue() {
		return PropertiesFieldEnum;
	}

	public void setValue(String propertiesFieldEnum) {
		PropertiesFieldEnum = propertiesFieldEnum;
	}
}
