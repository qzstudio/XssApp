package com.xss.web.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xss.web.entity.BeanFieldEntity;
import com.xss.web.entity.HqlEntity;
import com.xss.web.entity.Where;
import com.xss.web.model.base.BaseModel;

/**
 * @remark HQL处理
 * @author WebSOS
 * @email 644556636@qq.com
 * @blog bkkill.com
 */
public class HqlUtil {
	private static HqlEntity getHqlWhere(Object obj, String fatherBean,
			Where where) {
		List<BeanFieldEntity> list = PropertUtil.getBeanFields(obj);
		HqlEntity entity = new HqlEntity();
		if (StringUtils.isNullOrEmpty(list)) {
			return entity;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder();
		fatherBean = (fatherBean == null || fatherBean.equals("")) ? ""
				: fatherBean + ".";
		if (where != null && where.getWheres() != null) {
			
			List<Where.ThisWhere> wheres = where.getWheres();
			for (Where.ThisWhere whe : wheres) {
				try {
					if (whe.getFieldValues() == null) {
						continue;
					}
					hql.append(" and ").append(fatherBean)
							.append(whe.getFieldName()).append(" ");
					String paraName = "where_"
							+ whe.getFieldName().replace(".", "_");
					hql.append(whe.getSymbol());
					if (!StringUtils.isNullOrEmpty(whe.getFieldValues())) {
						hql.append("(:").append(paraName).append(") ");
						map.put(paraName, whe.getFieldValues());
					}

				} catch (Exception e) {
				}

			}
		}
		for (BeanFieldEntity tmp : list) {
			try {
				if (StringUtils.isNullOrEmpty(tmp)
						|| StringUtils.isNullOrEmpty(tmp.getFieldValue())) {
					continue;
				}
				String avaName = getAdvateParaName(fatherBean
						+ tmp.getFieldName());
				if (tmp.getFieldValue() instanceof Number) {
					hql.append(MessageFormat.format(" and {0}{1}=:{2}",
							fatherBean, tmp.getFieldName(), avaName));
					map.put(avaName, tmp.getFieldValue());
				}
				if (tmp.getFieldValue() instanceof String) {
					hql.append(MessageFormat.format(" and {0}{1} like:{2}",
							fatherBean, tmp.getFieldName(), avaName));
					map.put(avaName, "%" + tmp.getFieldValue() + "%");
				}
				if (tmp.getFieldValue() instanceof BaseModel) {
					HqlEntity entityTmp = getHqlWhere(tmp.getFieldValue(),
							fatherBean + tmp.getFieldName(), null);
					hql.append(entityTmp.getHql());
					map.putAll(entityTmp.getMap());
				}
			} catch (Exception e) {
			}
		}
		entity.setHql(hql.toString());
		entity.setMap(map);
		return entity;
	}

	public static HqlEntity getHqlWhere(Object obj) {
		return getHqlWhere(obj, null, null);
	}

	public static HqlEntity getHqlWhere(Object obj, Where where) {
		return getHqlWhere(obj, null, where);
	}

	private static String getAdvateParaName(String str) {
		return str.replace(".", "_").replace("\\.", "_");
	}
}
