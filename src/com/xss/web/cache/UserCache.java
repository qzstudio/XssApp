package com.xss.web.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xss.web.annotation.CacheHandle;
import com.xss.web.annotation.DelCacheHandle;
import com.xss.web.base.cache.CacheFinal;
import com.xss.web.base.page.Pager;
import com.xss.web.cache.base.BaseCache;
import com.xss.web.model.Module;
import com.xss.web.model.Project;
import com.xss.web.model.User;
import com.xss.web.service.UserService;
import com.xss.web.util.PropertUtil;
import com.xss.web.util.StringUtils;

@Service
public class UserCache extends BaseCache {
	@Resource
	UserService userService;
	@Resource
	ModuleCache moduleCache;
	@Resource
	ProjectCache projectCache;
	@CacheHandle(key=CacheFinal.USER_INFO_KEY ,validTime=65)
	public User getUser(String userName) {
		User user = userService.getUser(userName);
		return user;
	}
	@CacheHandle(key=CacheFinal.USER_LIST_KEY ,validTime=65)
	public Pager<User> getUsers(User user, Pager<User> pager) {
		Pager<User> users = (Pager<User>) userService.findPagerByObject(user, pager, "id",
				true);
		return users;
	}
	@CacheHandle(key=CacheFinal.USER_COUNT_KEY ,validTime=65)
	public Integer getUserCount() {
		Integer count = userService.getUserCount();
		return count;

	}
	@CacheHandle(key=CacheFinal.USER_INFO_ID_KEY ,validTime=65)
	public User getUser(Integer id) {
		User user = (User) userService.get(User.class, id);
		return user;
	}
	@CacheHandle(key=CacheFinal.USER_INFO_KEY ,validTime=65)
	public User getOtherUser(String userName, Integer currId) {
		User user = userService.getOtherUser(userName, currId);
		return user;
	}
	@CacheHandle(key=CacheFinal.USER_INFO_KEY ,validTime=65)
	public User getUser(User paraUser) {
		User	user = (User) userService.findFirstByObject(paraUser);
		return user;
	}
	@CacheHandle(key=CacheFinal.USER_INFO_KEY ,validTime=65)
	public User getUserByUuid(String uuid) {
		User user = (User) userService.findFirstByField(User.class, "uuid", uuid);
		return user;
	}
	@DelCacheHandle(keys={CacheFinal.USER_INFO_KEY,CacheFinal.USER_INFO_ID_KEY,CacheFinal.USER_LIST_KEY})
	public void save(User user) {
		userService.saveOrUpdate(user);
	}
	@DelCacheHandle(keys={CacheFinal.USER_INFO_KEY,CacheFinal.USER_INFO_ID_KEY,CacheFinal.USER_LIST_KEY})
	public void delete(Integer id) {
		// 删除模块
		List<Module> modules = moduleCache.loadUserModules(id);
		if (!StringUtils.isNullOrEmpty(modules)) {
			try {
				List<Integer> ids = (List<Integer>) PropertUtil.getFieldValues(
						modules, "id");
				Integer[] idArgs = ids.toArray(new Integer[ids.size()]);
				moduleCache.del(idArgs);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// 删除项目
		List<Project> projects = projectCache.loadProjects(id);
		if (!StringUtils.isNullOrEmpty(projects)) {
			try {
				List<Integer> ids = (List<Integer>) PropertUtil.getFieldValues(
						projects, "id");
				Integer[] idArgs = ids.toArray(new Integer[ids.size()]);
				projectCache.del(idArgs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		userService.delUser(id);
	}
}
