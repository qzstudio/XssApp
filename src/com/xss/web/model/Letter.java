package com.xss.web.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.xss.web.model.base.BaseModel;

/**
 * 信封 Letter entity. @author MyEclipse Persistence Tools
 */

public class Letter extends BaseModel {

	// Fields

	private Integer id;
	private Project project;
	private String refUrl;
	private Date updateTime;
	private Set letterParases = new HashSet(0);
	private String uuid;
	private String context;
	private String ip;

	// Constructors

	/** default constructor */
	public Letter() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getRefUrl() {
		return refUrl;
	}

	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set getLetterParases() {
		return letterParases;
	}

	public void setLetterParases(Set letterParases) {
		this.letterParases = letterParases;
	}

	public String getUuid() {
		return uuid;
	}

	public Letter(Integer id, Project project, String refUrl, Date updateTime,
			Set letterParases, String uuid, String context) {
		super();
		this.id = id;
		this.project = project;
		this.refUrl = refUrl;
		this.updateTime = updateTime;
		this.letterParases = letterParases;
		this.uuid = uuid;
		this.context = context;
	}

	public String getContext() {
		return context;
	}

	public Letter(Integer id, Project project, String refUrl, Date updateTime,
			Set letterParases, String uuid, String context, String ip) {
		super();
		this.id = id;
		this.project = project;
		this.refUrl = refUrl;
		this.updateTime = updateTime;
		this.letterParases = letterParases;
		this.uuid = uuid;
		this.context = context;
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Letter(Integer id, Project project, String refUrl, Date updateTime,
			Set letterParases, String uuid) {
		super();
		this.id = id;
		this.project = project;
		this.refUrl = refUrl;
		this.updateTime = updateTime;
		this.letterParases = letterParases;
		this.uuid = uuid;
	}

}