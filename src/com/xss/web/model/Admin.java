package com.xss.web.model;

import com.xss.web.model.base.BaseModel;

/**
 * Admin entity. @author MyEclipse Persistence Tools
 */

public class Admin extends BaseModel {

	// Fields

	private Integer id;
	private Role role;
	private String userName;
	private String userPwd;

	// Constructors

	/** default constructor */
	public Admin() {
	}

	/** full constructor */
	public Admin(Role role, String userName, String userPwd) {
		this.role = role;
		this.userName = userName;
		this.userPwd = userPwd;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

}