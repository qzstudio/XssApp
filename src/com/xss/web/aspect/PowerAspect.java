package com.xss.web.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xss.web.annotation.Power;
import com.xss.web.cache.MenuCache;
import com.xss.web.cache.RoleCache;
import com.xss.web.cache.base.BaseCache;
import com.xss.web.enm.PowerEnum;
import com.xss.web.entity.MsgEntity;
import com.xss.web.model.Admin;
import com.xss.web.model.Menus;
import com.xss.web.model.Role;
import com.xss.web.util.JSONWriter;
import com.xss.web.util.PropertUtil;
import com.xss.web.util.RequestUtil;
import com.xss.web.util.SpringContextHelper;
import com.xss.web.util.StringUtils;

@Aspect
@Component
public class PowerAspect {

	@Resource
	private BaseCache baseCache;

	@SuppressWarnings("unchecked")
	@Around("execution(* com.xss.web.controllers..*.*(..)) && @annotation(com.xss.web.annotation.Power)")
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
			Power handle = method.getAnnotation(Power.class);
			if (handle == null||StringUtils.isNullOrEmpty(handle.value())) {
				return pjp.proceed();
			}
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getResponse();
			Admin admin = (Admin) RequestUtil.getAdmin(request);
			if (!RequestUtil.isAdminLogin(request)) {
				printMsg(request, response, handle, new MsgEntity(-1, "无权操作"));
				return null;
			}
			RoleCache roleCache = (RoleCache) SpringContextHelper
					.getBean("roleCache");
			Role role = roleCache.loadRole(admin.getRole().getId());
			MenuCache menuCache = (MenuCache) SpringContextHelper
					.getBean("menuCache");
			List<Menus> roleMenus = menuCache.loadRoleMenus(role);
			if(StringUtils.isNullOrEmpty(roleMenus)){
				printMsg(request, response, handle, new MsgEntity(-1, "无权操作"));
				return null;
			}
			List<Menus> childMenus=new ArrayList<Menus>();
			for (Menus menu:roleMenus) {
				if(StringUtils.isNullOrEmpty(menu.getChildMenus())){
					continue;
				}
				childMenus.addAll(menu.getChildMenus());
			}
			List<String> codes = (List<String>) PropertUtil.getFieldValues(
					childMenus, "code");
			if(StringUtils.isNullOrEmpty(codes)){
				printMsg(request, response, handle, new MsgEntity(-1, "无权操作"));
				return null;	
			}
			if(!codes.contains(handle.value())){
				printMsg(request, response, handle, new MsgEntity(-1, "无权操作"));
				return null;
			}
			Object result = pjp.proceed();
			return result;
		} finally {
			sw.stop();
		}
	}
	// 打印返回消息
	protected void printMsg(HttpServletRequest req,HttpServletResponse res,Power handle, Object obj) {
		try {
			if(handle.resType()==PowerEnum.JSON){
				String msg = JSONWriter.write(obj);
				res.getWriter().print(msg);
				return;
			}
			if(handle.resType()==PowerEnum.PAGE){
				req.getRequestDispatcher("/WEB-INF/jsp/back/no_power.jsp").forward(req, res);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // 打印返回消息

}
