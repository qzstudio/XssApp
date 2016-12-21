package com.xss.web.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import com.xss.web.entity.BeanFieldEntity;
import com.xss.web.entity.RequestBeanEntity;
import com.xss.web.model.base.BaseModel;

/**
 * @remark HTTP工具类。
 * @author WebSOS
 * @email 644556636@qq.com
 * @blog bkkill.com
 */
public class RequestUtil {

	public static final String user_session = "curr_login_user";
	public static final String admin_session = "curr_login_admin";
	private static final AntPathMatcher MATCHER = new AntPathMatcher();
	public static void setUser(HttpServletRequest request, Object user) {
		request.getSession().setAttribute(user_session, user);
	}

	public static Object getUser(HttpServletRequest request) {
		return request.getSession().getAttribute(user_session);
	}

	public static void setAdmin(HttpServletRequest request, Object user) {
		request.getSession().setAttribute(admin_session, user);
	}

	public static Object getAdmin(HttpServletRequest request) {
		return request.getSession().getAttribute(admin_session);
	}

	public static boolean isUserLogin(HttpServletRequest request) {
		Object obj = getUser(request);
		if (!StringUtils.isNullOrEmpty(obj)) {
			return true;
		}
		return false;
	}

	public static boolean isAdminLogin(HttpServletRequest request) {
		Object obj = getAdmin(request);
		if (!StringUtils.isNullOrEmpty(obj)) {
			return true;
		}
		return false;
	}

	public static void setCode(HttpServletRequest request, Object code) {
		request.getSession().setAttribute("sys_ver_code", code);
	}

	public static Object getCode(HttpServletRequest request) {
		return request.getSession().getAttribute("sys_ver_code");
	}

