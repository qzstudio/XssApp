package com.xss.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xss.web.model.User;
import com.xss.web.service.base.BaseService;

@Service
public class UserService extends BaseService {

	// 根据用户名或邮箱或手机或QQ获取用户信息
	public User getUser(String userName) {
		String hql = "from User where id>-1 and (userName=:userName or mobile=:userName or email=:userName )";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		return (User) baseDao.findFirstByHql(hql, map);
	}

	public void delUser(Integer id) {
		// 删除用户
		delete(User.class, id);
	}

	// 根据用户名或邮箱或手机或QQ获取用户信息
	public User getOtherUser(String userName, Integer currId) {
		String hql = "from User where id>-1 and (userName=:userName or mobile=:userName or email=:userName) and id<>:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("id", currId);
		return (User) baseDao.findFirstByHql(hql, map);
	}

	public Integer getUserCount() {
		String hql = "from User";
		Integer count = (Integer) baseDao.getCountByHql(hql);
		return count;
	}
}
