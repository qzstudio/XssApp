package com.xss.web.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xss.web.annotation.CacheHandle;
import com.xss.web.annotation.DelCacheHandle;
import com.xss.web.base.cache.CacheFinal;
import com.xss.web.base.page.Pager;
import com.xss.web.cache.base.BaseCache;
import com.xss.web.entity.Where;
import com.xss.web.model.Module;
import com.xss.web.service.ModuleService;
import com.xss.web.util.StringUtils;

@Service
public class ModuleCache extends BaseCache {
	@Resource
	ModuleService moduleService;
	@CacheHandle(key=CacheFinal.MODULE_PAGER_KEY ,validTime=60)
	public Pager<Module> getPager(Module module, Pager<Module> pager) {
		Pager<Module> pageData = (Pager<Module>) moduleService.findPagerByObject(module,
				pager, "id", true);
		return pageData;
	}
	@CacheHandle(key=CacheFinal.USER_MODULE_LIST_KEY ,validTime=60)
	public List<Module> loadUserModules(Integer userId) {
		List<Module> modules = (List<Module>) moduleService.findByField(Module.class,
				"user.id", userId);
		return modules;
	}
	@CacheHandle(key=CacheFinal.USER_MODULE_INFO_KEY ,validTime=60)
	public Module getModule(Integer id) {
		Module module = (Module) moduleService.get(Module.class, id);
		return module;
	}
	@DelCacheHandle(keys={CacheFinal.USER_MODULE_INFO_KEY,CacheFinal.USER_MODULE_REPLACE_KEY,CacheFinal.USER_MODULE_LIST_KEY,CacheFinal.SYS_MODULE_LIST_KEY,CacheFinal.ALL_MODULE_LIST_KEY,
			CacheFinal.MODULE_PAGER_KEY})
	public void del(Integer... moduleId) {
		moduleService.cleanModule(moduleId);
		moduleService.deleteModule(moduleId);
	}
	@DelCacheHandle(keys={CacheFinal.USER_MODULE_INFO_KEY,CacheFinal.USER_MODULE_REPLACE_KEY,CacheFinal.USER_MODULE_LIST_KEY,CacheFinal.SYS_MODULE_LIST_KEY,CacheFinal.ALL_MODULE_LIST_KEY,
			CacheFinal.MODULE_PAGER_KEY})
	public void save(Module module) {
		moduleService.saveOrUpdate(module);
		String key = CacheFinal.USER_MODULE_INFO_KEY.toString();
		removeCacheFuzzy(key);
		key = CacheFinal.USER_MODULE_REPLACE_KEY.toString();
		removeCacheFuzzy(key);
		key = CacheFinal.USER_MODULE_LIST_KEY.toString();
		removeCacheFuzzy(key);
		key = CacheFinal.SYS_MODULE_LIST_KEY.toString();
		removeCacheFuzzy(key);
		key = CacheFinal.ALL_MODULE_LIST_KEY.toString();
		removeCacheFuzzy(key);
		key = CacheFinal.MODULE_PAGER_KEY.toString();
		removeCacheFuzzy(key);
	}
	@CacheHandle(key=CacheFinal.USER_MODULE_REPLACE_KEY ,validTime=65)
	public Module getModuleReplaceCurr(Integer id, Integer userId, String title) {
		Where where = new Where().set("user.id", userId).set("title", title)
				.set("id", "<>", id);
		Module module = (Module) moduleService.findFirstByObject(new Module(), where);
		return module;
	}
	@CacheHandle(key=CacheFinal.SYS_MODULE_LIST_KEY ,validTime=65)
	public List<Module> loadSysModules() {
		List<Module> modules = (List<Module>) moduleService.findByField(Module.class,
				"type", 0);
		return modules;
	}
	@CacheHandle(key=CacheFinal.ALL_MODULE_LIST_KEY ,validTime=65)
	public List<Module> loadAllModules() {
		List<Module> modules = (List<Module>) moduleService.load(Module.class);
		return modules;
	}
	@CacheHandle(key=CacheFinal.MODULE_COUNT_KEY ,validTime=30)
	public Integer getModuleCount() {
		// 查DB
		Integer count = moduleService.getModuleCount();
		return count;

	}
}
