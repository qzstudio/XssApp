package com.xss.web.cache.base;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xss.web.annotation.CacheHandle;
import com.xss.web.base.cache.CacheFinal;
import com.xss.web.base.cache.CacheClient;
import com.xss.web.entity.IpAddressEntity;
import com.xss.web.service.base.BaseService;
import com.xss.web.util.IpAddressUtil;

@Service
public class BaseCache {

	@Resource
	protected BaseService baseService;

	@Resource
	protected CacheClient cacheClient;
	/**
	 * 增加缓存对象
	 * 
	 * @param key
	 * @param ce
	 * @param validityTime
	 *            有效时间
	 */
	public  synchronized void addCache(String key, Object ce,
			int validityTime) {
		try {
			cacheClient.addCache(key, ce, validityTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  synchronized void addCache(String key, Object ce) {
		try {
			cacheClient.addCache(key, ce);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public  synchronized Object getCache(String key) {
		try {
			return cacheClient.getCache(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * 获取缓存key列表
	 * 
	 * @param key
	 * @return
	 */
	public   Set<String> getCacheKeys() {
		try {
			return cacheClient.getCacheKeys();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 检查是否含有制定key的缓冲
	 * 
	 * @param key
	 * @return
	 */
	public  synchronized boolean contains(String key) {
		try {
			return cacheClient.contains(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public  synchronized void removeCache(String key) {
		try {
			cacheClient.removeCache(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public  synchronized void removeCacheFuzzy(String key) {
		try {
			cacheClient.removeCacheFuzzy(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@CacheHandle(key=CacheFinal.IP_ADDRESS_INFO ,validTime=600)
	public IpAddressEntity.AddressInfo getIpAddress(String ip) {
		IpAddressEntity.AddressInfo address=IpAddressUtil.getAddress(ip);
		return address;
	}

}
