package com.xss.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xss.web.service.base.BaseService;

@Service
public class ModuleService extends BaseService {

	public void cleanModule(Integer... moduleId) {
		String hql = "update Project set module.id=null where module.id in(:moduleId)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("moduleId", moduleId);
		baseDao.cudByHql(hql, map);
	}

	public void deleteModule(Integer... moduleId) {
		String hql = "delete from Module where id in(:moduleId)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("moduleId", moduleId);
		baseDao.cudByHql(hql, map);
	}

	public Integer getModuleCount() {
		String hql = "from Module";
		Integer count = (Integer) baseDao.getCountByHql(hql);
		return count;
	}
}
