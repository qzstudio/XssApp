package com.xss.web.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xss.web.annotation.CacheHandle;
import com.xss.web.annotation.DelCacheHandle;
import com.xss.web.base.cache.CacheFinal;
import com.xss.web.base.cache.CacheTimerHandler;
import com.xss.web.cache.base.BaseCache;
import com.xss.web.model.Suffix;
import com.xss.web.service.SuffixService;
import com.xss.web.util.StringUtils;

@Service
public class SuffixCache extends BaseCache {

	@Resource
	private SuffixService SuffixService;
	@CacheHandle(key=CacheFinal.SITE_SUFFIX_KEY ,validTime=65)
	public List<Suffix> loadSuffix() {
		List<Suffix> list = SuffixService.loadSuffix();// 查询数据库
		return list;
	}
	@CacheHandle(key=CacheFinal.SITE_TMP_SUFFIX_KEY ,validTime=3600)
	public Suffix getSuffix(Integer id) {
		Suffix suffix = SuffixService.getSuffix(id);
		return suffix;
	}
	@DelCacheHandle(keys={CacheFinal.SITE_TMP_SUFFIX_KEY,CacheFinal.SITE_SUFFIX_KEY,CacheFinal.SITE_DEF_SUFFIX_KEY})
	public void updateSuffix(Suffix suffix) {
		SuffixService.updateSuffix(suffix);
	}
	@CacheHandle(key=CacheFinal.SITE_AVA_SUFFIX_KEY ,validTime=3600)
	public List<String> loadAvaSuffix() {
		List<String> suffix = SuffixService.loadAvaSuffix();
		return suffix;
	}
	@CacheHandle(key=CacheFinal.SITE_DEF_SUFFIX_KEY ,validTime=3600)
	public String loadDefSuffix() {
		String defSuffix = SuffixService.loadDefSuffix();
		return defSuffix;
	}
	@CacheHandle(key=CacheFinal.SITE_STA_SUFFIX_KEY ,validTime=3600)
	public List<String> loadStaSuffix() {
		List<String> list = SuffixService.loadStaSuffix();
		return list;
	}
	@DelCacheHandle(keys={CacheFinal.SITE_TMP_SUFFIX_KEY,CacheFinal.SITE_SUFFIX_KEY,CacheFinal.SITE_DEF_SUFFIX_KEY,CacheFinal.SITE_AVA_SUFFIX_KEY})
	public void updateSuffix(Integer[] suffix) {
		SuffixService.updateSuffix(suffix);
	}
}
