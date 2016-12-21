package com.xss.web.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xss.web.annotation.Power;
import com.xss.web.base.page.Pager;
import com.xss.web.cache.AdminCache;
import com.xss.web.cache.EmailCache;
import com.xss.web.cache.InviteCache;
import com.xss.web.cache.LetterCache;
import com.xss.web.cache.MenuCache;
import com.xss.web.cache.ModuleCache;
import com.xss.web.cache.ProjectCache;
import com.xss.web.cache.RoleCache;
import com.xss.web.cache.SettingCache;
import com.xss.web.cache.SuffixCache;
import com.xss.web.cache.UserCache;
import com.xss.web.controllers.base.BaseController;
import com.xss.web.enm.PowerEnum;
import com.xss.web.entity.MsgEntity;
import com.xss.web.model.Admin;
import com.xss.web.model.Email;
import com.xss.web.model.Invite;
import com.xss.web.model.Letter;
import com.xss.web.model.LetterParas;
import com.xss.web.model.Menus;
import com.xss.web.model.Module;
import com.xss.web.model.Project;
import com.xss.web.model.Role;
import com.xss.web.model.Setting;
import com.xss.web.model.Suffix;
import com.xss.web.model.User;
import com.xss.web.util.EncryptionUtil;
import com.xss.web.util.RequestUtil;
import com.xss.web.util.StringUtils;

@Controller
public class AdminController extends BaseController {
	static final String DIR = "back/";
	@Resource
	SettingCache settingCache;
	@Resource
	AdminCache adminCache;
	@Resource
	UserCache userCache;
	@Resource
	LetterCache letterCache;
	@Resource
	ProjectCache projectCache;
	@Resource
	ModuleCache moduleCache;
	@Resource
	SuffixCache suffixCache;
	@Resource
	RoleCache roleCache;
	@Resource
	MenuCache menuCache;
	@Resource
	EmailCache emailCache;
	@Resource
	InviteCache inviteCache;

	@RequestMapping
	public String login(HttpServletRequest req, HttpServletResponse res) {
		return DIR + "login";
	}

