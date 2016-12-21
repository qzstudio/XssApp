package com.xss.web.cache;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xss.web.annotation.CacheHandle;
import com.xss.web.annotation.DelCacheHandle;
import com.xss.web.base.cache.CacheFinal;
import com.xss.web.cache.base.BaseCache;
import com.xss.web.model.Role;

@Service
public class RoleCache extends BaseCache {
	@CacheHandle(key=CacheFinal.ROLE_INFO_KEY ,validTime=65)
	public Role loadRole(Integer roleId) {
		Role roles = (Role) baseService.get(Role.class, roleId);
		return roles;
	}
	@DelCacheHandle(keys={CacheFinal.ROLE_INFO_KEY,CacheFinal.ROLE_LIST_KEY, CacheFinal.ROLE_MENU_LIST_KEY})
	public void save(Role role) {
		baseService.saveOrUpdate(role);
	}
	@DelCacheHandle(keys={CacheFinal.ROLE_INFO_KEY,CacheFinal.ROLE_LIST_KEY, CacheFinal.ROLE_MENU_LIST_KEY})
	public void del(Integer roleId) {
		baseService.delete(Role.class, roleId);
	}
	@CacheHandle(key=CacheFinal.ROLE_LIST_KEY ,validTime=65)
	public List<Role> loadRoles() {
		List<Role> roles = (List<Role>) baseService.load(Role.class);
		return roles;
	}

}
