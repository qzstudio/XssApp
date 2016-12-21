package com.xss.web.controllers;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import com.xss.web.base.cache.CacheFinal;
import com.xss.web.cache.EmailCache;
import com.xss.web.cache.InviteCache;
import com.xss.web.cache.SettingCache;
import com.xss.web.cache.UserCache;
import com.xss.web.controllers.base.BaseController;
import com.xss.web.entity.MsgEntity;
import com.xss.web.model.Email;
import com.xss.web.model.Invite;
import com.xss.web.model.Setting;
import com.xss.web.model.User;
import com.xss.web.util.EmailSenderUtil;
import com.xss.web.util.EncryptionUtil;
import com.xss.web.util.JUUIDUtil;
import com.xss.web.util.RequestUtil;
import com.xss.web.util.StringUtils;

@org.springframework.stereotype.Controller
public class Controller extends BaseController {
	static final String DIR = "front/";

	@Resource
	UserCache userCache;
	@Resource
	EmailCache emailCache;
	@Resource
	SettingCache settingCache;
	@Resource
	InviteCache inviteCache;
	@Resource
	EmailSenderUtil emailSenderUtil;

	@RequestMapping
	public String login(HttpServletRequest req, HttpServletResponse res) {
		if (RequestUtil.getUser(req) != null) {
			return "redirect:" + getAttribute("basePath") + "user/index."
					+ getAttribute("defSuffix");
		}
		return DIR + "login";
	}

	@RequestMapping
	public String resetPwd(HttpServletRequest req, HttpServletResponse res) {
		if (RequestUtil.getUser(req) != null) {
			return "redirect:" + getAttribute("basePath") + "user/index."
					+ getAttribute("defSuffix");
		}
		return DIR + "reset_pwd";
	}

	@RequestMapping
	public void sendVerCode(HttpServletRequest req, HttpServletResponse res) {
		String verCode = getPara("verCode");
		if (StringUtils.isNullOrEmpty(verCode)) {
			printMsg(res, new MsgEntity(1, "请输入验证码"));
			return;
		}
		String sessionCode = (String) getSessionPara("piccode");
		setSessionPara("piccode", null);
		if (sessionCode == null || !sessionCode.equals(verCode)) {
			printMsg(res, new MsgEntity(4, "验证码有误"));
			return;
		}
		String userName = getPara("userName");
		if (StringUtils.isNullOrEmpty(userName)) {
			printMsg(res, new MsgEntity(1, "用户名有误"));
			return;
		}
		User user = userCache.getUser(userName);
		if (StringUtils.isNullOrEmpty(user)) {
			printMsg(res, new MsgEntity(2, "用户不存在"));
			return;
		}
		if (StringUtils.isNullOrEmpty(user.getEmail())) {
			printMsg(res, new MsgEntity(3, "尚未绑定手机"));
			return;
		}
		String uuid = JUUIDUtil.createUuid();
		verCode = StringUtils.getRanDom(100000, 999999).toString();
		String url = getAttribute("basePath") + "modifyPwd."
				+ getAttribute("defSuffix") + "?uuid=" + uuid + "&verCode="
				+ verCode + "&userName=" + userName;
		String key = CacheFinal.USER_UUID_KEY.toString() + userName;
		baseCache.addCache(key, uuid, 60 * 30);
		key = CacheFinal.USER_VERCODE_KEY.toString() + userName;
		baseCache.addCache(key, verCode, 60 * 30);
		List<Email> emails = emailCache.loadEmails();
		emailSenderUtil.send(emails, "XSS找回密码邮件", "请点击链接修改密码:" + url
				+ " Prower By WebSOS", user.getEmail());
		printMsg(res, new MsgEntity(4, "发送成功!"));
		return;
	}

	@RequestMapping
	public String modifyPwd(HttpServletRequest req, HttpServletResponse res) {
		String uuid = getPara("uuid");
		String verCode = getPara("verCode");
		String userName = getPara("userName");
		if (StringUtils.findEmptyIndex(uuid, verCode, userName) > -1) {
			setAttribute("msg", new MsgEntity(-1, "参数有误"));
			return DIR + "modify_pwd";
		}
		String key = CacheFinal.USER_UUID_KEY.toString() + userName;
		String sysUuid = (String) baseCache.getCache(key);
		key = CacheFinal.USER_VERCODE_KEY.toString() + userName;
		String sysVerCode = (String) baseCache.getCache(key);
		String verCodeCacheNumberKey=userName+sysVerCode;
		Integer number=(Integer) baseCache.getCache(verCodeCacheNumberKey);
		number=(number==null)?0:number;
		if(number>4){
			baseCache.removeCache(verCodeCacheNumberKey);
		}
		number++;
		baseCache.addCache(verCodeCacheNumberKey, number, 60);
		if (StringUtils.findEmptyIndex(sysUuid, sysVerCode) > -1) {
			setAttribute("msg", new MsgEntity(-1, "验证码已过期"));
			return DIR + "modify_pwd";
		}
		if (!uuid.equals(sysUuid) || !verCode.equals(sysVerCode)) {
			setAttribute("msg", new MsgEntity(-1, "验证参数错误"));
			return DIR + "modify_pwd";
		}
		User user = userCache.getUser(userName);
		if (StringUtils.isNullOrEmpty(user)) {
			setAttribute("msg", new MsgEntity(-1, "用户不存在"));
			return DIR + "modify_pwd";
		}
		setSessionPara("user", user);
		setAttribute("msg", new MsgEntity(0, "验证通过"));
		baseCache.removeCache(verCodeCacheNumberKey);
		return DIR + "modify_pwd";
	}

