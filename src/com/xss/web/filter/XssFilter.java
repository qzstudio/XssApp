package com.xss.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xss.web.base.wrapper.XssHttpServletRequestWrapper;
import com.xss.web.util.RequestUtil;
import com.xss.web.util.StringUtils;

/**
 *
 * 拦截防止XSS跨站脚本攻击
 *
 * @author WebSOS
 * @time 2015-06-09
 */
public class XssFilter implements Filter {

	private String[] exculdeUrls;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludes = filterConfig.getInitParameter("excludes");
		if (!StringUtils.isNullOrEmpty(excludes))
			exculdeUrls = excludes.split(",");
	}

	public void destroy() {
	}

	/**
	 * 過濾
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		chain.doFilter(new XssHttpServletRequestWrapper(request,response), resp);
	}

	
}