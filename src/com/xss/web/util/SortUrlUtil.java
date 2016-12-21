package com.xss.web.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xss.web.entity.HttpEntity;

public class SortUrlUtil {

	public static String getSortUrl(String url) {
		try {
			HttpEntity entity = HttpUtil
					.Get("http://api.t.sina.com.cn/short_url/shorten.json?source=3213676317&url_long="
							+ url);
			String html = entity.getHtml().replace("[", "").replace("]", "");
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = parser.parse(html).getAsJsonObject();
			String sortUrl = jsonObject.get("url_short").getAsString();
			return sortUrl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
	}
}
