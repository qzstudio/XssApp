package com.xss.web.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import com.xss.web.entity.HttpEntity;

/**
 * HTTP工具类 支持Cookie操作、支持GZIP解压、支持图片下载、支持Head操作、支持编码 BY:WebSOS QQ644556636
 * 2015-05-26
 */
public class HttpUtil {

	public final static String POST = "POST";
	public final static String GET = "GET";
	public final static String HEAD = "HEAD";
	public final static String PUT = "PUT";
	public final static String CONNECT = "CONNECT";
	public final static String OPTIONS = "OPTIONS";
	public final static String DELETE = "DELETE";
	public final static String PATCH = "PATCH";
	public final static String PROPFIND = "PROPFIND";
	public final static String PROPPATCH = "PROPPATCH";
	public final static String MKCOL = "MKCOL";
	public final static String COPY = "COPY";
	public final static String MOVE = "MOVE";
	public final static String LOCK = "LOCK";
	public final static String UNLOCK = "UNLOCK";
	public final static String TRACE = "TRACE";

	public static HttpEntity Get(String url) {
		try {
			return httpBaseConn(url, "GET", null, null, null, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode) {
		try {
			return httpBaseConn(url, "GET", null, encode, null, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout) {
		try {
			return httpBaseConn(url, "GET", null, null, timeout, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, Integer timeout) {
		try {
			return httpBaseConn(url, "GET", null, encode, timeout, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie) {
		try {
			return httpBaseConn(url, "GET", null, encode, null, sendCookie,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout, String sendCookie) {
		try {
			return httpBaseConn(url, "GET", null, null, timeout, sendCookie,
					null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie,
			Integer timeout) {
		try {
			return httpBaseConn(url, "GET", null, encode, timeout, sendCookie,
					null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Map<String, String> headers) {
		try {
			return httpBaseConn(url, "GET", null, null, null, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, "GET", null, encode, null, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, "GET", null, null, timeout, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, Integer timeout,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, "GET", null, encode, timeout, null,
					headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, "GET", null, encode, null, sendCookie,
					headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout,
			String sendCookie, Map<String, String> headers) {
		try {
			return httpBaseConn(url, "GET", null, null, timeout, sendCookie,
					headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie,
			Integer timeout, Map<String, String> headers) {
		try {
			return httpBaseConn(url, "GET", null, encode, timeout, sendCookie,
					headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData) {
		try {
			return httpBaseConn(url, "POST", postData, null, null, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode) {
		try {
			return httpBaseConn(url, "POST", postData, encode, null, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout) {
		try {
			return httpBaseConn(url, "POST", postData, null, timeout, null,
					null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode,
			Integer timeout) {
		try {
			return httpBaseConn(url, "POST", postData, encode, timeout, null,
					null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout,
			String sendCookie) {
		try {
			return httpBaseConn(url, "POST", postData, null, timeout,
					sendCookie, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode,
			String sendCookie) {
		try {
			return httpBaseConn(url, "POST", postData, encode, null,
					sendCookie, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode,
			Integer timeout, String sendCookie) {
		try {
			return httpBaseConn(url, "POST", postData, encode, timeout,
					sendCookie, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, "POST", postData, null, null, null,
					headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity PUT(String url, String postData,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, "PUT", postData, null, null, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, "POST", postData, encode, null, null,
					headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, "POST", postData, null, timeout, null,
					headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode,
			Integer timeout, Map<String, String> headers) {
		try {
			return httpBaseConn(url, "POST", postData, encode, timeout, null,
					headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout,
			String sendCookie, Map<String, String> headers) {
		try {
			return httpBaseConn(url, "POST", postData, null, timeout,
					sendCookie, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode,
			String sendCookie, Map<String, String> headers) {
		try {
			return httpBaseConn(url, "POST", postData, encode, null,
					sendCookie, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode,
			Integer timeout, String sendCookie, Map<String, String> headers) {
		try {
			return httpBaseConn(url, "POST", postData, encode, timeout,
					sendCookie, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity httpBaseConn(String url, String method,
			String postData, String encode, Integer timeout, String sendCookie,
			Map<String, String> headers) throws Exception {

		if (encode == null || encode.equals("")) {
			encode = "UTF-8";
		}
		if (timeout == null || timeout < 500) {
			timeout = 15000;
		}
		URL serverUrl = new URL(url);
		HttpURLConnection conn = getConnection(serverUrl);
		if (conn == null) {
			return null;
		}
		if (headers != null && !headers.isEmpty()) {
			for (String key : headers.keySet()) {
				conn.addRequestProperty(key, headers.get(key));
				key = null;
			}
		}
		if (sendCookie != null) {
			conn.addRequestProperty("Cookie", sendCookie);
		}
		if (method.equalsIgnoreCase(POST) || method.equalsIgnoreCase(PUT)) {
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			byte[] postByte = postData.getBytes(encode);
			conn.setRequestProperty("Content-Length",
					String.valueOf(postByte.length));
			conn.getOutputStream().write(postByte);
			conn.connect();
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
		}
		conn.setConnectTimeout(timeout);
		InputStream ins = null;
		HttpEntity hEntity = new HttpEntity();
		String key = "";
		StringBuilder cookie = new StringBuilder();
		String newCookie = null;
		try {

			hEntity.setCode(conn.getResponseCode());
			if (conn.getResponseCode() != 200) {
				return hEntity;
			}
			ins = conn.getInputStream();
			Map<String, String> headMap = new TreeMap<String, String>();
			for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
				if (key.equalsIgnoreCase("set-cookie")) {
					try {
						cookie.append(conn.getHeaderField(i).replace("/", ""));
					} catch (Exception e) {
					}
				}
			}
			newCookie = mergeCookie(sendCookie, cookie.toString());
			byte[] b = toByte(ins);
			if (headMap.get("Content-Encoding") != null
					&& headMap.get("Content-Encoding").contains("gzip")) {
				b = gzipDecompress(b);
			}
			hEntity.setBye(b);
			hEntity.setCookie(newCookie);
			hEntity.setEncode(encode);
			hEntity.setHeadMap(headMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hEntity;
	}

	public static byte[] gzipDecompress(final byte[] srcByte) {
		try {
			byte[] depBytes;
			ByteArrayInputStream bis = null;
			ByteArrayOutputStream bos = null;
			try {
				bis = new ByteArrayInputStream(srcByte);
				GZIPInputStream gis = new GZIPInputStream(bis);
				bos = new ByteArrayOutputStream();
				byte[] tmp = new byte[1024];
				int read;
				while ((read = gis.read(tmp)) != -1) {
					bos.write(tmp, 0, read);
				}
				gis.close();
				bos.flush();
				depBytes = bos.toByteArray();
			} finally {
				if (bos != null) {
					bos.close();
					bos = null;
				}
				if (bis != null) {
					bis.close();
					bis = null;
				}
			}
			return depBytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static byte[] toByte(InputStream ins) {
		ByteArrayOutputStream swapStream = null;
		try {
			swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int rc = 0;
			while ((rc = ins.read(buff, 0, 1024)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			return swapStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				swapStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String mergeCookie(String oldCookie, String newCookie) {
		if (newCookie == null) {
			return oldCookie;
		}
		Map<String, String> cookieMap = new TreeMap<String, String>();
		String[] cookTmp = null;
		String[] cookieTab = null;
		StringBuilder valueTmp = new StringBuilder();
		String[] cookies = { oldCookie, newCookie };
		for (String currCookie : cookies) {
			if (StringUtils.isNullOrEmpty(currCookie)) {
				continue;
			}
			cookieTab = currCookie.split(";");
			for (String cook : cookieTab) {
				cookTmp = cook.split("=");
				if (cookTmp.length < 2) {
					continue;
				}
				valueTmp = new StringBuilder();
				for (int i = 1; i < cookTmp.length; i++) {
					valueTmp.append(cookTmp[i]);
					if (i < cookTmp.length - 1) {
						valueTmp.append("=");
					}
				}
				if (StringUtils.findNull(cookTmp[0], valueTmp) > -1) {
					continue;
				}
				cookieMap.put(cookTmp[0], valueTmp.toString());
			}
		}
		valueTmp = new StringBuilder();
		for (String key : cookieMap.keySet()) {
			valueTmp.append(key).append("=").append(cookieMap.get(key));
			valueTmp.append(";");
		}
		return valueTmp.toString();
	}

	private static HttpURLConnection getConnection(URL serverUrl) {
		try {
			HttpURLConnection conn= null;
			if(serverUrl.toString().toLowerCase().startsWith("https://")){
				conn= (HttpsURLConnection) serverUrl
						.openConnection();
			}
			else{conn= (HttpURLConnection) serverUrl
					.openConnection();
			}
			conn.addRequestProperty("Referer", serverUrl.toString());
			conn.addRequestProperty("Accept",
					"application/json, text/plain, */*");
			conn.addRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			conn.addRequestProperty("Origin", "http://m.1.pin.wlwork.com");
			conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			conn.addRequestProperty(
					"User-Agent",
					" Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4");
			conn.addRequestProperty("X-Requested-With", "XMLHttpRequest");
			return conn;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得KeyStore.
	 * 
	 * @param keyStorePath
	 *            密钥库路径
	 * @param password
	 *            密码
	 * @return 密钥库
	 * @throws Exception
	 */
	public static KeyStore getKeyStore(String password, String keyStorePath)
			throws Exception {
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance("JKS");
		// 获得密钥库文件流
		FileInputStream is = new FileInputStream(keyStorePath);
		// 加载密钥库
		ks.load(is, password.toCharArray());
		// 关闭密钥库文件流
		is.close();
		return ks;
	}

	/**
	 * 获得SSLSocketFactory.
	 * 
	 * @param password
	 *            密码
	 * @param keyStorePath
	 *            密钥库路径
	 * @param trustStorePath
	 *            信任库路径
	 * @return SSLSocketFactory
	 * @throws Exception
	 */
	public static SSLContext getSSLContext(String password,
			String keyStorePath, String trustStorePath) throws Exception {
		// 实例化密钥库
		KeyManagerFactory keyManagerFactory = KeyManagerFactory
				.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		// 获得密钥库
		KeyStore keyStore = getKeyStore(password, keyStorePath);
		// 初始化密钥工厂
		keyManagerFactory.init(keyStore, password.toCharArray());

		// 实例化信任库
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		// 获得信任库
		KeyStore trustStore = getKeyStore(password, trustStorePath);
		// 初始化信任库
		trustManagerFactory.init(trustStore);
		// 实例化SSL上下文
		SSLContext ctx = SSLContext.getInstance("TLS");
		// 初始化SSL上下文
		ctx.init(keyManagerFactory.getKeyManagers(),
				trustManagerFactory.getTrustManagers(), null);
		// 获得SSLSocketFactory
		return ctx;
	}

	/**
	 * 初始化HttpsURLConnection.
	 * 
	 * @param password
	 *            密码
	 * @param keyStorePath
	 *            密钥库路径
	 * @param trustStorePath
	 *            信任库路径
	 * @throws Exception
	 */
	public static void initHttpsURLConnection(String password,
			String keyStorePath, String trustStorePath) throws Exception {
		// 声明SSL上下文
		SSLContext sslContext = null;
		// 实例化主机名验证接口
		HostnameVerifier hnv = new HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		};
		try {
			sslContext = getSSLContext(password, keyStorePath, trustStorePath);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		if (sslContext != null) {
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
		}
		HttpsURLConnection.setDefaultHostnameVerifier(hnv);
	}
}
