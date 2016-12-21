package com.xss.web.base.cache;

import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.xss.web.entity.SimpleConcurrentMap;

/**
 * @className：CacheHandler
 * @description：缓存操作类，对缓存进行管理,清除方式采用Timer定时的方式
 * @creater：Websos
 * @creatTime：2014年5月7日 上午9:18:54
 * @alter：WebSOS
 * @alterTime：2014年5月7日 上午9:18:54
 * @remark：
 * @version
 */
public class CacheTimerHandler {
	// private static final long SECOND_TIME = 1000;//默认过期时间 20秒
	// private static final int DEFUALT_VALIDITY_TIME = 20;//默认过期时间 20秒
	private static final Timer timer;
	private static final SimpleConcurrentMap<String, Object> map;
	static Object mutex = new Object();
	static {
		timer = new Timer();
		map = new SimpleConcurrentMap<String, Object>(
				new HashMap<String, Object>(1 << 18));
	}

	/**
	 * 增加缓存对象
	 * 
	 * @param key
	 * @param ce
	 * @param validityTime
	 *            有效时间
	 */
	public static synchronized void addCache(String key, Object ce,
			int validityTime) {
		synchronized (mutex) {
			map.put(key, ce);
			timer.schedule(new TimeoutTimerTask(key), validityTime * 1000);
		}
		;
	}
	//获取缓存KEY列表
	public static Set<String> getCacheKeys() {
		return map.keySet();
	}
	/**
	 * 增加缓存对象
	 * 
	 * @param key
	 * @param ce
	 * @param validityTime
	 *            有效时间
	 */
	public static synchronized void addCache(String key, Object ce) {
		synchronized (mutex) {
			map.put(key, ce);
		}
		;
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public static synchronized Object getCache(String key) {
		return map.get(key);
	}

	/**
	 * 检查是否含有制定key的缓冲
	 * 
	 * @param key
	 * @return
	 */
	public static synchronized boolean contains(String key) {
		return map.containsKey(key);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public static synchronized void removeCache(String key) {
		map.remove(key);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public static synchronized void removeCacheFuzzy(String key) {
		for (String tmpKey : map.keySet()) {
			if (tmpKey.indexOf(key)>-1) {
				map.remove(tmpKey);
			}
		}
	}

	/**
	 * 获取缓存大小
	 * 
	 * @param key
	 */
	public static int getCacheSize() {
		return map.size();
	}

	/**
	 * 清除全部缓存
	 */
	public static synchronized void clearCache() {
		// if (null != timer) {
		// timer.cancel();
		// }
		map.clear();
	}

	/**
	 * @projName：lottery
	 * @className：TimeoutTimerTask
	 * @description：清除超时缓存定时服务类
	 * @creater：WebSOS
	 * @creatTime：2014年5月7日上午9:34:39
	 * @alter：WebSOS
	 * @alterTime：2014年5月7日 上午9:34:39
	 * @remark：
	 * @version
	 */
	static class TimeoutTimerTask extends TimerTask {
		private String ceKey;

		public TimeoutTimerTask(String key) {
			this.ceKey = key;
		}

		@Override
		public void run() {
			CacheTimerHandler.removeCache(ceKey);
		}
	}
}
