package com.xss.web.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

/**
 * 客户端请求处理类
 * @author WUJB
 *
 */
@Service("reqJsonUtil")
public class ReqJsonUtil {
	/**
	 * 获取客户端的json请求内容(此方法不作URLDecoder的UTF-8解码，用于登录使用)
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public  String getJsonContentNoDecode(HttpServletRequest request){
		// 读取请求内容
		InputStream i = null;
		StringBuilder sb = null;
		String str = null;
		try {
			i =request.getInputStream();
			int a = 0;
			byte[] bytes = new byte[2048];
			sb = new StringBuilder();
			while ((a = i.read(bytes)) != -1) {
				sb.append(new String(bytes,0,a,"utf-8"));
			}
			str = sb.toString();
		} catch (IOException e) {
			
		}
		return str;
	}
	
	/**
	 * 获取POST请求参数中数据
	 * 
	 * @param request
	 * @throws IOException
	 */
	public  String getPostContent(HttpServletRequest request){
		String content = null;
		try {
			content = IOUtils.toString(request.getInputStream(), request
					.getCharacterEncoding());
		} catch (Exception e) {
			
		}
		return content;
	}
	public  String readServletInputStream(HttpServletRequest req) {

		String content = null;
		try {
		ServletInputStream sis = req.getInputStream();
		byte[] b = new byte[1024];
		int count = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((count = sis.read(b)) != -1) {
		baos.write(b, 0, count);
		}
		baos.flush();
		content = baos.toString("UTF-8");
		baos.close();
		sis.close();
		} catch (IOException e) {
		
		}
		return content;
		}

		public  void writeServletOutputStream(HttpServletResponse resp, String content) {

		try {
		ServletOutputStream  out=resp.getOutputStream();
		OutputStreamWriter sos = new OutputStreamWriter(out,"UTF-8");  
		sos.write(content);
		sos.close();
		} catch (IOException e) {
		
		}
		}
		/**
		 * 获取客户端的json请求内容
		 * @param request
		 * @return
		 * @throws UnsupportedEncodingException 
		 */
		public static String getJsonContent(HttpServletRequest request){
			InputStream i = null;
			StringBuilder sb = null;
			String str = null;
			try {
				i =request.getInputStream();
				int a = 0;
				byte[] bytes = new byte[2048];
				sb = new StringBuilder();
				while ((a = i.read(bytes)) != -1) {
					sb.append(new String(bytes,0,a,"utf-8"));
				}
				str = URLDecoder.decode(sb.toString(),"utf-8");
			} catch (IOException e) {
				
			}
			return str;//sb.toString();
		}
		/**
		 * 直接将客户端请求转换成对象
		 * @param request
		 * @param cla
		 * @return
		 */
		public static Object changeToObject(HttpServletRequest request, Class cla) {
			Object object = null;
			try {
				String jsonContent = getJsonContent(request);
				Gson gson = new Gson();
				object = gson.fromJson(jsonContent, cla);
			} catch (Exception e) {
				
			}
			return object;
		}
		
		/**
		 * json转换成对象
		 * @param jsonContent
		 * @param cla
		 * @return
		 */
		public static Object jsonToObject(String jsonContent, Class cla) {
			if(jsonContent==null)return null;
			Object object = null;
			try {
				Gson gson = new Gson();
				object = gson.fromJson(jsonContent, cla);
			} catch (Exception e) {
				
			}
			return object;
		}
		
		public static String objectToJson(Object obj){
			Gson gson = new Gson();
			return gson.toJson(obj);
		}
		/**
		 * json返回数据
		 * @param returnCode
		 * @param msg
		 * @return
		 * @throws IOException
		 * @throws JSONException 
		 */
		public static String jsonResult(int returnCode, String msg){
	       try {
			JSONObject json = new JSONObject();
			json.put("returnCode", returnCode);//响应码
			json.put("msg", msg);//描述
			return json.toString();
	       } catch (Exception e) {
	    	   
			   return null;
	       }
	    }

}
