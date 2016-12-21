package com.xss.web.base.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.xss.web.base.cache.CacheTimerHandler;
import com.xss.web.base.page.Pager;
import com.xss.web.entity.HqlEntity;
import com.xss.web.entity.Where;
import com.xss.web.util.HqlUtil;
import com.xss.web.util.SpringContextHelper;
import com.xss.web.util.StringUtils;

/**
 * @remark DAO基础方法
 * @author WebSOS
 * @email 644556636@qq.com
 * @blog bkkill.com
 */
@Service
public class BaseDao extends HibernateDaoSupport {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	public void save(Object obj) {
		getHibernateTemplate().save(obj);
	}

	public void saveOrUpadete(Object obj) {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	public void delete(Object obj) {
		getHibernateTemplate().delete(obj);
	}

	public List<?> get(String hql) {
		return getHibernateTemplate().find(hql);
	}

	public List<?> findByHql(String hql, Object[] obj) {
		return getHibernateTemplate().find(hql, obj);
	}

	public List<?> findByHqlPage(String hql, Integer start, Integer rows) {
		Session session = getCurrSession();
		Query query = session.createQuery(hql).setFirstResult(start.intValue())
				.setMaxResults(rows.intValue());
		List<?> list = query.list();
		session.close();
		return list;
	}

	public List<?> findByHqlPage(String hql, Integer start, Integer rows,
			Object[] objs) {
		Session session = getCurrSession();
		Query query = session.createQuery(hql);
		for (int i = 0; i < objs.length; i++) {
			query.setParameter(i, objs[i]);
		}
		query.setFirstResult(start.intValue()).setMaxResults(rows.intValue());
		List<?> list = query.list();
		session.close();
		return list;
	}

	public List<?> findBySqlPage(String sql, Integer start, Integer rows) {
		Session session = getCurrSession();
		Query query = session.createSQLQuery(sql)
				.setFirstResult(start.intValue())
				.setMaxResults(rows.intValue())
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<?> list = query.list();
		session.close();
		return list;
	}

	public Long findTotalCountByHql(String hql, Object[] obj) {
		hql = "SELECT COUNT(*) " + hql;
		Session session = getCurrSession();
		Query query = session.createQuery(hql);
		for (int i = 0; i < obj.length; i++) {
			query.setParameter(i, obj[i]);
		}
		Long totalCount = (Long) query.uniqueResult();
		session.close();
		return totalCount;
	}

	public int cudByHql(String hql, Map<String, Object> map) {
		return this.getQuery(hql, map).executeUpdate();
	}

	public int cudBySql(String sql, Map<String, Object> map) {
		return this.getSQLQuery(sql, map).executeUpdate();
	}

	public void delete(Class<?> objClass, Serializable id) {
		getHibernateTemplate().delete(this.get(objClass, id));
	}

	public void deleteAll(Collection<?> collection) {
		getHibernateTemplate().deleteAll(collection);
	}

	public List<?> findByCriteria(Criteria criteria,
			Map<String, String> orderByMap, int pageSize, int pageNo) {
		return getCriteria(criteria, orderByMap, pageSize, pageNo).list();
	}

	public List<?> findByHql(String hql, Map<String, Object> map, int pageSize,
			int pageNo) {
		return this.getQuery(hql, map, pageSize, pageNo).list();
	}

	public Pager<?> findByPager(String hql, Map<String, Object> map,
			Pager<?> pager) {
		Integer count = (Integer) getCountByHql(hql, map);
		List<?> list = findByHql(hql, map, pager.getPageSize(),
				pager.getCurrentPage());
		pager.setTotalRows(count);
		if (count.intValue() == 0) {
			return pager;
		}
		try {
			Integer page = Long.valueOf(count / pager.getPageSize()).intValue();
			if (count % pager.getPageSize() > 0) {
				page++;
			}
		} catch (Exception e) {
		}
		pager.setPageData(list);
		return pager;
	}

	public Pager<?> findByPager(String hql, HqlEntity whereEntity,
			Pager<?> pager) {
		Map<String, Object> map = whereEntity.getMap();
		return findByPager(hql, map, pager);
	}

	public List<?> findByPropertiesEq(Class<?> objClass,
			Map<String, Object> proMap, Map<String, String> orderByMap,
			int pageSize, int pageNo) {
		// 创建查询对象
		Criteria criteria = this.createCriteria(objClass);
		// 条件查询数据
		criteria = this.createCriteriaExpressionEq(criteria, proMap);
		// 查询结果
		return getCriteria(criteria, orderByMap, pageSize, pageNo).list();
	}

	public List<?> findBySql(String sql, Map<String, Object> map, int pageSize,
			int pageNo) {
		return this.getSQLQuery(sql, map, pageSize, pageNo).list();
	}

	public Object get(Class<?> objClass, Serializable id) {
		return getHibernateTemplate().get(objClass, id);
	}

	public Integer getCountByHql(String hql, Map<String, Object> map) {
		hql = parsCountHql(hql);
		String key=StringUtils.getBeanKey(hql,map);
		Integer count=(Integer) CacheTimerHandler.getCache(key);
		if(!StringUtils.isNullOrEmpty(count)){
			return count;
		}
		count= ((Long) this.findByHql(hql, map).get(0))
				.intValue();
		if(!StringUtils.isNullOrEmpty(count)&&count!=0){
			CacheTimerHandler.addCache(key, count,120);
		}
		return count;
	}

	private String parsCountHql(String hql){
		while (hql.indexOf("  ")>-1) {
			hql=hql.replace("  ", " ");
		}
		Integer formIndex=hql.toLowerCase().indexOf("from");
		if(formIndex>-1){
			hql=hql.substring(formIndex, hql.length());
		}
		Integer orderIndex=hql.toLowerCase().indexOf("order by");
		if(orderIndex>-1){
			hql=hql.substring(0, orderIndex);
		}
		Integer limitIndex=hql.toLowerCase().indexOf("limit");
		if(limitIndex>-1){
			hql=hql.substring(0, limitIndex);
		}
		hql="select count(0) "+hql;
		return hql;
	}

	public Object getCountBySql(String sql, Map<String, Object> map,
			int pageSize, int pageNo) {
		String totalHql = sql.substring(sql.toLowerCase().indexOf("from"),
				sql.length());
		totalHql = "SELECT COUNT(*) " + totalHql;
		return this.findBySql(totalHql, map, pageSize, pageNo).get(0);
	}

	public void insert(Object obj) {
		super.getHibernateTemplate().save(obj);
	}

	public void comit() {
		super.getHibernateTemplate().flush();
	}

	public Object load(Class<?> objClass, Serializable id) {
		return super.getHibernateTemplate().load(objClass, id);
	}

	public List<?> load(Class<?> objClass) {
		String hql = " from " + objClass.getSimpleName();
		return findByHql(hql);
	}

	public List<?> load(Class<?> objClass, String orderFieldName, Boolean isDesc) {
		String hql = " from " + objClass.getSimpleName();
		if (!StringUtils.isNullOrEmpty(orderFieldName)) {
			hql += (" order by " + orderFieldName);
			if (!StringUtils.isNullOrEmpty(isDesc) && isDesc) {
				hql += "  desc";
			}
		}
		return findByHql(hql);
	}

	public void saveOrUpdate(Object obj) {
		super.getHibernateTemplate().saveOrUpdate(obj);
	}

	public void batchSave(List<?> objs) {
		for (Object obj : objs) {
			super.getHibernateTemplate().saveOrUpdate(obj);
		}
	}

	public void update(Object obj) {
		super.getHibernateTemplate().update(obj);
	}

	/*
	 * ----------------------private method---------------------
	 */

	public List<?> findByHql(String hql, Map<String, Object> map) {
		return this.getQuery(hql, map).list();
	}

	public Session getCurrSession() {
		return this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
	}

	public List<?> findByHqlCurrSession(String hql, Map<String, Object> map,
			int pageSize, int pageNo) {
		return this.getCurrSessionQuery(hql, map, pageSize, pageNo).list();
	}

	public List<?> findByHql(String hql) {
		return this.getQuery(hql, null).list();
	}

	public int cudByHql(String hql) {
		return cudByHql(hql, null);
	}

	public List<?> findByField(Class<?> objClass, Map<String, Object> fieldMap) {
		return findByField(objClass, null, null, fieldMap, 0, 0);
	}

	public List<?> findByField(Class<?> objClass, String orderField,
			Boolean isDesc, Map<String, Object> fieldMap, int pageSize,
			int pageNo) {
		StringBuilder sb = new StringBuilder("from ");
		sb.append(objClass.getSimpleName());
		sb.append(" where 1=1 ");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		isDesc = (isDesc == null) ? false : isDesc;
		if (fieldMap != null && !fieldMap.isEmpty()) {
			for (String key : fieldMap.keySet()) {
				sb.append(" and ").append(key).append("=:")
						.append(getAdvateParaName(key)).append(" ");
				paraMap.put(getAdvateParaName(key), fieldMap.get(key));
			}
		}
		if (orderField != null && !orderField.trim().equals("")) {
			sb.append(" order by " + orderField);
			if (!StringUtils.isNullOrEmpty(isDesc) && isDesc) {
				sb.append(" desc ");
			}
		}
		return this.findByHql(sb.toString(), paraMap, pageSize, pageNo);
	}

	public List<?> findByField(Class<?> objClass, String orderField,
			Boolean isDesc, String fieldName, Object fieldValue) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put(fieldName, fieldValue);
		return findByField(objClass, orderField, isDesc, paraMap, 0, 0);
	}