	@RequestMapping
	public void doModify(HttpServletRequest req, HttpServletResponse res) {
		String userPwd = getPara("userPwd");
		User user = (User) getSessionPara("user");
		if (StringUtils.isNullOrEmpty(user)) {
			printMsg(res, new MsgEntity(-1, "用户不存在"));
		}
		user.setUserPwd(EncryptionUtil.customEnCode(userPwd));
		userCache.save(user);
		RequestUtil.setUser(req, user);
		printMsg(res, new MsgEntity(0, "修改成功"));
	}

	@RequestMapping
	public String index(HttpServletRequest req, HttpServletResponse res) {
		return "index";
	}

	@RequestMapping
	public String reg(HttpServletRequest req, HttpServletResponse res) {
		if (RequestUtil.getUser(req) != null) {
			return "redirect:" + getAttribute("basePath") + "user/index."
					+ getAttribute("defSuffix");
		}
		Setting setting = settingCache.loadSetting();
		setAttribute("setting", setting);
		return DIR + "reg";
	}

	@RequestMapping
	public void doLogin(HttpServletRequest req, HttpServletResponse res) {
		User paraUser = (User) getBeanAll(User.class);
		User user = userCache.getUser(paraUser.getUserName());
		if (StringUtils.isNullOrEmpty(user)) {
			printMsg(res, new MsgEntity(-1, "该用户名不存在"));
			return;
		}
		paraUser.setUserPwd(EncryptionUtil.customEnCode(paraUser.getUserPwd()));
		if (!user.getUserPwd().equals(paraUser.getUserPwd())) {
			printMsg(res, new MsgEntity(1, "密码不正确"));
			return;
		}
		RequestUtil.setUser(req, user);
		printMsg(res, new MsgEntity(0, "登录成功"));
		return;
	}

	@RequestMapping
	public void doReg(HttpServletRequest req, HttpServletResponse res) {
		Setting setting = settingCache.loadSetting();
		if (!StringUtils.isNullOrEmpty(setting.getOpenReg())
				&& setting.getOpenReg() == 0) {
			printMsg(res, new MsgEntity(1, "本站已关闭注册"));
			return;
		}
		String verCode = getPara("verCode");
		if (StringUtils.isNullOrEmpty(verCode)) {
			printMsg(res, new MsgEntity(1, "请输入验证码"));
			return;
		}
		String sessionCode = (String) getSessionPara("piccode");
		setSessionPara("piccode", null);
		if (sessionCode == null || !sessionCode.equals(verCode)) {
			printMsg(res, new MsgEntity(4, "验证码有误"));
			return;
		}
		String invite = getPara("invite");
		Invite inviteModel = null;
		if (!StringUtils.isNullOrEmpty(setting.getOpenInvite())
				&& setting.getOpenInvite() == 1) {
			if (StringUtils.isNullOrEmpty(invite)) {
				printMsg(res, new MsgEntity(5, "请输入邀请码"));
				return;
			}
			inviteModel = inviteCache.getInvite(invite);
			if (StringUtils.isNullOrEmpty(inviteModel)) {
				printMsg(res, new MsgEntity(7, "邀请码不存在"));
				return;
			}
			if (inviteModel.getStatus() == 0) {
				printMsg(res, new MsgEntity(6, "该邀请码已使用"));
				return;
			}
		}
		User user = (User) getBeanAll(User.class);
		if (hasUser(user.getUserName())) {
			printMsg(res, new MsgEntity(1, "该用户名已被注册"));
			return;
		}
		user.setUserName(user.getUserName().trim());
		user.setUserPwd(EncryptionUtil.customEnCode(user.getUserPwd()));
		String uuid = JUUIDUtil.createUuid();
		user.setUuid(uuid);
		user.setCreateTime(new Date());
		userCache.save(user);
		user = userCache.getUserByUuid(uuid);
		if (!StringUtils.isNullOrEmpty(setting.getOpenInvite())
				&& setting.getOpenInvite() == 1) {
			inviteModel.setUser(user);
			inviteModel.setStatus(0);
			inviteModel.setUpdateTime(new Date());
			inviteCache.save(inviteModel);
		}
		RequestUtil.setUser(req, user);
		printMsg(res, new MsgEntity(0, "注册成功"));
		return;
	}

	@RequestMapping
	public void checkUserName(HttpServletRequest req, HttpServletResponse res) {

		String userName = getPara("userName");
		if (hasUser(userName)) {
			printMsg(res, new MsgEntity(1, "该用户名已被注册"));
			return;

		}
		printMsg(res, new MsgEntity(0, "该用户名可用"));
		return;
	}

	private boolean hasUser(String userName) {
		User user = userCache.getUser(userName);
		if (StringUtils.isNullOrEmpty(user)) {
			return false;
		}
		return true;
	}
}
