package com.xss.web.model;

import com.xss.web.model.base.BaseModel;

/**
 * Email entity. @author MyEclipse Persistence Tools
 */

public class Email extends BaseModel {

	// Fields

	private Integer id;
	private String smtp;
	private String email;
	private String password;
	private Integer activite=1;
	// Constructors

	/** default constructor */
	public Email() {
	}

	public Email(Integer id, String smtp, String email, String password,
			Integer activite) {
		super();
		this.id = id;
		this.smtp = smtp;
		this.email = email;
		this.password = password;
		this.activite = activite;
	}

	public Integer getActivite() {
		return activite;
	}

	public void setActivite(Integer activite) {
		this.activite = activite;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Email(Integer id, String smtp, String email, String password) {
		super();
		this.id = id;
		this.smtp = smtp;
		this.email = email;
		this.password = password;
	}

}