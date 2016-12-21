package com.xss.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.xss.web.cache.SettingCache;
import com.xss.web.model.Setting;
import com.xss.web.util.SpringContextHelper;

public class BaseFilter implements Filter {

	String [] languages={"ASP.NET","ASP","PHP/5.4.27","JScript","VB.NET","VBScript","CGI","Python","Perl","JAVA","ELanguage"};
	List<String> languageList=Arrays.<String>asList(languages);
	
	String [] servers={"Microsoft-IIS/10.0","Microsoft-IIS/9.0","Microsoft-IIS/9.5","Microsoft-IIS/3.0","Microsoft-IIS/3.5",
			"Microsoft-IIS/2.0","Microsoft-IIS/2.5",
			"WebSOS-Server/2.0","WebSOS-Server/3.0","WebSOS-Server/9.0",
			"Hacker-Server/2.0","Hacker-Server/3.0","Hacker-Server/4.0","Hacker-Server/8.0","Hacker-Server/9.0",
			"Hacker-Server/2.5","Hacker-Server/3.5","Hacker-Server/4.5","Hacker-Server/8.5","Hacker-Server/9.5",
			"ASP-Server/2.5","ASP-Server/3.5","ASP-Server/4.5","ASP-Server/5.5",
			"Xampp-Server/2.5","Xampp-Server/3.5","Xampp-Server/5.5","Xampp-Server/6.0","Xampp-Server/8.5",
	};
	List<String> serverList=Arrays.<String>asList(servers);
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Collections.shuffle(languageList);
		String XPBy=languageList.get(0);
		response.setHeader("X-Powered-By", XPBy);
		Collections.shuffle(serverList);
		String server=serverList.get(0);
		response.setHeader("Server", server);
		// 加载系统设置
		loadSetting(request);
		// 加载根目录
		loadBasePath(request);
		arg2.doFilter(arg0, arg1);
	}

	private String[] exculdeUrls;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludes = filterConfig.getInitParameter("excludes");
		if (StringUtils.isNotBlank(excludes))
			exculdeUrls = StringUtils.split(excludes, ",");
	}

	private void loadSetting(HttpServletRequest request) {
		SettingCache settingCache = (SettingCache) SpringContextHelper
				.getBean("settingCache");
		Setting setting = settingCache.loadSetting();
		request.setAttribute("setting", setting);
	}

	private void loadBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme()
				+ "://"
				+ request.getServerName()
				+ (request.getServerPort() == 80 ? "" : ":"
						+ request.getServerPort()) + path + "/";
		request.getSession().setAttribute("basePath", basePath);
		request.setAttribute("basePath", basePath);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