	public List<?> findInFields(Class<?> objClass, String orderField,
			Boolean isDesc, String fieldName, List<?> fieldValues) {
		StringBuilder sb = new StringBuilder("from ");
		sb.append(objClass.getSimpleName());
		sb.append(" where  ").append(fieldName)
				.append(" in (:" + getAdvateParaName(fieldName) + ") ");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put(getAdvateParaName(fieldName), fieldValues);
		if (orderField != null && !orderField.trim().equals("")) {
			sb.append(" order by " + orderField);
			if (isDesc != null && isDesc) {
				sb.append(" desc");
			}
		}
		return this.findByHql(sb.toString(), paraMap, 0, 0);
	}

	public List<?> findInFields(Class<?> objClass, String fieldName,
			List<?> fieldValues) {
		return findInFields(objClass, null, false, fieldName, fieldValues);
	}

	public List<?> findInFields(Class<?> objClass, String orderField,
			Boolean isDesc, String fieldName, Object... fieldValues) {
		return findInFields(objClass, orderField, isDesc, fieldName,
				Arrays.asList(fieldValues));
	}

	public List<?> findInFields(Class<?> objClass, String fieldName,
			Object... fieldValues) {
		return findInFields(objClass, null, false, fieldName, fieldValues);
	}

	public List<?> findByField(Class<?> objClass, String fieldName,
			Object fieldValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(fieldName, fieldValue);
		return findByField(objClass, map);
	}

