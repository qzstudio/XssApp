package com.xss.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xss.web.service.base.BaseService;

@Service
public class ProjectService extends BaseService {
	public Integer getLeterCount(Integer projectId) {
		String hql = " from Letter where project.id=:projectId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectId", projectId);
		Integer num = (Integer) baseDao.getCountByHql(hql, map);
		return num;
	}

	public void deleteProject(Integer... projectId) {
		String hql = " delete from Project where id in(:projectId)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectId", projectId);
		baseDao.cudByHql(hql, map);
	}

	public Integer getProjectCount() {
		String hql = "from Project";
		Integer count = (Integer) baseDao.getCountByHql(hql);
		return count;
	}
}
