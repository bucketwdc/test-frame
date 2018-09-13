package com.test.frame.common.DBUtils;

import org.apache.commons.lang3.StringUtils;

import com.test.frame.common.PropertyUtils.LoadPropertiesFile;
import com.test.frame.common.PropertyUtils.PropertyConfig;
import com.test.frame.common.configparautil.Configration;
import com.test.frame.common.logutil.TcRunLog;


/**   
 *    
 * 类  名  称：GetDbConfigInfo   
 * 类  描  述：  用来获取数据库配置文件
 * 创  建  人：wdc   
 * @version    
 *    
 */
public class GetDbConfigInfo {
	/** 配置项缓存 */
	private static Configration testConfigs;

	/** db.conf的目录 */
	public static final String DB_config_DIR = "DB_Conf/";
	/** 配置数据源文件 */
	public static final String DB_File_Key = "DB_File_Key";

	public static final String TIME_OUT = "test_run_time";

	/** 配置中的表分割符 */
	public static String DB_TABLENAME_SPIT_REGEX = ",";

	/** 配置项计数，计算出共有多少组数据库配置 */
	public static int dbConfNumber = -1;

	public static void readDBconfInfo(Configration testConfigs) {
		//获取文件名称,根据LoadConfigFile类中定义的文件Ats_config_properties_name中的key获取文件名称
		String oDB_File = testConfigs.getPropertyValue(DB_File_Key);
		if (null == oDB_File) {
			TcRunLog.error("配置文件ats_config.properties中未发现配置项字段：【" + DB_File_Key
					+ "】,请配置该字段信息");
			return;
		}
		if ("" != oDB_File) {
			oDB_File = oDB_File.trim().toLowerCase();
			//db配置文件的地址是存放到DB_config_DIR这个文件夹下面
			String oEnv_Path = testConfigs.getPropertyValue("Env_Path");
			int flag = 0;
			if (StringUtils.isBlank(oEnv_Path) || null == oEnv_Path
					|| "" == oEnv_Path) {
				oEnv_Path = DB_config_DIR + oDB_File;

			} else {
				oEnv_Path = oEnv_Path + DB_config_DIR + oDB_File;
				flag = 1;
			}
			LoadPropertiesFile.oLoadPropertiesFile(oEnv_Path, flag);
		} else {
			TcRunLog.error("数据库配置文件未找到,请确认是否已经配置了对应的DB_File_Key！");
			return;
		}
		dbConfNumber = 1;
		while (testConfigs.getPropertyValue("ext" + dbConfNumber + "_db_name") != null) {
			dbConfNumber++;
		}

	}

	/**   
	*    
	* 方法名称：oGetDbConfigInfo   
	* 方法描述：  通过读取DB配置文件进行数据库配置信息的获取，
	*          dbConfigKey为空的的情况下，根据表明进行匹配读取，否则直接读取对应配置项
	*          如果都为空，则直接返回。
	* 参数列表：@param tableName  需要连接的表
	* 参数列表：@param dbConfigKey 需要连接的对应的配置
	* 参数列表：@return 对应的配置信息
	* 返  回  值：OracleDriverConfig 
	* @version  
	* @throws  
	*    
	*/
	public static OracleDriverConfig oGetDbConfigInfo(String tableName,
			String dbConfigKey) {
		OracleDriverConfig oracleDriverConfig = new OracleDriverConfig();
		TcRunLog.debug("当前连接的数据库配置信息是: " + tableName + "----" + dbConfigKey);
		if (StringUtils.isEmpty(tableName) && StringUtils.isEmpty(dbConfigKey)) {
			return oracleDriverConfig;
		}
		//判断缓存中是否已经加载了配置信息，如果没有加载调用初始化程序重新加载
		testConfigs = PropertyConfig.testConfigs;
		if (testConfigs == null) {
			PropertyConfig.initConfigs(null);
			testConfigs = PropertyConfig.testConfigs;
		}

		//定义配置信息字段
		String dbUrl = "";//连接串
		String dbName = "";//数据库访问用用户名
		String dbPassword = "";//数据库访问用户对应密码
		String dbSchema = "";
		//配置信息表名字，不区分大小写
		if (!tableName.isEmpty()) {
			tableName = tableName.toUpperCase();
		}
		//定义下db配置信息的字段，并读取这些字段信息内容
		//如果制定的DB配置字段存在的话，就直接读取
		Boolean b = null != dbConfigKey && !dbConfigKey.isEmpty()
				&& "" != dbConfigKey;
		if (b) {
			//去除掉文件中可能存在的空格，并把配置项转换成小写
			dbConfigKey = dbConfigKey.trim().toLowerCase();
			//定义配置项的内容都是以ext开头
			if (dbConfigKey.startsWith("ext")) {
				//获取对应的配置项的字段信息内容
				dbUrl = testConfigs.getPropertyValue(dbConfigKey + "_db_url");
				dbName = testConfigs.getPropertyValue(dbConfigKey + "_db_name");
				dbPassword = testConfigs.getPropertyValue(dbConfigKey
						+ "_db_password");
				dbSchema = testConfigs.getPropertyValue(dbConfigKey
						+ "_db_schema");
			}

		} else {
			if (judgeByTableNameIsExist(tableName,
					testConfigs.getPropertyValue("db_tablename"))) {
				//获取对应的配置项的字段信息内容
				dbUrl = testConfigs.getPropertyValue("db_url");
				dbName = testConfigs.getPropertyValue("db_name");
				dbPassword = testConfigs.getPropertyValue("db_password");
				dbSchema = testConfigs.getPropertyValue("db_schema");
			} else {
				//没有指定具体的配置信息，则根据表进匹配处理
				for (int i = 0; i < dbConfNumber; i++) {

					if (judgeByTableNameIsExist(
							tableName,
							testConfigs.getPropertyValue("ext" + i
									+ "_db_tablename"))) {
						//获取对应的配置项的字段信息内容
						dbUrl = testConfigs.getPropertyValue("ext" + i
								+ "_db_url");
						dbName = testConfigs.getPropertyValue("ext" + i
								+ "_db_name");
						dbPassword = testConfigs.getPropertyValue("ext" + i
								+ "_db_password");
						dbSchema = testConfigs.getPropertyValue("ext" + i
								+ "_db_schema");
					}
				}
			}
		}
		if (dbName == "" || null == dbName) {
			TcRunLog.info("配置的数据表没有对应的配置信息，请检查表是否存在对应的配置内容");
			return null;
		}
		oracleDriverConfig.setDbUrl(dbUrl);
		oracleDriverConfig.setName(dbName);
		oracleDriverConfig.setPassword(dbPassword);
		oracleDriverConfig.setSchema(dbSchema);
		return oracleDriverConfig;

	}

	/**   
	*    
	* 方法名称：judgeByTableNameIsExist   
	* 方法描述：  根据传入的表名以及配置项中的表名信息
	*         判断当前表是否存在对应的配置如果存在就返回true
	* 参数列表：@param tableName
	* 参数列表：@param db_name
	* 参数列表：@return
	* 返  回  值：boolean 
	* @version  
	* @throws  
	*    
	*/
	public static boolean judgeByTableNameIsExist(String tableName,
			String db_name) {
		if (StringUtils.isBlank(db_name) || StringUtils.isBlank(tableName)) {
			return false;
		}
		String[] tables = db_name.toUpperCase().split(",");

		for (String string : tables) {
			if (string.equals(tableName)) {
				return true;
			}
		}
		return false;
	}

}
