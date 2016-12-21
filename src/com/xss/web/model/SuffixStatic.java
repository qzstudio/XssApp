package com.xss.web.model;

import com.xss.web.model.base.BaseModel;

/**
 * 静态后缀 SuffixStatic entity. @author MyEclipse Persistence Tools
 */

public class SuffixStatic extends BaseModel {

	// Fields

	private Integer id;
	private String suffix;

	// Constructors

	/** default constructor */
	public SuffixStatic() {
	}

	/** full constructor */
	public SuffixStatic(String suffix) {
		this.suffix = suffix;
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

}