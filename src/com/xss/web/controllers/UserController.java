package com.xss.web.controllers;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xss.web.base.cache.CacheFinal;
import com.xss.web.base.page.Pager;
import com.xss.web.base.wrapper.XssHttpServletRequestWrapper;
import com.xss.web.cache.EmailCache;
import com.xss.web.cache.LetterCache;
import com.xss.web.cache.ModuleCache;
import com.xss.web.cache.ProjectCache;
import com.xss.web.cache.SuffixCache;
import com.xss.web.cache.UserCache;
import com.xss.web.controllers.base.BaseController;
import com.xss.web.entity.MsgEntity;
import com.xss.web.model.Email;
import com.xss.web.model.Letter;
import com.xss.web.model.LetterParas;
import com.xss.web.model.Module;
import com.xss.web.model.Project;
import com.xss.web.model.User;
import com.xss.web.util.EncryptionUtil;
import com.xss.web.util.JUUIDUtil;
import com.xss.web.util.PropertUtil;
import com.xss.web.util.RequestUtil;
import com.xss.web.util.SortUrlUtil;
import com.xss.web.util.StringUtils;

@Controller
public class UserController extends BaseController {

	@Resource
	SuffixCache SuffixCache;
	@Resource
	ModuleCache moduleCache;
	@Resource
	ProjectCache projectCache;
	@Resource
	UserCache userCache;
	@Resource
	LetterCache letterCache;
	@Resource
	EmailCache emailCache;
	static final String DIR = "user/";

	@RequestMapping
	public String index(HttpServletRequest req, HttpServletResponse res) {
		loadModule(req);
		loadUserProject(req);
		return DIR + "project_list";
	}

	@RequestMapping
	public String setting(HttpServletRequest req, HttpServletResponse res) {
		loadModule(req);
		loadUserProject(req);
		return DIR + "setting";
	}

	@RequestMapping
	public String moduleAdd(HttpServletRequest req, HttpServletResponse res) {
		loadModule(req);
		loadUserProject(req);
		return DIR + "module_info";
	}

	@RequestMapping
	public String projectAdd(HttpServletRequest req, HttpServletResponse res) {
		loadModule(req);
		loadUserProject(req);
		return DIR + "project_info";
	}

	@RequestMapping
	public String moduleEdit(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		Integer id = getParaInteger("id");
		Module module = moduleCache.getModule(id);
		if (module.getType() != 0
				&& module.getUser().getId().intValue() != user.getId()
						.intValue()) {
			return "404";
		}
		try {
			module.setContent(module.getContent().replace("<", "&lt;").replace(">","&gt;"));
		} catch (Exception e) {
		}
		setAttribute("module", module);
		loadModule(req);
		loadUserProject(req);
		return DIR + "module_info";
	}

	@RequestMapping
	public String projectEdit(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		Integer id = getParaInteger("id");
		Project project = projectCache.getProject(id);
		if (project.getUser().getId().intValue() != user.getId().intValue()) {
			return "404";
		}
		setAttribute("project", project);
		loadModule(req);
		loadUserProject(req);
		return DIR + "project_info";
	}

	@RequestMapping
	public void saveProject(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		Project project = (Project) getBeanAll(Project.class);
		// 检查权限
		if (!StringUtils.isNullOrEmpty(project.getId())) {
			Project tmpProject = projectCache.getProject(project.getId());
			if (tmpProject.getUser().getId().intValue() != user.getId()
					.intValue()) {
				printMsg(res, new MsgEntity(-1, "无权操作"));
				return;
			}
		}
		String uuid = JUUIDUtil.createUuid();
		// 检查是否已存在
		Project tmpProject = projectCache.getProjectReplaceCurr(
				project.getId(), user.getId(), project.getTitle());
		if (!StringUtils.isNullOrEmpty(tmpProject)) {
			printMsg(res, new MsgEntity(1, "已存在该名称模块"));
			return;
		}
		project.setUpdateTime(new Date());
		project.setUser(user);
		project.setUuid(uuid);
		projectCache.save(project);
		project = projectCache.getProject(uuid);
		project.setUri(getSessionPara("basePath") + "s/" + project.getId()
				+ "." + getAttribute("defSuffix"));
		String sortUti = getSortUti(project.getUri());
		if (!StringUtils.isNullOrEmpty(sortUti)) {
			project.setSortUri(sortUti);
		}
		projectCache.save(project);
		printMsg(res, new MsgEntity(0, "保存成功"));
	}

