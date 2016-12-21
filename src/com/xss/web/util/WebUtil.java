package com.xss.web.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class WebUtil {
    public static String getParm(HttpServletRequest _request, String _parmName, String _defaultValue) {
        Object rs = _request.getParameter(_parmName);
        if (isEmpty(rs)) {
            rs = _request.getAttribute(_parmName);
        }
        return isEmpty(rs) ? _defaultValue : rs.toString();
    }
    public static boolean isEmpty(Map obj) {
        return null == obj || obj.isEmpty();
    }
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        } else if (obj instanceof Number) {
            return false;
        } else {
            return false;
        }
    }
}