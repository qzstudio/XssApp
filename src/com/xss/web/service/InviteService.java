package com.xss.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xss.web.service.base.BaseService;

@Service
public class InviteService extends BaseService {
	public void delete(Integer... ids) {
		String hql = "delete from Invite where id in (:ids)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		baseDao.cudByHql(hql, map);
	}
}
