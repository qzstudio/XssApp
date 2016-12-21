package com.xss.web.entity;

import java.util.HashMap;
import java.util.Map;

public class HqlEntity {

	private String hql;
	private Map<String, Object> map;

	public String getHql() {
		if (hql == null) {
			return "";
		}
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public Map<String, Object> getMap() {
		if (map == null) {
			return new HashMap<String, Object>();
		}
		return map;
	}

	public void setMap(Map<String, Object> map) {

		this.map = map;
	}

}
