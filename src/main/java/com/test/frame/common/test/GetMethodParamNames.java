package com.test.frame.common.test;

import java.util.ArrayList;
import java.util.Iterator;


import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class GetMethodParamNames {
	/**
	 * 
	 * <p>
	 * 获取方法参数名称
	 * </p>
	 * 
	 * @param cm
	 * @return
	 */
	private static ArrayList<String> getMethodParamNames(CtMethod cm) {
		CtClass cc = cm.getDeclaringClass();
		MethodInfo methodInfo = cm.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
				.getAttribute(LocalVariableAttribute.tag);
		if (attr == null) {
			try {
				throw new Exception(cc.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<String> objArray = new ArrayList<String>();
		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
		int len = 0;
		try {
			len = cm.getParameterTypes().length;
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < len; i++) {
			objArray.add(i, attr.variableName(i + pos));
		}
		return objArray;
	}

	/**
	 * 获取方法参数名称，匹配同名的某一个方法
	 * 
	 * @param oClass
	 * @param method
	 * @return
	 * @throws NotFoundException
	 *             如果类或者方法不存在
	 * @throws MissingLVException
	 *             如果最终编译的class文件不包含局部变量表信息
	 */
	public static ArrayList<String> getMethodParamNames(Class<?> oClass,
			String method) {

		ClassPool pool = ClassPool.getDefault();
		CtClass cc;
		CtMethod cm = null;
		try {
			cc = pool.get((oClass).getName());
			cm = cc.getDeclaredMethod(method);
		} catch (NotFoundException e) {
		}
		return getMethodParamNames(cm);
	}

	@SuppressWarnings("serial")
	public static ArrayList<String> getMethodParams(Class<?> oClass,
			String method, int flag) {
		ArrayList<String> objArray = getMethodParamNames(oClass, method);
		Iterator<String> iterator = objArray.iterator();
		ArrayList<String> arrayList = new ArrayList<String>() {
			{
				add("id");
				add("desc");
				add("caseId");
			}
		};
		if (flag == 1) {
			arrayList.add("oIClient");
		}
		if (flag == 0) {
			arrayList.add("operator_no");
			arrayList.add("id");
			arrayList.add("subId");
			arrayList.add("password");
		}
		if (flag == 2) {
			arrayList.add("oIClient");
		}
		while (iterator.hasNext()) {
			String str = iterator.next();
			if (arrayList.contains(str)) {
				iterator.remove();
			}
		}
		return objArray;
	}

}
