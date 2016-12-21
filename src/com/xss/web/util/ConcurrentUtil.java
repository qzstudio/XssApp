package com.xss.web.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.xss.web.base.cache.CacheTimerHandler;

/**
 * @remark 并发锁方法调用工具类
 * @author 公子
 * @time 2016-06-11
 */
public class ConcurrentUtil {

	protected static final Logger logger = Logger
			.getLogger(ConcurrentUtil.class);

	/**
	 * 基于缓存的并发锁反射调用方法
	 * 
	 * @param targeObj
	 *            目标对象
	 * @param methodName
	 *            目标方法名
	 * @param lockServer
	 *            并发锁服务器
	 * @param lockKey
	 *            并发锁KEY
	 * @param paras
	 *            方法参数
	 * @return
	 */
	public static Object invokMethod(Object targeObj, String methodName,
			String lockKey, Object... paras) {
		lockKey = "LOCK_" + lockKey;
		//获取进入许可证
		if (!checkPermit(lockKey)) {
			return null;
		}
		try {
			// 寻找方法
			Method method = getTargeMethod(targeObj.getClass()
					.getDeclaredMethods(), methodName, paras);
			if (StringUtils.isNullOrEmpty(method)) {
				method = getTargeMethod(targeObj.getClass().getMethods(),
						methodName, paras);
			}
			if (StringUtils.isNullOrEmpty(method)) {
				return null;
			}
			method.setAccessible(true);
			// 执行方法
			return method.invoke(targeObj, paras);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			synchronized (ConcurrentUtil.class) {
				CacheTimerHandler.removeCache(lockKey);
			}
		}
		return null;
	}

	private static boolean checkPermit(String lockKey) {
		Integer status = null;
		for (int attempt = 0; attempt < 15000; attempt++) {
			synchronized (ConcurrentUtil.class) {
				status = (Integer) CacheTimerHandler.getCache(lockKey);
				if (StringUtils.isNullOrEmpty(status)) {
					CacheTimerHandler.addCache(lockKey, 1, 45);
					return true;
				}
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 从对象中获取目标方法
	 * 
	 * @param methods
	 *            方法数组
	 * @param methodName
	 *            方法名称
	 * @param paras
	 *            参数列表
	 * @return
	 */
	private static Method getTargeMethod(Method[] methods, String methodName,
			Object... paras) {
		String key = "METHOD_HANDLE_" + methodName;
		Method method = (Method) CacheTimerHandler.getCache(key);
		if (!StringUtils.isNullOrEmpty(method)) {
			return method;
		}
		for (Method m : methods) {
			if (isTargeMethod(m, methodName, paras)) {
				CacheTimerHandler.addCache(key, m, 600);
				return m;
			}
		}
		return null;
	}

	/**
	 * 判断目标是否是当前方法
	 * 
	 * @param method
	 *            当前方法
	 * @param methodName
	 *            目标方法名
	 * @param paras
	 *            目标方法参数列表
	 * @return
	 */
	private static boolean isTargeMethod(Method method, String methodName,
			Object... paras) {
		if (!method.getName().equals(methodName)) {
			return false;
		}
		Class<?>[] clas = method.getParameterTypes();
		if (StringUtils.isNullOrEmpty(clas) && StringUtils.isNullOrEmpty(paras)) {
			return true;
		}
		if (StringUtils.isNullOrEmpty(clas) || StringUtils.isNullOrEmpty(paras)) {
			return false;
		}
		if (clas.length != paras.length) {
			return false;
		}
		for (int i = 0; i < clas.length; i++) {
			if (!clas[i].isAssignableFrom(paras[i].getClass())) {
				return false;
			}
		}
		return true;
	}
}