	@RequestMapping
	public String loadEmails(HttpServletRequest req, HttpServletResponse res) {
		List<Email> emails = emailCache.loadEmails();
		setAttribute("emails", emails);
		loadModule(req);
		loadUserProject(req);
		return DIR + "email_list";
	}

	@RequestMapping
	public String loadLetters(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		Integer id = getParaInteger("id");
		Project project = null;
		if(!StringUtils.isNullOrEmpty(id)){
			project=projectCache.getProject(id);
		}
		if (project == null) {
			project = new Project();
		}
		User myUser = new User();
		myUser.setId(user.getId());
		project.setUser(myUser);
		Letter letter = new Letter();
		letter.setProject(project);
		Pager<Letter> pager = (Pager<Letter>) getBeanAll(Pager.class);
		// 加载信封列表
		pager = letterCache.getLetters(letter, pager);
		setAttribute("pager", pager);
		loadModule(req);
		loadUserProject(req);
		return DIR + "lette_list";
	}

	@RequestMapping
	public void delLetter(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		Integer[] id = getParaIntegers("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(-1, "请选择信封"));
			return;
		}
		List<Letter> letters = letterCache.getLetter(user.getId(), id);
		// 删除项目
		List<?> ids = PropertUtil.getFieldValues(letters, "id");
		if (!StringUtils.isNullOrEmpty(ids)) {
			letterCache.delLetter(StringUtils.getIntegerParas(ids.toArray()));
		}

		printMsg(res, new MsgEntity(0, "删除成功"));
	}

	@RequestMapping
	public String letterInfo(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		Integer id = getParaInteger("id");
		Letter letter = letterCache.getLetter(id);
		setAttribute("letter", letter);
		// 检查权限
		if (!StringUtils.isNullOrEmpty(letter)) {
			if (letter.getProject().getUser().getId().intValue() != user
					.getId().intValue()) {
				return "404";
			}
		}
		List<LetterParas> paras = letterCache.getLetterParas(id);
		setAttribute("paras", paras);
		loadModule(req);
		loadUserProject(req);
		loadIpAddress(letter.getIp());
		return DIR + "letter_paras";
	}

	@RequestMapping
	public void delProject(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		Integer id = getParaInteger("id");
		Project project = projectCache.getProject(id);
		// 检查权限
		if (!StringUtils.isNullOrEmpty(project)) {
			if (!StringUtils.isNullOrEmpty(project.getUser())
					&& project.getUser().getId().intValue() != user.getId()
							.intValue()) {
				printMsg(res, new MsgEntity(-1, "无权操作"));
				return;
			}
		}
		// 删除项目
		projectCache.del(project.getId());
		printMsg(res, new MsgEntity(0, "删除成功"));
	}

	@RequestMapping
	public void delModule(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		Integer id = getParaInteger("id");
		Module module = moduleCache.getModule(id);
		// 检查权限
		if (!StringUtils.isNullOrEmpty(module)) {
			if (!StringUtils.isNullOrEmpty(module.getUser())
					&& module.getUser().getId().intValue() != user.getId()
							.intValue()) {
				printMsg(res, new MsgEntity(-1, "无权操作"));
				return;
			}
		}
		// 删除项目
		moduleCache.del(module.getId());
		printMsg(res, new MsgEntity(0, "删除成功"));
	}

