package com.xss.web.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseRunnableEntity {
	public void main1() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method cc = new BaseRunnableEntity().getClass().getMethod("main1");// 获取方法
		// String.class如果没有就是new Class[0]
		cc.invoke(new BaseRunnableEntity());

	}

	public static void main(String[] args) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		new BaseRunnableEntity().main1();
	}
}
