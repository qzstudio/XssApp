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
import com.xss.web.model.Project;
import com.xss.web.service.LetterService;
import com.xss.web.service.ProjectService;
import com.xss.web.util.StringUtils;

@Service
public class ProjectCache extends BaseCache {

	@Resource
	ProjectService projectService;
	@Resource
	LetterService letterService;
	@CacheHandle(key=CacheFinal.USER_PROJECT_LIST_KEY ,validTime=65)
	public List<Project> loadProjects(Integer userId) {
		List<Project> projects = (List<Project>) projectService.findByField(Project.class,
				"id", true, "user.id", userId);
		return projects;
	}
	@CacheHandle(key=CacheFinal.PROJECT_PAGER_KEY ,validTime=65)
	public Pager<Project> getProjects(Project project, Pager<Project> pager) {
		Pager<Project> projects = (Pager<Project>) projectService.findPagerByObject(project,
				pager, "id", true);
		return projects;
	}
	@CacheHandle(key=CacheFinal.PROJECT_COUNT_KEY ,validTime=65)
	public Integer getProjectCount() {
		Integer count = projectService.getProjectCount();
		return count;

	}
	@CacheHandle(key=CacheFinal.USER_PROJECT_LETER_COUNT_KEY ,validTime=65)
	public Integer getLeterCount(Integer projectId) {
		Integer count = (Integer) projectService.getLeterCount(projectId);
		return count;

	}
	@DelCacheHandle(keys={CacheFinal.USER_PROJECT_INFO_KEY,CacheFinal.USER_PROJECT_REPLACE_KEY,CacheFinal.USER_PROJECT_LIST_KEY,CacheFinal.PROJECT_PAGER_KEY})
	public void save(Project project) {
		if (StringUtils.isNullOrEmpty(project)) {
			return;
		}
		projectService.saveOrUpdate(project);
	}
	@DelCacheHandle(keys={CacheFinal.USER_PROJECT_INFO_KEY,CacheFinal.USER_PROJECT_REPLACE_KEY,CacheFinal.USER_PROJECT_LIST_KEY,CacheFinal.PROJECT_PAGER_KEY})
	public void del(Integer... id) {
		if (StringUtils.isNullOrEmpty(id)) {
			return;
		}
		letterService.delLetter(id);
		projectService.deleteProject(id);
	}
	@CacheHandle(key=CacheFinal.USER_PROJECT_REPLACE_KEY ,validTime=65)
	public Project getProjectReplaceCurr(Integer id, Integer userId,
			String title) {
		Where where = new Where().set("user.id", userId).set("title", title)
				.set("id", "<>", id);
		Project	project = (Project) projectService.findFirstByObject(new Project(),
				where);
		return project;
	}
	@CacheHandle(key=CacheFinal.USER_PROJECT_INFO_KEY ,validTime=65)
	public Project getProject(String uuid) {
		Project	project = (Project) projectService.findFirstByField(Project.class,
				"uuid", uuid);
		return project;
	}
	@CacheHandle(key=CacheFinal.USER_PROJECT_INFO_KEY ,validTime=65)
	public Project getProject(Integer id) {
		Project project = (Project) projectService.get(Project.class, id);
		return project;
	}

}
