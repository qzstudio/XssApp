package com.xss.web.model;

import com.xss.web.model.base.BaseModel;

/**
 * 设置 Setting entity. @author MyEclipse Persistence Tools
 */

public class Setting extends BaseModel {

	// Fields

	private Integer id;
	private String siteName;
	private String keywords;
	private String description;
	private String copyright;
	private Integer openReg;
	private Integer openInvite;

	// Constructors

	/** default constructor */
	public Setting() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public Integer getOpenReg() {
		return openReg;
	}

	public void setOpenReg(Integer openReg) {
		this.openReg = openReg;
	}

	public Integer getOpenInvite() {
		return openInvite;
	}

	public void setOpenInvite(Integer openInvite) {
		this.openInvite = openInvite;
	}

	public Setting(Integer id, String siteName, String keywords,
			String description, String copyright, Integer openReg,
			Integer openInvite) {
		super();
		this.id = id;
		this.siteName = siteName;
		this.keywords = keywords;
		this.description = description;
		this.copyright = copyright;
		this.openReg = openReg;
		this.openInvite = openInvite;
	}

}