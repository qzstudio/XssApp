package com.xss.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xss.web.entity.BeanFieldEntity;
import com.xss.web.model.base.BaseModel;

//import oracle.sql.CLOB;

public class StringUtils {

	public static final String nullToEmptyOfObject(Object o) {
		if (o != null) {
			return o.toString().trim();
		}
		return "";
	}
	public static String toString(Object obj) {
		if (isNullOrEmpty(obj)) {
			return null;
		}
		try {
			return String.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}
	public static Integer[] getIntegerParas(Object[] objs) {
		if (isNullOrEmpty(objs)) {
			return null;
		}
		Integer[] ints = new Integer[objs.length];
		for (int i = 0; i < objs.length; i++) {
			try {
				ints[i] = Integer.valueOf(objs[i].toString());
			} catch (Exception e) {
			}
		}
		return ints;
	}
	/**
	 * 生成指定数目字符串按分隔符分割
	 * 
	 * @param baseStr
	 * @param mosaicChr
	 * @param size
	 * @return
	 */
	public static String getByMosaicChr(String baseStr, String mosaicChr,
			Integer size) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			if (isNullOrEmpty(baseStr)) {
				continue;
			}
			list.add(baseStr);
		}
		return collectionMosaic(list, mosaicChr);
	}
	public static List listRandom(List list) {
		List<Integer> indexList = new ArrayList<Integer>();
		Map<Integer, Object> map = new TreeMap<Integer, Object>();
		Integer maxRandom = list.size() * 100;
		Integer ran = null;
		for (int i = 0; i < list.size(); i++) {
			ran = getRanDom(0, maxRandom);
			while (map.get(ran) != null) {
				ran = getRanDom(1, maxRandom);
			}
			map.put(ran, list.get(i));
		}
		list = new ArrayList();
		for (Integer key : map.keySet()) {
			list.add(map.get(key));
		}
		return list;
	}
	public static String ClobToString(Clob clob) {
		String reString = "";
		StringBuffer sb = new StringBuffer();
		if (null == clob) {
			return "";
		}
		try {
			Reader is = clob.getCharacterStream();// 得到流
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
				sb.append(s);
				s = br.readLine();
			}
		} catch (SQLException e) {

		} catch (IOException e) {
			// TODO Auto-generated catch block

		}
		reString = sb.toString();
		return reString;
	}

	/**
	 * 将String转成Clob ,静态方法
	 * 
	 * @param str
	 *            字段
	 * @return clob 对象，如果出现错误，返回 null
	 */
	public static Clob stringToClob(String str) {
		if (null == str)
			return null;
		else {
			try {
				java.sql.Clob c = new javax.sql.rowset.serial.SerialClob(
						str.toCharArray());
				return c;
			} catch (Exception e) {
				return null;
			}
		}
	}

	public static String textCutCenter(String allTxt, String firstTxt,
			String lastTxt) {
		try {
			String tmp = "";
			int n1 = allTxt.indexOf(firstTxt) + 1;
			if (n1 != -1) {
				tmp = allTxt.substring(n1 + (firstTxt.length() - 1),
						allTxt.length());
				int n2 = tmp.indexOf(lastTxt);
				if (n2 != -1) {
					tmp = tmp.substring(0, n2);
					return tmp;
				}
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public static Integer toInteger(Object obj) {
		if (isNullOrEmpty(obj)) {
			return null;
		}
		try {
			return Integer.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static Double toDouble(Object obj) {
		if (isNullOrEmpty(obj)) {
			return null;
		}
		try {
			return Double.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static Float toFloat(Object obj) {
		if (isNullOrEmpty(obj)) {
			return null;
		}
		try {
			return Float.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static Long toLong(Object obj) {
		if (isNullOrEmpty(obj)) {
			return null;
		}
		try {
			return Long.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static Integer getRanDom(int start, int end) {
		return (int) (Math.random() * (end - start + 1)) + start;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static Boolean isMatcher(String val, String matcher) {
		Pattern p = Pattern.compile(matcher);
		Matcher m = p.matcher(val);
		return m.matches();
	}

	public static boolean isMobile(String mobile) {
		if (isNullOrEmpty(mobile)) {
			return false;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	public static boolean isLegal(String str) {
		if (isNullOrEmpty(str)) {
			return false;
		}
		Pattern p = Pattern.compile("[A-Za-z0-9_]{3,16}");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isEmail(String email) {
		if (isNullOrEmpty(email)) {
			return false;
		}
		Pattern p = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isMd5(String md5) {
		if (isNullOrEmpty(md5)) {
			return false;
		}
		Pattern p = Pattern.compile("[A-Za-z0-9_]{16,40}");
		Matcher m = p.matcher(md5);
		return m.matches();
	}

	public static Integer findEmptyIndex(Object... obj) {
		if (obj == null || obj.length == 0) {
			return -1;
		}
		for (int i = 0; i < obj.length; i++) {
			if (isNullOrEmpty(obj[i])) {
				return i;
			}
		}
		return -1;
	}

	public static Boolean hasEmpty(String... str) {
		Integer index = findEmptyIndex(str);
		if (index >-1) {
			return true;
		}
		return false;
	}

	/**
	 * 把一个数组按照分隔符拼接成字符串
	 * 
	 * @param 数组参数
	 * @param 分隔符
	 * @return
	 */
	public static String collectionMosaic(Object[] objs, String mosaicChr) {
		if (isNullOrEmpty(objs)) {
			return null;
		}
		List<Object> objList = Arrays.asList(objs);
		return collectionMosaic(objList, mosaicChr);
	}

	/**
	 * 把一个数组按照分隔符拼接成字符串
	 * 
	 * @param 数组参数
	 * @param 分隔符
	 * @return
	 */
	public static String collectionMosaic(int[] intObjs, String mosaicChr) {
		Object[] objs = new Object[intObjs.length];
		for (int i = 0; i < intObjs.length; i++) {
			objs[i] = String.valueOf(intObjs[i]);
		}
		return collectionMosaic(objs, mosaicChr);
	}

	/**
	 * 把一个或多个字符串按照分隔符拼接成字符串
	 * 
	 * @param 数组参数
	 * @param 分隔符
	 * @return
	 */
	public static String collectionMosaic(String mosaicChr, Object... objs) {
		List<Object> objList = Arrays.asList(objs);
		return collectionMosaic(objList, mosaicChr);
	}

	/**
	 * 把一个集合按照分隔符拼接成字符串
	 * 
	 * @param 集合参数
	 * @param 分隔符
	 * @return 字符串
	 */
	public static String collectionMosaic(List<?> objs, String mosaicChr) {
		if (objs == null || objs.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Object obj : objs) {
			if (isNullOrEmpty(obj)) {
				continue;
			}
			sb.append(obj);
			if (i < objs.size() - 1) {
				sb.append(mosaicChr);
			}
			i++;
		}
		return sb.toString();
	}

	/**
	 * 生成指定数目字符串按分隔符分割
	 * 
	 * @param baseStr
	 * @param mosaicChr
	 * @param size
	 * @return
	 */
	public static String getStringSByMosaicChr(String baseStr,
			String mosaicChr, Integer size) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			if (isNullOrEmpty(baseStr)) {
				continue;
			}
			list.add(baseStr);
		}
		return collectionMosaic(list, mosaicChr);
	}

	/**
	 * 按照分隔符分割,得到字符串集合
	 * 
	 * @param text
	 *            原字符串
	 * @param mosaiChr
	 *            分隔符
	 * @return list
	 */
	public static List<String> splitByMosaic(String text, String mosaiChr) {
		if (text == null || mosaiChr == null) {
			return null;
		}
		String[] tab = text.split(mosaiChr);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < tab.length; i++) {
			if (isNullOrEmpty(tab[i])) {
				continue;
			}
			list.add(tab[i]);
		}
		return list;
	}

	/**
	 * 按照分隔符分割,得到字符串集合
	 * 
	 * @param text
	 *            原字符串
	 * @param mosaiChr
	 *            分隔符
	 * @return list
	 */
	public static List<Integer> splitByMosaicInteger(String text,
			String mosaiChr) {
		if (text == null || mosaiChr == null) {
			return null;
		}
		String[] tab = text.split(mosaiChr);
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < tab.length; i++) {
			if (isNullOrEmpty(tab[i])) {
				continue;
			}
			try {
				list.add(Integer.valueOf(tab[i]));
			} catch (Exception e) {
			}

		}
		return list;
	}

	/**
	 * 按照分隔符分割,得到字符串集合
	 * 
	 * @param text
	 *            原字符串
	 * @param mosaiChr
	 *            分隔符
	 * @return list
	 */
	public static Integer[] splitByMosaicIntegers(String text, String mosaiChr) {
		if (text == null || mosaiChr == null) {
			return null;
		}
		String[] tab = text.split(mosaiChr);
		Integer[] list = new Integer[tab.length];
		for (int i = 0; i < tab.length; i++) {
			if (isNullOrEmpty(tab[i])) {
				continue;
			}
			try {
				list[i] = Integer.valueOf(tab[i]);
			} catch (Exception e) {
			}

		}
		return list;
	}
	public static List<String> doMatcher(String context, String pat) {
		try {
			List<String> images = new ArrayList<String>();
			Integer index = 0;
			Pattern pattern = Pattern.compile(pat, Pattern.DOTALL);
			Matcher matcher = pattern.matcher(context);
			String tmp = null;
			while (matcher.find(index)) {
				tmp = matcher.group(0);
				index = matcher.end();
				if (StringUtils.isNullOrEmpty(tmp)) {
					continue;
				}
				images.add(tmp);
			}
			return images;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String doMatcherFirst(String context, String pat) {
		List<String> strs = doMatcher(context, pat);
		if (StringUtils.isNullOrEmpty(strs)) {
			return null;
		}
		return strs.get(0);
	}
	public static boolean isNullOrEmpty(Object obj) {
		try {
			if (obj == null)
				return true;
			if (obj instanceof CharSequence) {
				return ((CharSequence) obj).length() == 0;
			}
			if (obj instanceof Collection) {
				return ((Collection) obj).isEmpty();
			}
			if (obj instanceof Map) {
				return ((Map) obj).isEmpty();
			}
			if (obj instanceof Object[]) {
				Object[] object = (Object[]) obj;
				if (object.length == 0) {
					return true;
				}
				boolean empty = true;
				for (int i = 0; i < object.length; i++) {
					if (!isNullOrEmpty(object[i])) {
						empty = false;
						break;
					}
				}
				return empty;
			}
			return false;
		} catch (Exception e) {
			return true;
		}

	}

	public static Integer findNull(Object... objs) {
		if (isNullOrEmpty(objs)) {
			return 0;
		}
		for (int i = 0; i < objs.length; i++) {
			if (isNullOrEmpty(objs[i])) {
				return i;
			}
		}
		return -1;
	}


	

	// 判断是否为数字
	public static Boolean isNumber(String str) {
		if (isNullOrEmpty(str)) {
			return false;
		}
		try {
			Integer.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String argsToString(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (String tmp : args) {
			sb.append(tmp);
		}
		return sb.toString();
	}

	// 字符串意义分割
	public static String[] splitString(String str) {
		if (isNullOrEmpty(str)) {
			return null;
		}
		String[] finalStrs = new String[str.length()];
		for (int i = 0; i < str.length(); i++) {
			finalStrs[i] = str.substring(i, i + 1);
		}
		return finalStrs;
	}

	public static String getString(Object... objs) {
		if (isNullOrEmpty(objs)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object obj : objs) {
			if (isNullOrEmpty(obj)) {
				sb.append("null");
			}
			sb.append(String.valueOf(obj));
		}
		return sb.toString();
	}

	// 将对象内所有字段名、字段值拼接成字符串，用于缓存Key
	public static String getBeanKey(Object...obj) {
		if (isNullOrEmpty(obj)) {
			return "";
		}
		String str = JSONWriter.write(obj);
		return EncryptionUtil.md5Code(str);
	}
	public static String getBeanString(Object obj){
		return getBeanString(obj, null);
	}
	public static String getBeanString(Object obj, String currFather) {
		if (isNullOrEmpty(obj)) {
			return "";
		}
		currFather = (isNullOrEmpty(currFather)) ? "" : currFather + "_";
		List<BeanFieldEntity> list = PropertUtil.getBeanFields(obj);
		StringBuilder sb = new StringBuilder();
		if(isNullOrEmpty(list)){
			return sb.toString();
		}
		for (BeanFieldEntity tmp : list) {
			if (isNullOrEmpty(tmp.getFieldValue())) {
				continue;
			}
			if (tmp.getFieldValue() instanceof BaseModel) {
				sb.append(getBeanString(tmp.getFieldValue(), tmp.getFieldName()));
				continue;
			}
			if (tmp.getFieldValue() instanceof Map) {
				continue;
			}
			if (tmp.getFieldValue().getClass().isArray()) {
				continue;
			}
			sb.append(currFather + tmp.getFieldName() + "_"
					+ String.valueOf(tmp.getFieldValue()));
			sb.append("|");
		}
		return sb.toString();
	}
	public static String stringSort(String str) {
		if(isNullOrEmpty(str)){
			return "";
		}
		String[] strs = splitString(str);
		Arrays.sort(strs);
		return argsToString(strs);
	}

	public static void main(String[] args) {
		
	}
}
