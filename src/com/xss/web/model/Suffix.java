package com.xss.web.model;

import com.xss.web.model.base.BaseModel;

/**
 * 后缀 Suffix entity. @author MyEclipse Persistence Tools
 */

public class Suffix extends BaseModel {

	// Fields

	private Integer id;
	private String suffix;
	private Integer status;

	// Constructors

	/** default constructor */
	public Suffix() {
	}

	/** full constructor */
	public Suffix(String suffix, Integer status) {
		this.suffix = suffix;
		this.status = status;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}