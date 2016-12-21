package com.xss.web.model;

import java.util.Date;

import com.xss.web.model.base.BaseModel;

/**
 * 邀请码 Invite entity. @author MyEclipse Persistence Tools
 */

public class Invite extends BaseModel {

	// Fields

	private Integer id;
	private User user;
	private String inviteCode;
	private Integer status;
	private Date updateTime;

	// Constructors

	/** default constructor */
	public Invite() {
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

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Invite(Integer id, User user, String inviteCode, Integer status,
			Date updateTime) {
		super();
		this.id = id;
		this.user = user;
		this.inviteCode = inviteCode;
		this.status = status;
		this.updateTime = updateTime;
	}

}