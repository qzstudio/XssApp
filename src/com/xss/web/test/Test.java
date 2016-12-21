package com.xss.web.test;

import java.lang.reflect.Method;

import sun.misc.BASE64Decoder;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class Test {

	public Test(){}
	public static void main(String[] args) throws Exception {
		ClassPool cp = ClassPool.getDefault();
		CtClass cc = cp.get("com.xss.web.test.Test2");
		CtMethod m =CtNewMethod.make("public void sayhello(){}", cc);
		String code="U3lzdGVtLm91dC5wcmludGxuKCJoZWxsbyIpOw==";
		code=new String(new BASE64Decoder().decodeBuffer(code));
		m.setBody(code);
		cc.addMethod(m);
		cc.toClass();
		Test2 h=Test2.class.newInstance();
		Method cm=Test2.class.getDeclaredMethod("sayhello");
		cm.invoke(h);
	}
	
}
