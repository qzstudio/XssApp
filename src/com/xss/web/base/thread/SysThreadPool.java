package com.xss.web.base.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SysThreadPool {
	public static ExecutorService threadPool = Executors.newCachedThreadPool();
}
