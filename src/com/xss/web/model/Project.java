package com.xss.web.model;

import java.util.Date;

import com.xss.web.model.base.BaseModel;

/**
 * 项目 Project entity. @author MyEclipse Persistence Tools
 */

public class Project extends BaseModel {

	// Fields

	private Integer id;
	private User user;
	private Module module;
	private String title;
	private String remark;
	private Date updateTime;
	private String uuid;
	private String uri;
	private String sortUri;
	private Integer openMobile;
	private Integer openEmail;
	private Integer count;
	private String filter;

	// Constructors

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Project(Integer id, User user, Module module, String title,
			String remark, Date updateTime, String uuid, String uri,
			String sortUri, Integer openMobile, Integer openEmail,
			Integer count, String filter) {
		super();
		this.id = id;
		this.user = user;
		this.module = module;
		this.title = title;
		this.remark = remark;
		this.updateTime = updateTime;
		this.uuid = uuid;
		this.uri = uri;
		this.sortUri = sortUri;
		this.openMobile = openMobile;
		this.openEmail = openEmail;
		this.count = count;
		this.filter = filter;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	/** default constructor */
	public Project() {
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

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getSortUri() {
		return sortUri;
	}

	public void setSortUri(String sortUri) {
		this.sortUri = sortUri;
	}

	public Integer getOpenMobile() {
		return openMobile;
	}

	public void setOpenMobile(Integer openMobile) {
		this.openMobile = openMobile;
	}

	public Integer getOpenEmail() {
		return openEmail;
	}

	public void setOpenEmail(Integer openEmail) {
		this.openEmail = openEmail;
	}

	public Project(Integer id, User user, Module module, String title,
			String remark, Date updateTime, String uuid, String uri,
			String sortUri, Integer openMobile, Integer openEmail, Integer count) {
		super();
		this.id = id;
		this.user = user;
		this.module = module;
		this.title = title;
		this.remark = remark;
		this.updateTime = updateTime;
		this.uuid = uuid;
		this.uri = uri;
		this.sortUri = sortUri;
		this.openMobile = openMobile;
		this.openEmail = openEmail;
		this.count = count;
	}

}