	@RequestMapping
	public void loginOut(HttpServletRequest req, HttpServletResponse res) {
		RequestUtil.setAdmin(req, null);
		try {
			res.sendRedirect(getAttribute("basePath") + "admin/login."
					+ getAttribute("defSuffix"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping
	public String modifyAdmin(HttpServletRequest req, HttpServletResponse res) {
		return DIR + "modify_admin";
	}

	@RequestMapping
	@Power(value="emailSetting",resType=PowerEnum.PAGE)
	public String sysEmailAdmin(HttpServletRequest req, HttpServletResponse res) {
		List<Email> emails = emailCache.loadEmails();
		setAttribute("emails", emails);
		return DIR + "email_list";
	}

	@RequestMapping
	@Power(value="emailSetting",resType=PowerEnum.PAGE)
	public String emailEdit(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (!StringUtils.isNullOrEmpty(id)) {
			Email email = emailCache.getEmail(id);
			setAttribute("email", email);
		}
		return DIR + "email_edit";
	}

	@RequestMapping
	@Power(value="emailSetting",resType=PowerEnum.JSON)
	public void emailSave(HttpServletRequest req, HttpServletResponse res) {
		Email email = (Email) getBeanAll(Email.class);
		if (StringUtils.findEmptyIndex(email.getEmail(), email.getPassword(),
				email.getSmtp()) > -1) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		emailCache.save(email);
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	@Power(value="emailSetting",resType=PowerEnum.JSON)
	public void emailDel(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		emailCache.delete(id);
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	@Power(value="adminSetting",resType=PowerEnum.PAGE)
	public String sysUserAdmin(HttpServletRequest req, HttpServletResponse res) {
		List<Admin> admins = adminCache.loadAdmins();
		setAttribute("admins", admins);
		return DIR + "admin_list";
	}

	@RequestMapping
	@Power(value="adminSetting",resType=PowerEnum.PAGE)
	public String adminEdit(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (!StringUtils.isNullOrEmpty(id)) {
			Admin admin = adminCache.getAdmin(id);
			setAttribute("admin", admin);
		}
		List<Role> roles = roleCache.loadRoles();
		setAttribute("roles", roles);
		return DIR + "admin_edit";
	}

	@RequestMapping
	@Power(value="adminSetting",resType=PowerEnum.JSON)
	public void adminDel(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		adminCache.delete(id);
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	@Power(value="adminSetting",resType=PowerEnum.JSON)
	public void adminSave(HttpServletRequest req, HttpServletResponse res) {
		Admin admin = (Admin) getBeanAll(Admin.class);
		if (StringUtils.findEmptyIndex(admin.getUserName(), admin.getUserPwd(),
				admin.getRole().getId()) > -1) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		admin.setUserPwd(EncryptionUtil.customEnCode(admin.getUserPwd()));
		adminCache.save(admin);
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	@Power(value="userSetting",resType=PowerEnum.PAGE)
	public String userList(HttpServletRequest req, HttpServletResponse res) {
		User user = (User) getBeanAll(User.class);
		Pager<User> pager = (Pager<User>) getBeanAll(Pager.class);
		pager = userCache.getUsers(user, pager);
		setAttribute("pager", pager);
		keepParas();
		return DIR + "user_list";
	}

	@RequestMapping
	@Power(value="userSetting",resType=PowerEnum.PAGE)
	public String userEdit(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (!StringUtils.isNullOrEmpty(id)) {
			User user = userCache.getUser(id);
			setAttribute("user", user);
		}
		return DIR + "user_edit";
	}

	@RequestMapping
	@Power(value="userSetting",resType=PowerEnum.JSON)
	public void userSave(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		User user = userCache.getUser(id);
		user = (User) getBeanAccept(user, "userName", "email", "mobile");
		userCache.save(user);
		printMsg(res, new MsgEntity(0, "操作成功"));

	}

	@RequestMapping
	@Power(value="userSetting",resType=PowerEnum.JSON)
	public void userDel(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		userCache.delete(id);
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	@Power(value="projectSetting",resType=PowerEnum.PAGE)
	public String projectList(HttpServletRequest req, HttpServletResponse res) {
		Project project = (Project) getBeanAll(Project.class);
		Pager<Project> pager = (Pager<Project>) getBeanAll(Pager.class);
		pager = projectCache.getProjects(project, pager);
		setAttribute("pager", pager);
		keepParas();
		List<Module> modules = moduleCache.loadAllModules();
		setAttribute("modules", modules);
		return DIR + "project_list";
	}

	@RequestMapping
	@Power(value="projectSetting",resType=PowerEnum.PAGE)
	public String projectEdit(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (!StringUtils.isNullOrEmpty(id)) {
			Project project = projectCache.getProject(id);
			setAttribute("project", project);
		}
		List<Module> modules = moduleCache.loadAllModules();
		setAttribute("modules", modules);
		return DIR + "project_edit";
	}

	@RequestMapping
	@Power(value="projectSetting",resType=PowerEnum.JSON)
	public void projectSave(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		Project project = projectCache.getProject(id);
		project = (Project) getBeanAccept(project, "id", "module.id", "title",
				"remark");
		project.setUpdateTime(new Date());
		projectCache.save(project);
		printMsg(res, new MsgEntity(0, "操作成功"));

	}

	@RequestMapping
	@Power(value="projectSetting",resType=PowerEnum.JSON)
	public void projectDel(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		projectCache.del(id);
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	@Power(value="moduleSetting",resType=PowerEnum.PAGE)
	public String moduleList(HttpServletRequest req, HttpServletResponse res) {
		Module module = (Module) getBeanAll(Module.class);
		Pager<Module> pager = (Pager<Module>) getBeanAll(Pager.class);
		pager = moduleCache.getPager(module, pager);
		setAttribute("pager", pager);
		keepParas();
		return DIR + "module_list";
	}

	@RequestMapping
	@Power(value="moduleSetting",resType=PowerEnum.PAGE)
	public String moduleEdit(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (!StringUtils.isNullOrEmpty(id)) {
			Module module = moduleCache.getModule(id);
			try {
				module.setContent(module.getContent().replace("<", "&lt;").replace(">","&gt;"));
			} catch (Exception e) {
			}
			setAttribute("module", module);
		}
		List<Module> modules = moduleCache.loadAllModules();
		setAttribute("modules", modules);
		return DIR + "module_edit";
	}

	@RequestMapping
	@Power(value="moduleSetting",resType=PowerEnum.JSON)
	public void moduleSave(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		Module module = moduleCache.getModule(id);
		module = (Module) getBeanAccept(module, "title", "remark", "content",
				"id", "type");//只接受这几个参数反射到bean
		module.setUpdateTime(new Date());
		moduleCache.save(module);
		printMsg(res, new MsgEntity(0, "操作成功"));

	}

	@RequestMapping
	@Power(value="moduleSetting",resType=PowerEnum.JSON)
	public void moduleDel(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		moduleCache.del(id);
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	@Power(value="letterSetting",resType=PowerEnum.PAGE)
	public String letterList(HttpServletRequest req, HttpServletResponse res) {
		Letter letter = (Letter) getBeanAll(Letter.class);
		Pager<Letter> pager = (Pager<Letter>) getBeanAll(Pager.class);
		pager = letterCache.getLetters(letter, pager);
		setAttribute("pager", pager);
		keepParas();
		return DIR + "letter_list";
	}

	@RequestMapping
	@Power(value="letterSetting",resType=PowerEnum.PAGE)
	public String letterEdit(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (!StringUtils.isNullOrEmpty(id)) {
			Letter letter = letterCache.getLetter(id);
			setAttribute("letter", letter);
			List<LetterParas> paras = letterCache.getLetterParas(id);
			setAttribute("paras", paras);
			loadIpAddress(letter.getIp());
		}
		return DIR + "letter_edit";
	}

	@RequestMapping
	@Power(value="letterSetting",resType=PowerEnum.JSON)
	public void letterDel(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		letterCache.delLetter(id);
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	@Power(value="inviteSetting",resType=PowerEnum.PAGE)
	public String inviteList(HttpServletRequest req, HttpServletResponse res) {
		Invite invite = (Invite) getBeanAll(Invite.class);
		Pager<Invite> pager = (Pager<Invite>) getBeanAll(Pager.class);
		pager = inviteCache.getPager(invite, pager);
		setAttribute("pager", pager);
		keepParas();
		return DIR + "invite_list";
	}

	@RequestMapping
	@Power(value="inviteSetting",resType=PowerEnum.JSON)
	public void inviteDel(HttpServletRequest req, HttpServletResponse res) {
		Integer[] id = getParaIntegers("id");
		if (StringUtils.isNullOrEmpty(id)) {
			printMsg(res, new MsgEntity(-1, "未选择任何数据"));
			return;
		}
		inviteCache.delete(id);
		printMsg(res, new MsgEntity(0, "删除成功"));
	}

	@RequestMapping
	@Power(value="inviteSetting",resType=PowerEnum.PAGE)
	public String inviteImport(HttpServletRequest req, HttpServletResponse res) {
		return DIR + "invite_import";
	}

	@RequestMapping
	@Power(value="inviteSetting",resType=PowerEnum.JSON)
	public void inviteDoImport(HttpServletRequest req, HttpServletResponse res) {
		Integer num = getParaInteger("num");
		if (StringUtils.isNullOrEmpty(num) || num < -1) {
			printMsg(res, new MsgEntity(1, "参数有误"));
			return;
		}
		if (num > 100) {
			printMsg(res, new MsgEntity(1, "每次生成数量不得大于100"));
			return;
		}
		inviteCache.importInvite(num);
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	public void saveCurrAdmin(HttpServletRequest req, HttpServletResponse res) {
		Admin currAdmin = (Admin) RequestUtil.getAdmin(req);
		String newUsername = getPara("username");
		String password = getPara("password");
		String newPwd = getPara("newPwd");
		if (StringUtils.findEmptyIndex(newUsername, password, newPwd) > -1) {
			printMsg(res, new MsgEntity(1, "用户名或密码为空"));
			return;
		}
		password = EncryptionUtil.customEnCode(password);
		if (!password.equals(currAdmin.getUserPwd())) {
			printMsg(res, new MsgEntity(4, "旧密码有误"));
			return;
		}
		Admin admin = adminCache.getAdmin(newUsername);
		if (!StringUtils.isNullOrEmpty(admin)
				&& admin.getId().intValue() != currAdmin.getId().intValue()) {
			printMsg(res, new MsgEntity(6, "该用户名已被使用"));
			return;
		}
		newPwd = EncryptionUtil.customEnCode(newPwd);
		currAdmin.setUserName(newUsername);
		currAdmin.setUserPwd(newPwd);
		adminCache.save(currAdmin);
		RequestUtil.setAdmin(req, currAdmin);
		printMsg(res, new MsgEntity(0, "操作成功"));
		return;
	}

	@RequestMapping
	public void doLogin(HttpServletRequest req, HttpServletResponse res) {
		String username = getPara("username");
		String password = getPara("password");
		String verCode = getPara("verCode");
		if (StringUtils.isNullOrEmpty(verCode)) {
			printMsg(res, new MsgEntity(3, "验证码为空"));
			return;
		}
		if (StringUtils.findEmptyIndex(username, password) > -1) {
			printMsg(res, new MsgEntity(1, "用户名或密码为空"));
			return;
		}
		String sessionCode = (String) getSessionPara("piccode");
		setSessionPara("piccode", null);
		if (sessionCode == null || !sessionCode.equals(verCode)) {
			printMsg(res, new MsgEntity(4, "验证码有误"));
			return;
		}
		Admin admin = adminCache.getAdmin(username);
		if (StringUtils.isNullOrEmpty(admin)) {
			printMsg(res, new MsgEntity(2, "该用户不存在"));
			return;
		}
		password = EncryptionUtil.customEnCode(password);
		if (!password.equals(admin.getUserPwd())) {
			printMsg(res, new MsgEntity(3, "密码有误"));
			return;
		}
		RequestUtil.setAdmin(req, admin);
		setSessionPara("loginTime", new Date());
		printMsg(res, new MsgEntity(0, "登录成功"));
		return;
	}

	@RequestMapping
	@Power(value="backIndex",resType=PowerEnum.PAGE)
	public String base(HttpServletRequest req, HttpServletResponse res) {
		Integer userCount = userCache.getUserCount();
		setAttribute("userCount", userCount);
		Integer letterCount = letterCache.getLetterCount();
		setAttribute("letterCount", letterCount);
		Integer projectCount = projectCache.getProjectCount();
		setAttribute("projectCount", projectCount);
		Integer moduleCount = moduleCache.getModuleCount();
		setAttribute("moduleCount", moduleCount);
		return DIR + "base";
	}

	@RequestMapping
	@Power(value="roleSetting",resType=PowerEnum.PAGE)
	public String roleList(HttpServletRequest req) {
		List<Role> roleList = roleCache.loadRoles();
		setAttribute("roleList", roleList);
		return DIR + "role_list";
	}
	@RequestMapping
	@Power(value="roleSetting",resType=PowerEnum.JSON)
	public void roleDel(HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (!StringUtils.isNullOrEmpty(id)) {
		roleCache.del(id);
		}
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	@Power(value="roleSetting",resType=PowerEnum.PAGE)
	public String roleEdit(HttpServletResponse res) {
		Integer id = getParaInteger("id");
		if (!StringUtils.isNullOrEmpty(id)) {
			Role role =roleCache.loadRole(id);
			setAttribute("role", role);
		}
		return DIR + "role_edit";
	}

	@RequestMapping
	@Power(value="roleSetting",resType=PowerEnum.JSON)
	public void roleSave(HttpServletResponse res) {
		Integer id=getParaInteger("id");
		Role role=new Role();
		if(!StringUtils.isNullOrEmpty(id)){
			role =roleCache.loadRole(id);
		}
		role = (Role) getBeanAll(role);
		roleCache.save(role);
		printMsg(res, new MsgEntity(0, "操作成功"));
	}

	@RequestMapping
	@Power(value="roleSetting",resType=PowerEnum.PAGE)
	public String disPermis(HttpServletRequest req) {
		Integer id = getParaInteger("id");
		Role role = roleCache.loadRole(id);
		if (StringUtils.isNullOrEmpty(role)) {
			return "404";
		}
		setAttribute("role", role);
		List<Menus> menuList = menuCache.loadBaseMenus();
		setAttribute("menuList", menuList);
		if (!StringUtils.isNullOrEmpty(role.getMenus())) {
			Integer[] ids = StringUtils.splitByMosaicIntegers(role.getMenus(),
					",");
			Map<Integer, Object> roleMap = new HashMap<Integer, Object>();
			for (Integer tmp : ids) {
				roleMap.put(tmp, tmp);
			}
			setAttribute("roleMap", roleMap);
		}
		return DIR + "dis_permis";
	}

	@RequestMapping
	@Power(value="roleSetting",resType=PowerEnum.JSON)
	public void savePermis(HttpServletRequest req, HttpServletResponse res) {
		Integer id = getParaInteger("roleId");
		Role role = roleCache.loadRole(id);
		if (StringUtils.isNullOrEmpty(role)) {
			printMsg(res, new MsgEntity(-1, "参数有误"));
			return;
		}
		try {
			Integer[] ids = getParaIntegers("ids[]");
			String menuIds = StringUtils.collectionMosaic(ids, ",");
			role.setMenus(menuIds);
			roleCache.save(role);
			printMsg(res, new MsgEntity(0, "操作成功"));
			return;
		} catch (Exception e) {
			printMsg(res, new MsgEntity(1, "系统出错"));
			return;
		}
	}

	@RequestMapping
	@Power(value="suffixSetting",resType=PowerEnum.PAGE)
	public String adminSuffix(HttpServletRequest req) {
		List<Suffix> suffixList = suffixCache.loadSuffix();
		setAttribute("suffixList", suffixList);
		return DIR + "suffix";
	}

	@RequestMapping
	@Power(value="suffixSetting",resType=PowerEnum.JSON)
	public void updateSuffix(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		String[] suffix = req.getParameterValues("suffix[]");
		if (suffix == null || suffix.length < 1) {
			printMsg(res, new MsgEntity(1, "至少保留一个后缀"));
			return;
		}
		try {
			Integer[] intSuffix = new Integer[suffix.length];
			for (int i = 0; i < suffix.length; i++) {
				intSuffix[i] = Integer.valueOf(suffix[i]);
			}
			suffixCache.updateSuffix(intSuffix);
			printMsg(res, new MsgEntity(0, "操作成功"));
			return;
		} catch (Exception e) {
			e.printStackTrace();
			printMsg(res, new MsgEntity(4, "系统出错"));
			return;
		}
	}

	@RequestMapping
	@Power(value="suffixSetting",resType=PowerEnum.JSON)
	public void defaultSuffix(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		try {
			Integer sid = getParaInteger("id");
			Suffix suffix = suffixCache.getSuffix(sid);
			if (suffix == null) {
				printMsg(res, new MsgEntity(3, "后缀不存在"));
				return;
			}
			suffix.setStatus(2);
			suffixCache.updateSuffix(suffix);
			printMsg(res, new MsgEntity(0, "设置成功"));
			return;
		} catch (Exception e) {
			printMsg(res, new MsgEntity(4, "系统出错"));
			return;
		}
	}

	@RequestMapping
	@Power(value="baseSetting",resType=PowerEnum.PAGE)
	public String setting(HttpServletRequest req, HttpServletResponse res) {
		Setting setting = settingCache.loadSetting();
		setAttribute("setting", setting);
		return DIR + "setting";
	}

	@RequestMapping
	@Power(value="baseSetting",resType=PowerEnum.JSON)
	public void saveSetting(HttpServletRequest req, HttpServletResponse res) {
		try {
			Setting setting = (Setting) getBeanAll("setting", Setting.class);
			settingCache.saveSetting(setting);
			printMsg(res, new MsgEntity(0, "保存成功"));
			return;
		} catch (Exception e) {
			e.printStackTrace();
			printMsg(res, new MsgEntity(-1, "系统出错"));
			return;
		}

	}

	@RequestMapping
	@Power(value="emailSetting",resType=PowerEnum.PAGE)
	public String emailList(HttpServletRequest req, HttpServletResponse res) {
		return DIR + "email_list";
	}

	@RequestMapping
	public String index(HttpServletRequest req, HttpServletResponse res) {
		return DIR + "index";
	}

	public static void main(String[] args) {
	}
}