	public static String getIpAddr(HttpServletRequest request) {
		
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static <T> void beanConvert(T newSource, T source) {
		try {
			BeanUtils.copyProperties(newSource, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 *            请求对象
	 * @param obj
	 *            要设置Bean的类型,传入试为: Bean.class
	 * @return
	 */
	public static Object getBeanFromRequest(HttpServletRequest request,
			Object obj, String... paraArgs) {
		try {
			// 获取页面所有的请求参数名称
			Enumeration<String> enume = request.getParameterNames();
			try {
				List<String> paras = null;
				if (!StringUtils.isNullOrEmpty(paraArgs)) {
					paras = Arrays.<String> asList(paraArgs);
				}
				List<PropertyDescriptor> fields = getBeanPropertyFields(obj
						.getClass());
				List<String> fieldNames = getFieldNameByFields(fields);
				while (enume.hasMoreElements()) {
					try {
						// 参数名称
						String propertyName = enume.nextElement();
						if (!StringUtils.isNullOrEmpty(paras)
								&& !paras.contains(propertyName)) {
							continue;
						}
						// 判断是否存在此属性
						if (fieldNames.contains(propertyName)) {
							// 获取请求值
							Object propertyValue = request
									.getParameter(propertyName);
							PropertyDescriptor currField = getFieldByName(
									fields, propertyName);
							setProperties(obj, currField.getPropertyType(),
									propertyName, propertyValue);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public static RequestBeanEntity getRequestBeanEntity(String paraNames) {
		if (StringUtils.isNullOrEmpty(paraNames)) {
			return null;
		}
		try {
			String[] strs = paraNames.split("\\.");
			RequestBeanEntity entity = new RequestBeanEntity();
			entity.setCurrParas(strs[0]);
			if (strs.length > 1) {
				if (paraNames.indexOf(".") > -1) {
					paraNames = paraNames.substring(strs[0].length() + 1,
							paraNames.length());
				} else {
					paraNames = paraNames.substring(strs[0].length(),
							paraNames.length());
				}
				entity.setEntity(getRequestBeanEntity(paraNames));
			}
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static List<String> getFieldNameByFields(
			List<PropertyDescriptor> fieldList) {
		List<String> fieldNames = new ArrayList<String>();
		for (PropertyDescriptor f : fieldList) {
			fieldNames.add(f.getName());
		}
		if (StringUtils.isNullOrEmpty(fieldNames)) {
			return null;
		}
		return fieldNames;
	}

	public static Map<String, String> getFieldMapFields(
			List<PropertyDescriptor> fieldList) {
		Map<String, String> fieldNames = new HashMap<String, String>();
		for (PropertyDescriptor f : fieldList) {
			fieldNames.put(f.getName(), f.getName());
		}
		if (StringUtils.isNullOrEmpty(fieldNames)) {
			return null;
		}
		return fieldNames;
	}

	public static Object getBeanFromRequest(HttpServletRequest request,
			Object obj) {
		return getBeanFromRequest(request, obj, null);
	}

	private static <T> T getObjectByClass(Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return t;
	}

	/**
	 * @param clazz
	 *            Class对象
	 * @param propertyName
	 *            属性名称
	 * @return true || false 检查对象中是否存在该属性名称
	 */
	private static List<PropertyDescriptor> getBeanPropertyFields(Class<?> clazz) {
		try {
			BeanInfo sourceBean = Introspector.getBeanInfo(clazz,
					java.lang.Object.class);
			PropertyDescriptor[] propertys = sourceBean
					.getPropertyDescriptors();
			return Arrays.<PropertyDescriptor> asList(propertys);
		} catch (Exception e) {
		}
		return null;

	}

	public static PropertyDescriptor getFieldByName(
			List<PropertyDescriptor> fieldList, String name) {
		if (StringUtils.isNullOrEmpty(fieldList)) {
			return null;
		}
		for (PropertyDescriptor f : fieldList) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}

	/**
	 * 根据Request获取Model。排除指定参数
	 * 
	 * @param request
	 *            请求对象
	 * @param obj
	 *            实例化的Model对象
	 * @param paraArgs
	 *            指定参数
	 * @return
	 */
	public static Object getBeanRemove(HttpServletRequest request, Object obj,
			String... paraArgs) {
		return getBean(request, obj, null, null, null, true, paraArgs);
	}

	/**
	 * 根据Request获取Model。接受指定参数
	 * 
	 * @param request
	 *            请求对象
	 * @param obj
	 *            实例化的Model对象
	 * @param paraArgs
	 *            指定参数
	 * @return
	 */
	public static Object getBeanAccept(HttpServletRequest request, Object obj,
			String... paraArgs) {
		return getBean(request, obj, null, null, null, false, paraArgs);
	}

	/**
	 * 根据Request获取Model所有参数
	 * 
	 * @param request
	 *            请求对象
	 * @param obj
	 *            实例化的Model对象
	 * @return
	 */
	public static Object getBeanAll(HttpServletRequest request, Object obj) {
		return getBean(request, obj, null, null, null, true, null);
	}

	/**
	 * 根据Request获取Model。排除指定参数
	 * 
	 * @param request
	 *            请求对象
	 * @param obj
	 *            实例化的Model对象
	 * @param paraArgs
	 *            指定参数
	 * @return
	 */
	public static Object getBeanRemove(HttpServletRequest request,
			String firstSuffix, Object obj, String... paraArgs) {
		return getBean(request, obj, null, null, firstSuffix, true, paraArgs);
	}

	/**
	 * 根据Request获取Model。接受指定参数
	 * 
	 * @param request
	 *            请求对象
	 * @param obj
	 *            实例化的Model对象
	 * @param paraArgs
	 *            指定参数
	 * @return
	 */
	public static Object getBeanAccept(HttpServletRequest request,
			String firstSuffix, Object obj, String... paraArgs) {
		return getBean(request, obj, null, null, firstSuffix, false, paraArgs);
	}

	/**
	 * 根据Request获取Model所有参数
	 * 
	 * @param request
	 *            请求对象
	 * @param obj
	 *            实例化的Model对象
	 * @return
	 */
	public static Object getBeanAll(HttpServletRequest request,
			String firstSuffix, Object obj) {
		return getBean(request, obj, null, null, firstSuffix, true, null);
	}

	private static Object getBean(HttpServletRequest request, Object obj,
			List<BeanFieldEntity> fields, String baseName, String firstSuffix,
			Boolean isReplace, String... paraArgs) {
		try {
			try {
				if (obj instanceof Class) {
					obj = ((Class) obj).newInstance();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (StringUtils.isNullOrEmpty(firstSuffix)) {
				firstSuffix = "";
			} else {
				firstSuffix += ".";
			}
			if (StringUtils.isNullOrEmpty(isReplace)) {
				isReplace = false;
			}
			if (StringUtils.isNullOrEmpty(baseName)) {
				baseName = "";
			} else {
				baseName += ".";
			}
			List<String> paras = null;
			if (!StringUtils.isNullOrEmpty(paraArgs)) {
				// 转化参数
				paras = new ArrayList<String>();
				for (String tmp : paraArgs) {
					try {
						String[] tab = tmp.split("\\.");
						for (String tmpTab : tab) {
							try {
								paras.add(tmpTab);
							} catch (Exception e) {
							}
						}
						paras.add(tmp);
					} catch (Exception e) {
					}
				}
			}
			if (StringUtils.isNullOrEmpty(fields)) {
				// 获取对象字段属性
				fields = PropertUtil.getBeanFields(obj);
			}
			firstSuffix = (firstSuffix == null) ? "" : firstSuffix;
			Object childObj = null;
			String paraName = null;
			Object paraValue = null;
			for (BeanFieldEntity entity : fields) {
				try {
					paraName = firstSuffix + baseName + entity.getFieldName();
					String className = getTypeName(entity.getFieldType());
					if (!StringUtils.isNullOrEmpty(paras)) {
						if (isReplace) {
							if (paras.contains(paraName)) {
								continue;
							}
						}
						if (!isReplace) {
							if (!paras.contains(paraName)) {
								continue;
							}
						}
					}

					if (className.startsWith("java.util")
							|| className.startsWith("java.lang")) {
						paraValue = request.getParameter(paraName);
						if (StringUtils.isNullOrEmpty(paraValue)) {
							continue;
						}
						setProperties(obj, entity.getFieldType(),
								entity.getFieldName(), paraValue);
					} else {
						if (entity.getFieldType().getSuperclass()
								.getSimpleName()
								.equals(BaseModel.class.getSimpleName())) {
							childObj = entity.getFieldType().newInstance();
							childObj = getBean(request, childObj, null,
									paraName, firstSuffix, isReplace, paraArgs);
							setProperties(obj, entity.getFieldType(),
									entity.getFieldName(), childObj);
						}
					}
				} catch (Exception e) {
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	private static String getTypeName(Class type) {
		String name = type.toString();
		String[] names = name.split(" ");
		return names[names.length - 1];
	}

	/**
	 * 设置字段值
	 * 
	 * @param obj
	 *            实例对象
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            新的字段值
	 * @return
	 */
	public static void setProperties(Object object, Class type,
			String propertyName, Object value) throws Exception {
		PropertyDescriptor pd = new PropertyDescriptor(propertyName,
				object.getClass());
		try {
			if (StringUtils.isNullOrEmpty(value)) {
				value = null;
				Method methodSet = pd.getWriteMethod();
				methodSet.invoke(object, value);
				return;
			} else if (type.getName().equals(Boolean.class.getName())) {
				value = ((String) value).equals("true") ? true : false;
			} else if (type.getName().equals(Integer.class.getName())) {
				value = Integer.valueOf(value.toString());
			} else if (type.getName().equals(Float.class.getName())) {
				value = Float.valueOf(value.toString());
			} else if (type.getName().equals(Long.class.getName())) {
				value = Long.valueOf(value.toString());
			} else if (type.getName().equals(String.class.getName())) {
				value = value.toString();
			} else if (type.getName().equals(Date.class.getName())) {
				if (StringUtils.isMatcher(value.toString(),
						"[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")) {
					value = new SimpleDateFormat("yyyy-MM-dd").parse(value
							.toString());
				}
				if (StringUtils
						.isMatcher(value.toString(),
								"^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*")) {
					value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(value.toString());
				}
			}
			Method methodSet = pd.getWriteMethod();
			methodSet.invoke(object, value);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static float getFloatParameter(HttpServletRequest request,
			String paramName, float defaultValue) {
		String value = request.getParameter(paramName);
		if (StringUtils.isNullOrEmpty(value)) {
			return defaultValue;
		} else {
			try {
				return Float.parseFloat(value);
			} catch (Exception e) {
				return defaultValue;
			}
		}
	}

	public static String getReqSuffix(HttpServletRequest request) {
		String url = request.getServletPath();
		String[] tab = url.split("\\.");
		if (tab.length > 1) {
			return tab[tab.length - 1];
		}
		return "";
	}

	/**
	 * 获得当的访问路径
	 * 
	 * HttpServletRequest.getRequestURL+"?"+HttpServletRequest.getQueryString
	 * 
	 * @param request
	 * @return
	 */
	public static String getLocation(HttpServletRequest request) {
		UrlPathHelper helper = new UrlPathHelper();
		StringBuffer buff = request.getRequestURL();
		String uri = request.getRequestURI();
		String origUri = helper.getOriginatingRequestUri(request);
		buff.replace(buff.length() - uri.length(), buff.length(), origUri);
		String queryString = helper.getOriginatingQueryString(request);
		if (queryString != null) {
			buff.append("?").append(queryString);
		}
		return buff.toString();
	}

	public static String getBodyAsString(HttpServletRequest request,
			String charset) {
		InputStream i = null;
		String str = null;
		try {
			i = request.getInputStream();
			int a = 0;
			int length = request.getContentLength();
			length = length <= 0 ? 1024 : length;
			byte[] body = new byte[0];
			byte[] tmp = new byte[1024];
			while ((a = i.read(tmp)) != -1) {
				int tmplength = body.length;
				body = new byte[tmplength + a];
				System.arraycopy(tmp, 0, body, tmplength, a);
			}
			str = URLDecoder.decode(new String(body, charset), charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 获取POST请求参数中数据
	 * 
	 * @param request
	 * @throws IOException
	 */
	public static String getPostContent(HttpServletRequest request) {
		String content = null;
		try {
			content = IOUtils.toString(request.getInputStream(),
					request.getCharacterEncoding());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	public static boolean isFilterExcluded(ServletRequest request,String []exculdeUrls) throws IOException,
			ServletException {
		if (exculdeUrls != null) {
			String uri = getRequestUri((HttpServletRequest)request);
			for (String url : exculdeUrls) {
				if (MATCHER.match(url,uri)) {
					return true;
				}
			}
		}
		return false;
	}
	private static String getRequestUri(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String projectName = request.getContextPath();
		if (projectName != null && !projectName.trim().equals("")) {
			uri = uri.replace(projectName, "/");
		}
		uri = uri.replace("../", "/");
		while (uri.indexOf("//") > -1) {
			uri = uri.replace("//", "/");
		}
		return uri.toLowerCase();
	}

	public static void main(String[] args) {

	}
}
