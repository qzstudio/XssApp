package com.xss.web.service.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xss.web.base.dao.BaseDao;
import com.xss.web.base.page.Pager;
import com.xss.web.entity.Where;
import com.xss.web.util.StringUtils;

/**
 * 
 * @author WebSOS
 * @email 644556636@qq.com
 * @blog bkkill.com
 */
@Service
public class BaseService {

	// Class<?> objClass 实体类
	// String fieldName 对象
	// Object fieldValue 参数

	@Resource
	protected BaseDao baseDao;

	public Object get(Class<?> objClass, Serializable id) {
		return baseDao.get(objClass, id);
	}

	public void insert(Object obj) {
		baseDao.insert(obj);
	}

	public void saveOrUpdate(Object obj) {
		baseDao.saveOrUpdate(obj);
	}

	public void batchSave(List<?> objs) {
		baseDao.batchSave(objs);
	}

	public void update(Object obj) {
		baseDao.update(obj);
	}

	public void delete(Object obj) {
		baseDao.delete(obj);
	}

	public List<?> findByField(Class<?> objClass, Map<String, Object> fieldMap) {
		return baseDao.findByField(objClass, fieldMap);
	}

	public List<?> findByField(Class<?> objClass, String orderField,
			Boolean isDesc, Map<String, Object> fieldMap, int pageSize,
			int pageNo) {
		return baseDao.findByField(objClass, orderField, isDesc, fieldMap,
				pageSize, pageNo);
	}

	public List<?> findInFields(Class<?> objClass, String orderField,
			String fieldName, List<?> fieldValues) {
		return baseDao.findInFields(objClass, orderField, fieldName,
				fieldValues);
	}

	public List<?> findInFields(Class<?> objClass, String orderField,
			Boolean isDesc, String fieldName, List<?> fieldValues) {
		return baseDao.findInFields(objClass, orderField, isDesc, fieldName,
				fieldValues);
	}

	public List<?> findInFields(Class<?> objClass, String orderField,
			Boolean isDesc, String fieldName, Object... fieldValues) {
		return baseDao.findInFields(objClass, orderField, isDesc, fieldName,
				fieldValues);
	}

	public List<?> findInFields(Class<?> objClass, String fieldName,
			List<?> fieldValues) {
		return baseDao.findInFields(objClass, fieldName, fieldValues);
	}

	public List<?> findInFields(Class<?> objClass, String fieldName,
			Object... fieldValues) {
		return baseDao.findInFields(objClass, fieldName, fieldValues);
	}

	public List<?> findByField(Class<?> objClass, String fieldName,
			Object fieldValue) {
		return baseDao.findByField(objClass, fieldName, fieldValue);
	}

	public List<?> findByField(Class<?> objClass, String fieldName,
			Object fieldValue, String orderField) {
		return baseDao.findByField(objClass, fieldName, fieldValue, orderField);
	}

	public List<?> findByField(Class<?> objClass, String orderField,
			Boolean isDesc, Map<String, Object> fieldMap) {
		return baseDao.findByField(objClass, orderField, isDesc, fieldMap);
	}

	public Object findFirstByField(Class<?> objClass,
			Map<String, Object> fieldMap, String orderField) {
		return baseDao.findFirstByField(objClass, fieldMap, orderField);
	}

	public Object findFirstByField(Class<?> objClass,
			Map<String, Object> fieldMap) {
		return findFirstByField(objClass, fieldMap, null);
	}

	public Object findFirstByField(Class<?> objClass, String fieldName,
			Object fieldValue) {
		return baseDao.findFirstByField(objClass, fieldName, fieldValue);
	}

	public List<?> load(Class<?> objClass) {
		return baseDao.load(objClass);
	}

	public Object loadFirst(Class<?> objClass) {
		List<?> list = baseDao.load(objClass);
		if (!StringUtils.isNullOrEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	public List<?> load(Class<?> objClass, String orderFieldName, Boolean isDesc) {
		return baseDao.load(objClass, orderFieldName, isDesc);
	}

	public List<?> findByField(Class<?> objClass, String orderField,
			Boolean isDesc, String fieldName, Object fieldValue) {
		return baseDao.findByField(objClass, orderField, isDesc, fieldName,
				fieldValue);
	}

	public Pager<?> findPagerByObject(Object obj, Pager<?> pager, Where where,
			String orderField, Boolean isDesc) {
		return baseDao.findPagerByObject(obj, pager, where, orderField, isDesc);
	}

	public Pager<?> findPagerByObject(Object obj, Pager<?> pager,
			String orderField) {
		return findPagerByObject(obj, pager, null, orderField, true);
	}

	public Pager<?> findPagerByObject(Object obj, Pager<?> pager,
			String orderField, Boolean isDesc) {
		return findPagerByObject(obj, pager, null, orderField, isDesc);
	}

	public Pager<?> findPagerByObject(Object obj, Pager<?> pager, Where where) {
		return findPagerByObject(obj, pager, where, null, null);
	}

	public Pager<?> findPagerByObject(Object obj, Pager<?> pager) {
		return findPagerByObject(obj, pager, null, null, null);
	}

	public List<?> findByObject(Object obj, Where where, String orderField,
			Boolean isDesc) {
		return baseDao.findByObject(obj, where, orderField, isDesc);
	}

	public List<?> findByObject(Object obj, Where where, String orderField) {
		return findByObject(obj, where, orderField, null);
	}

	public List<?> findByObject(Object obj, Where where) {
		return findByObject(obj, where, null);
	}

	public List<?> findByObject(Object obj) {
		return findByObject(obj, null);
	}

	public Object findFirstByObject(Object obj, Where where, String orderField,
			Boolean isDesc) {
		List<?> list = baseDao.findByObject(obj, where, orderField, isDesc);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public Object findFirstByObject(Object obj, Where where, String orderField) {
		List<?> list = findByObject(obj, where, orderField, null);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public Object findFirstByObject(Object obj, Where where) {
		List<?> list = findByObject(obj, where, null);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public Object findFirstByObject(Object obj) {
		List<?> list = findByObject(obj, null);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public void delete(Class<?> objClass, Serializable id) {
		baseDao.delete(this.get(objClass, id));
	}
}