	public List<?> findByField(Class<?> objClass, String fieldName,
			Object fieldValue, String orderField) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(fieldName, fieldValue);
		return findByField(objClass, orderField, false, fieldName, fieldValue);
	}

	public Object findFirstByHql(String hql) {
		return findFirstByHql(hql, null);
	}

	public Object findFirstByHql(String hql, Map<String, Object> map) {
		List<?> list = findByHql(hql, map, 1, 1);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public List<?> findByField(Class<?> objClass, String orderField,
			Boolean isDesc, Map<String, Object> fieldMap) {

		return findByField(objClass, orderField, isDesc, fieldMap, 0, 0);
	}

	public Object findFirstByField(Class<?> objClass, String orderField,
			Boolean isDesc, Map<String, Object> fieldMap) {
		List<?> list = findByField(objClass, orderField, isDesc, fieldMap, 1, 1);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public Object findFirstByField(Class<?> objClass, Map<String, Object> map,
			String orderField) {
		return findFirstByField(objClass, orderField, null, map);
	}

	public Object findFirstByField(Class<?> objClass, String fieldName,
			Object fieldValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(fieldName, fieldValue);
		List<?> list = findByField(objClass, null, null, map, 1, 1);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public Object getCountByHql(String hql) {
		return getCountByHql(hql, null);
	}

	public Pager<?> findPagerByObject(Object obj, Pager<?> pager, Where where,
			String orderField, Boolean isDesc) {
		StringBuilder hql = new StringBuilder("select ");
		try {
			if (obj instanceof Class) {
				obj = ((Class) obj).newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取主键
		String primary = getPrimaryKey(obj.getClass());
		hql.append(primary);
		hql.append(" from ");
		hql.append(obj.getClass().getSimpleName());
		hql.append(" where 1=1 ");
		//根据对象解析语句
		HqlEntity entity = HqlUtil.getHqlWhere(obj, where);
		hql.append(entity.getHql());
		if (isDesc == null) {
			isDesc = true;
		}
		if (orderField != null) {
			hql.append(" order by ").append(orderField).append(" ")
					.append(isDesc ? "desc" : "");
		}
		Pager<?> pagers = findByPager(hql.toString(), entity.getMap(), pager);
		List<Object> objs = (List<Object>) pagers.getPageData();
		if (objs != null && !objs.isEmpty()) {
			pagers.setPageData(findInFields(obj.getClass(), orderField,
					isDesc, primary, objs));
		}
		return pagers;
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
		Pager<?> pager = new Pager<Object>();
		pager.setPageSize(0);
		pager.setCurrentPage(0);
		pager = findPagerByObject(obj, pager, where, orderField, isDesc);
		if (pager != null) {
			return (List<?>) pager.getPageData();
		}
		return null;
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

	public Object findFirstByObject(Object obj) {
		List<?> list = findByObject(obj, null);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * HQL
	 */
	private Query getQuery(String hql, Map<String, Object> map) {
		Query query = getCurrSession().createQuery(hql);
		query = this.setParameter(query, map);
		return query;
	}

	private Query createQuery(String hql) {
		return getCurrSession().createQuery(hql);
	}

	private Query setParameter(Query query, Map<String, Object> map) {
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				Object obj = map.get(string);
				// 这里考虑传入的参数是什么类型，不同类型使用的方法不同
				if (obj instanceof Collection<?>) {
					query.setParameterList(string, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					query.setParameterList(string, (Object[]) obj);
				} else {
					query.setParameter(string, obj);
				}
			}
		}
		return query;
	}

	private Query getQuery(String hql, Map<String, Object> map, int pageSize,
			int pageNo) {
		Session session = getCurrSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, map);
		query = this.setPageProperty(query, pageSize, pageNo);
		return query;
	}

	private Query setPageProperty(Query query, int pageSize, int pageNo) {
		if (pageNo != 0 || pageSize != 0) {
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		return query;
	}

	private SQLQuery getSQLQuery(String sql, Map<String, Object> map) {
		SQLQuery query = this.createSQLQuery(sql);
		query = this.setParameter(query, map);
		return query;
	}

	private SQLQuery createSQLQuery(String sql) {
		return getCurrSession().createSQLQuery(sql);
	}

	private SQLQuery setParameter(SQLQuery query, Map<String, Object> map) {
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				Object obj = map.get(string);
				// 这里考虑传入的参数是什么类型，不同类型使用的方法不同
				if (obj instanceof Collection<?>) {
					query.setParameterList(string, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					query.setParameterList(string, (Object[]) obj);
				} else {
					query.setParameter(string, obj);
				}
			}
		}
		return query;
	}

	private SQLQuery getSQLQuery(String sql, Map<String, Object> map,
			int pageSize, int pageNo) {
		SQLQuery query = this.createSQLQuery(sql);
		query = this.setParameter(query, map);
		query = this.setPageProperty(query, pageSize, pageNo);
		return query;
	}

	private SQLQuery setPageProperty(SQLQuery query, int pageSize, int pageNo) {
		if (pageNo != 0 && pageSize != 0) {
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		return query;
	}

	/**
	 * Criteria
	 */
	/**
	 * 条件查询-多条件排序
	 */
	private Criteria createOrderBy(Criteria criteria,
			Map<String, String> orderByMap) {
		if (orderByMap != null) {
			Set<String> keySet = orderByMap.keySet();
			for (String string : keySet) {
				this.createOrderBy(criteria, string, orderByMap.get(string));
			}
		}
		return criteria;
	}

	/**
	 * 条件查询-排序
	 */
	private Criteria createOrderBy(Criteria criteria, String orderByKey,
			String orderByType) {
		if (Constant4Dao.DESC.equalsIgnoreCase(orderByType.trim())) {
			criteria.addOrder(Order.desc(orderByKey));
		} else {
			criteria.addOrder(Order.asc(orderByKey));
		}
		return criteria;
	}

	/**
	 * 条件查询-分页
	 */
	private Criteria createPaging(Criteria criteria, int pageSize, int pageNo) {
		if (pageSize != 0 && pageNo != 0) {
			criteria.setFirstResult((pageNo - 1) * pageSize);
			criteria.setMaxResults(pageSize);
		}
		return criteria;
	}

	private Criteria getCriteria(Criteria criteria,
			Map<String, String> orderByMap, int pageSize, int pageNo) {
		// 排序
		criteria = this.createOrderBy(criteria, orderByMap);
		// 分页
		criteria = this.createPaging(criteria, pageSize, pageNo);
		return criteria;
	}

	/**
	 * 创建条件查询对象
	 */
	private Criteria createCriteria(Class<?> objClass) {
		return getCurrSession().createCriteria(objClass);
	}

	private Criteria createCriteriaExpressionEq(Criteria criteria,
			Map<String, Object> proMap) {
		if (proMap != null) {
			Set<String> keySet = proMap.keySet();
			for (String string : keySet) {
				criteria.add(Restrictions.eq(string, proMap.get(string)));
			}
		}
		return criteria;
	}

	private String getAdvateParaName(String str) {
		return str.replace(".", "_").replace("\\.", "_");
	}

	private String getPrimaryKey(Class cla) {
		LocalSessionFactoryBean sessionFactory = (LocalSessionFactoryBean) SpringContextHelper
				.getBean("&sessionFactory");
		Configuration config = sessionFactory.getConfiguration();
		PersistentClass pc = config.getClassMapping(cla.getName());
		return pc.getTable().getPrimaryKey().getColumn(0).getName();
	}

	private Query getCurrSessionQuery(String hql, Map<String, Object> map,
			int pageSize, int pageNo) {
		Query query = getCurrSession().createQuery(hql);
		query = this.setParameter(query, map);
		query = this.setPageProperty(query, pageSize, pageNo);
		return query;
	}
}