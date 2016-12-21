package com.xss.web.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.xss.web.model.base.BaseModel;

/**
 * 模版 Module entity. @author MyEclipse Persistence Tools
 */

public class Module extends BaseModel {

	// Fields

	private Integer id;
	private User user;
	private String remark;
	private String content;
	private Integer type;
	private Date updateTime = new Date();
	private Set projects = new HashSet(0);
	private String title;

	public Module() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set getProjects() {
		return projects;
	}

	public void setProjects(Set projects) {
		this.projects = projects;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Module(Integer id, User user, String remark, String content,
			Integer type, Date updateTime, Set projects, String title) {
		super();
		this.id = id;
		this.user = user;
		this.remark = remark;
		this.content = content;
		this.type = type;
		this.updateTime = updateTime;
		this.projects = projects;
		this.title = title;
	}

}