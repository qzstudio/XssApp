package com.xss.web.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xss.web.annotation.CacheHandle;
import com.xss.web.annotation.DelCacheHandle;
import com.xss.web.base.cache.CacheFinal;
import com.xss.web.cache.base.BaseCache;
import com.xss.web.model.Admin;
import com.xss.web.service.AdminService;
import com.xss.web.util.StringUtils;

@Service
public class AdminCache extends BaseCache {

	@Resource
	AdminService adminService;
	@CacheHandle(key=CacheFinal.ADMIN_INFO_KEY ,validTime=60)
	public Admin getAdmin(String username) {
		Admin admin = (Admin) baseService.findFirstByField(Admin.class, "userName",
				username);
		return admin;
	}
	@CacheHandle(key=CacheFinal.ADMIN_LIST_KEY ,validTime=60)
	public List<Admin> loadAdmins() {
		List<Admin> admins = (List<Admin>) baseService.load(Admin.class);
		return admins;
	}
	@CacheHandle(key=CacheFinal.ADMIN_INFO_KEY ,validTime=60)
	public Admin getAdmin(Integer id) {
		Admin admin = (Admin) baseService.get(Admin.class, id);
		return admin;
	}
	@DelCacheHandle(keys={CacheFinal.ADMIN_LIST_KEY,CacheFinal.ADMIN_INFO_KEY})
	public void save(Admin admin) {
		adminService.saveOrUpdate(admin);
	}
	@DelCacheHandle(keys={CacheFinal.ADMIN_INFO_KEY,CacheFinal.ADMIN_LIST_KEY})
	public void delete(Integer id) {
		adminService.delete(Admin.class, id);
	}
}
