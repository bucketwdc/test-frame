package com.test.frame.common.test;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.testng.annotations.Test;

public class TestFile {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void test() throws Exception {
		String source = "package com.testPlatform.test;"
				+ "import org.apache.log4j.Logger;"
				+ "import org.testng.annotations.Test;"
				+ "import com.common.ats.TCRunConfigManage.TCRunnerBase;"
				+ "public class TCNormalTest extends TCRunnerBase {"
				+ "private static final Logger logger = Logger.getLogger(TCNormalTest.class);"
				+ "@Test(dataProvider = \"oCsvDataProviderConfig\")"
				+ "public static void test(String userName,String passWord) {"
				+ "logger.info(userName+\"---- Str2: \" + passWord);" + "}"
				+ "}";
		String fileName = System.getProperty("user.dir")
				+ "/src/test/java/com/testPlatform/test/TCNormalTest.java";
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write(source);
		fw.flush();
		fw.close();

		//compile
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null,
				null, null);
		Iterable units = fileMgr.getJavaFileObjects(fileName);
		CompilationTask t = compiler.getTask(null, fileMgr, null, null, null,
				units);
		t.call();
		fileMgr.close();
		
		  //load into memory and create an instance
        URL[] urls = new URL[] {new URL("file:/" + System.getProperty("user.dir") +"/src/test/java/")};
        URLClassLoader ul = new URLClassLoader(urls);
        Class<?> c = ul.loadClass("com.testPlatform.test.TCNormalTest");
        System.out.println(c);
        Method method = c.getMethod("test", String[].class);
        Object value = method.invoke(null,
				new Object[] { new String[] {} });
		System.out.println(value);
         
	}
}
