package com.xss.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xss.web.model.Letter;
import com.xss.web.service.base.BaseService;
import com.xss.web.util.PropertUtil;
import com.xss.web.util.StringUtils;

@Service
public class LetterService extends BaseService {
	public void delLetter(Integer... projectId) {
		// delLetterParas(projectId);

		List<Letter> letters = (List<Letter>) findInFields(Letter.class,
				"project.id", projectId);
		if (!StringUtils.isNullOrEmpty(letters)) {
			List<Integer> ids = (List<Integer>) PropertUtil.getFieldValues(
					letters, "id");
			delParasByLetterId(ids);

		}

		String hql = "delete from Letter where project.id in(:projectId)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectId", projectId);
		baseDao.cudByHql(hql, map);
	}

	public Integer getLetterCount() {

		String hql = "from Letter";
		Integer count = (Integer) baseDao.getCountByHql(hql);
		return count;
	}

	public void delLetterById(Integer... id) {
		delParasByLetterId(id);
		String hql = "delete from Letter where id in (:id)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		baseDao.cudByHql(hql, map);
	}

	public void delParasByLetterId(Integer... ids) {
		String hql = "delete from LetterParas where letter.id in(:letteryId)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("letteryId", ids);
		baseDao.cudByHql(hql, map);
	}

	public void delParasByLetterId(List<Integer> ids) {
		String hql = "delete from LetterParas where letter.id in(:letteryId)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("letteryId", ids);
		baseDao.cudByHql(hql, map);
	}

	public void delLetterParas(Integer projectId) {
		String hql = "delete from LetterParas p where p.letter.project.id=:projectId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectId", projectId);
		baseDao.cudByHql(hql, map);
	}
}