	@RequestMapping
	public void saveModule(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		Module module = (Module) getBeanAll(Module.class);
		// 检查权限
		if (!StringUtils.isNullOrEmpty(module.getId())) {
			Module tmpModule = moduleCache.getModule(module.getId());
			if (tmpModule.getUser().getId().intValue() != user.getId()
					.intValue()) {
				printMsg(res, new MsgEntity(-1, "无权操作"));
				return;
			}
		}
		// 检查是否已存在
		Module tmpModule = moduleCache.getModuleReplaceCurr(module.getId(),
				user.getId(), module.getTitle());
		if (!StringUtils.isNullOrEmpty(tmpModule)) {
			printMsg(res, new MsgEntity(1, "已存在该名称模块"));
			return;
		}
		String content=((XssHttpServletRequestWrapper)req).getOrgRequest().getParameter("content");
		module.setContent(content);
		module.setUser(user);
		moduleCache.save(module);
		printMsg(res, new MsgEntity(0, "保存成功"));
	}

	@RequestMapping
	public void loginOut(HttpServletRequest req, HttpServletResponse res) {
		RequestUtil.setUser(req, null);
		try {
			RequestUtil.setUser(req, null);
			setSessionPara("action", "loginOut");
			res.sendRedirect(getAttribute("basePath") + "index."
					+ getAttribute("defSuffix"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping
	public String loadUserModule(HttpServletRequest req) {
		loadModule(req);
		loadUserProject(req);
		return DIR + "module_list";
	}

	@RequestMapping
	public void saveUserInfo(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		user = (User) getBeanAccept(user, "email", "mobile");
		User tmpUser = userCache.getOtherUser(user.getEmail(), user.getId());
		if (!StringUtils.isNullOrEmpty(tmpUser)) {
			printMsg(res, new MsgEntity(1, "该邮箱已被使用"));
			return;
		}
		tmpUser = userCache.getOtherUser(user.getMobile(), user.getId());
		if (!StringUtils.isNullOrEmpty(tmpUser)) {
			printMsg(res, new MsgEntity(2, "该手机已被使用"));
			return;
		}
		userCache.save(user);
		printMsg(res, new MsgEntity(0, "保存成功"));
		return;
	}

	@RequestMapping
	public String modifyPwd(HttpServletRequest req, HttpServletResponse res) {

		return DIR + "modify_pwd";
	}

	@RequestMapping
	public void doModifyPwd(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) RequestUtil.getUser(req);
		String oldPwd = getPara("oldPwd");
		oldPwd = EncryptionUtil.customEnCode(oldPwd);
		if (!user.getUserPwd().equals(oldPwd)) {
			printMsg(res, new MsgEntity(3, "旧密码有误"));
			return;
		}
		String newPwd = getPara("userPwd");
		if (StringUtils.isNullOrEmpty(newPwd)) {
			printMsg(res, new MsgEntity(2, "请输入新密码"));
			return;
		}
		user.setUserPwd(EncryptionUtil.customEnCode(newPwd));
		userCache.save(user);
		RequestUtil.setUser(req, user);
		printMsg(res, new MsgEntity(0, "修改成功"));
		return;
	}

	void loadUserProject(HttpServletRequest req) {
		User user = (User) RequestUtil.getUser(req);
		List<Project> projects = projectCache.loadProjects(user.getId());
		if (!StringUtils.isNullOrEmpty(projects)) {
			for (Project proj : projects) {
				try {
					Integer num = projectCache.getLeterCount(proj.getId());
					proj.setCount(num);
				} catch (Exception e) {
				}
			}
		}
		setAttribute("projects", projects);
	}

	void loadModule(HttpServletRequest req) {
		User user = (User) RequestUtil.getUser(req);
		List<Module> sysModules = moduleCache.loadSysModules();
		List<Module> userModules = moduleCache.loadUserModules(user.getId());
		setAttribute("sysModules", sysModules);
		setAttribute("userModules", userModules);
	}

	public String getSortUti(String url) {
		String key = CacheFinal.PROJECR_SORT_URI_KEY.toString() + url;
		String sortUrl = (String) projectCache.getCache(key);
		if (!StringUtils.isNullOrEmpty(sortUrl)) {
			return sortUrl;
		}
		sortUrl = SortUrlUtil.getSortUrl(url);
		if (!StringUtils.isNullOrEmpty(sortUrl)) {
			projectCache.addCache(key, sortUrl, 60 * 10);
		}
		return sortUrl;
	}
}
