package com.xss.web.aspect;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.xss.web.annotation.CacheHandle;
import com.xss.web.annotation.DelCacheHandle;
import com.xss.web.cache.base.BaseCache;
import com.xss.web.util.ConcurrentUtil;
import com.xss.web.util.PropertUtil;
import com.xss.web.util.StringUtils;

@Aspect
@Component
public class CacheAspect {


	@Resource
	private BaseCache baseCache;

	@Around("execution(* com.xss.web.cache..*.*(..)) && @annotation(com.xss.web.annotation.CacheHandle)")
	public Object procCache(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			// AOP获取方法执行信息
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method method = methodSignature.getMethod();
			if (method == null) {
				return pjp.proceed();
			}
			// 获取注解
			CacheHandle handle = method.getAnnotation(CacheHandle.class);
			if (handle == null) {
				return pjp.proceed();
			}
			// 封装缓存KEY
			StringBuilder key = new StringBuilder();
			String methodKey = PropertUtil.getMethodClass(method).getName()
					+ "." + method.getName();
			methodKey = methodKey.replace(".", "_");
			key.append(methodKey).append(handle.key());
			Object[] args = pjp.getArgs();
			if (!StringUtils.isNullOrEmpty(args)) {
				try {
					key.append(StringUtils.getBeanKey(args));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Integer cacheTimer = ((handle.validTime() == 0) ? 24 * 3600
					: handle.validTime());
			// 获取缓存
			try {
				Object result = baseCache.getCache(key.toString());
				if (!StringUtils.isNullOrEmpty(result)) {
					return result;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//单例调用。减少数据库压力
			return ConcurrentUtil.invokMethod(this,
					"getProceedingJoinPointResult", key.toString(),
					key.toString(), cacheTimer, pjp);
		} finally {
			sw.stop();
		}
	}

	public Object getProceedingJoinPointResult(String key, Integer cacheTimer,
			ProceedingJoinPoint pjp) throws Throwable {
		// 获取缓存
		try {
			Object result = baseCache.getCache(key.toString());
			if (!StringUtils.isNullOrEmpty(result)) {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object result = pjp.proceed();
		if (!StringUtils.isNullOrEmpty(result)) {
			baseCache.addCache(key.toString(), result, cacheTimer);
		}
		return result;
	}

	/**
	 * 是否存在注解，则作内存处理
	 * 
	 * @param joinPoint
	 * @param DelCacheHandle
	 * @return
	 * @throws Exception
	 */
	private DelCacheHandle getDelCacheHandle(ProceedingJoinPoint joinPoint)
			throws Exception {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		if (method != null) {
			return method.getAnnotation(DelCacheHandle.class);
		}
		return null;
	}
	
	@Around("execution(* com.xss.web.cache..*.*(..)) && @annotation(com.xss.web.annotation.DelCacheHandle)")
	public Object delCache(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// 启动监听
			sw.start(pjp.getSignature().toShortString());
			DelCacheHandle handle = getDelCacheHandle(pjp);
			Object result = pjp.proceed();
			if (handle != null) {
				if (handle.isModuel()) {// 按照模块删除
					for (String key : handle.keys()) {
						baseCache.removeCacheFuzzy(key);
					}
					return result;
				}
				// 按照key值删除
				Signature signature = pjp.getSignature();
				MethodSignature methodSignature = (MethodSignature) signature;
				for (String key : handle.keys()) {
					baseCache.removeCache(key);
					System.out.println("删除缓存:" + key);
				}
			}
			return result;
		} finally {
			sw.stop();
		}
	}
}
