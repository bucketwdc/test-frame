package com.test.frame.common.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;

import com.test.frame.common.CsvProviderUtils.CsvDataProvideBase;


/**
 * 动态编译Java 源文件
 * 
 */
public class CompilerTest {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(CompilerTest.class);

	public static void main(String[] args) throws Exception {
		//D:\workspace\HomsAts\src\test\java\ligang13009\hundsun\com\TC\TCNormalTest.java
		String filePath = "D:\\workspace\\HomsAts\\src\\test\\java\\";
		oCompiler(filePath, "ligang13009\\hundsun\\com\\TC", "TCNormalTest");
	}

	public static void oCompiler(String oFilePath, String packageName,
			String className) throws Exception {
		String filePath = oFilePath + packageName + "\\" + className + ".java";
		String source = FileUtils.readFile(filePath);
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		// 建立DiagnosticCollector对象
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				diagnostics, null, null);
		StringSourceJavaObject sourceObject = new CompilerTest.StringSourceJavaObject(
				className, source);
		Iterable<? extends JavaFileObject> fileObjects = Arrays
				.asList(sourceObject);

		// 获取编译类根路径，不然会报找不到类的错误
		String path = Class.class.getClass().getResource("/").getPath();
		Iterable<String> options = Arrays.asList("-d", path);

		CompilationTask task = compiler.getTask(null, fileManager, diagnostics,
				options, null, fileObjects);

		boolean result = task.call();
		for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics())
			System.out.printf("Code: %s%n" + "Kind: %s%n" + "Position: %s%n"
					+ "Start Position: %s%n" + "End Position: %s%n"
					+ "Source: %s%n" + "Message: %s%n", diagnostic.getCode(),
					diagnostic.getKind(), diagnostic.getPosition(),
					diagnostic.getStartPosition(), diagnostic.getEndPosition(),
					diagnostic.getSource(), diagnostic.getMessage(null));
		if (result) {
			logger.info("--------------------------编译成功.------------------------");

			ClassLoader loader = CompilerTest.class.getClassLoader();
			try {
				Class<?> clazz = loader.loadClass(packageName
						.replace("\\", ".") + "." + className);
//				Object o = clazz.newInstance();
				Method method = clazz.getMethods()[0];
				CsvDataProvideBase oCsvDataProvideBase=new CsvDataProvideBase("d:\\workspace\\HomsAts\\");
				Iterator<Object[]> oCsvDataProvider =oCsvDataProvideBase 
						.getCsvTestData(method);
				//获取方法的参数列表
				int len = method.getParameterTypes().length;
				List<String[]> oList = new ArrayList<String[]>();
				while (oCsvDataProvider.hasNext()) {
					String[] array = new String[len];
					int i = 0;
					Object[] str = oCsvDataProvider.next();
					for (Object object : str) {
						logger.info("测试数据如下: " + object);
						array[i] = object.toString();
						i = i + 1;
					}
					oList.add(array);
				}
//				for (String[] string : oList) {
//					Object value = method.invoke(o, string);
//					logger.info(value);
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static class StringSourceJavaObject extends SimpleJavaFileObject {

		private String content = null;

		public StringSourceJavaObject(String name, String content)
				throws URISyntaxException {
			super(URI.create("string:///" + name.replace('.', '/')
					+ Kind.SOURCE.extension), Kind.SOURCE);
			this.content = content;
		}

		public CharSequence getCharContent(boolean ignoreEncodingErrors)
				throws IOException {
			return content;
		}
	}
}