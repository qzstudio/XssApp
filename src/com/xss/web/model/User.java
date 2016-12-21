package com.xss.web.model;

import java.util.Date;

import com.xss.web.model.base.BaseModel;

/**
 * 用户 User entity. @author MyEclipse Persistence Tools
 */

public class User extends BaseModel {

	// Fields

	private Integer id;
	private String userName;
	private String userPwd;
	private String mobile;
	private String email;
	private Date createTime ;
	private String uuid;

	// Constructors

	/** default constructor */
	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getMobile() {
		return mobile;
	}

	public User(Integer id, String userName, String userPwd, String mobile,
			String email, Date createTime, String uuid) {
		super();
		this.id = id;
		this.userName = userName;
		this.userPwd = userPwd;
		this.mobile = mobile;
		this.email = email;
		this.createTime = createTime;
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User(Integer id, String userName, String userPwd, String mobile,
			String email, Date createTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.userPwd = userPwd;
		this.mobile = mobile;
		this.email = email;
		this.createTime = createTime;
	}

}