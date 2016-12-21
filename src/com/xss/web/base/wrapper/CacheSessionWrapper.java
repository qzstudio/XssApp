package com.xss.web.base.wrapper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.xss.web.base.cache.CacheClient;
import com.xss.web.enm.CacheKeyEnum;
import com.xss.web.util.JUUIDUtil;
import com.xss.web.util.SpringContextHelper;
import com.xss.web.util.StringUtils;

@SuppressWarnings("deprecation")
public class CacheSessionWrapper  implements HttpSession{
	CacheClient cacheClient;
	Map<String, Object> session;
	HttpServletRequest orgRequest;
	static String cookieName = "XSSAPP_SHELL";
	String sessionKey;
	public CacheSessionWrapper(){
		
	}
	public CacheSessionWrapper(HttpServletRequest request,
			HttpServletResponse response) {
		this.orgRequest = request;
		this.cacheClient = (CacheClient) SpringContextHelper
				.getBean("cacheClient");
		this.sessionKey = CacheKeyEnum.SYS_SESSION_KEY + "_"
				+ getCookieId(response);
	}

	@Override
	public Object getAttribute(String arg0) {
		return getSession(orgRequest).get(arg0);
	}

	public Map<String, Object> getSession(HttpServletRequest request) {
		Map<String, Object> session = (Map<String, Object>) cacheClient.getCache(sessionKey);
		if (session != null) {
			cacheClient.addCache(sessionKey, session, 60 * 30);
			return session;
		}
		session =new HashMap<String, Object>();
		return session;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return null;
	}
	@Override
	public long getCreationTime() {
		return 0;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public long getLastAccessedTime() {
		return 0;
	}

	@Override
	public int getMaxInactiveInterval() {
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}


	@Override
	public void putValue(String arg0, Object arg1) {
		Map<String, Object> session = getSession(orgRequest);
		session.put(arg0, arg1);
		cacheClient.addCache(sessionKey, session, 60 * 30);
	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		Map<String, Object> session = getSession(orgRequest);
		session.put(arg0, arg1);
		cacheClient.addCache(sessionKey, session, 60 * 30);
	}

	@Override
	public void setMaxInactiveInterval(int arg0) {
		
	}

	public String getCookieId(HttpServletResponse response) {
		String cookieId = (String) orgRequest.getSession().getAttribute(cookieName);
		if (StringUtils.isNullOrEmpty(cookieId)) {
			cookieId = getCookieId();
		}
		if (StringUtils.isNullOrEmpty(cookieId)) {
			cookieId = JUUIDUtil.createUuid();
			Cookie cook = new Cookie(cookieName, cookieId);
			cook.setHttpOnly(true);
			response.addCookie(cook);
			orgRequest.getSession().setAttribute(cookieName, cookieId);
		}
		return cookieId;
	}

	public String getCookieId() {
		Cookie[] cookies = orgRequest.getCookies();
		if (StringUtils.isNullOrEmpty(cookies)) {
			return null;
		}
		for (Cookie c : cookies) {
			if (c.getName().equalsIgnoreCase(cookieName)) {
				System.out.println("SESSIONID:" + c.getValue());
				return c.getValue();
			}
		}
		return null;
	}
	@Override
	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getValueNames() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeValue(String name) {
		// TODO Auto-generated method stub
		
	}
}
