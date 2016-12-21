package com.xss.web.model;

import java.util.Date;

import com.xss.web.model.base.BaseModel;

/**
 * 信封参数 LetterParas entity. @author MyEclipse Persistence Tools
 */

public class LetterParas extends BaseModel {

	// Fields

	private Integer id;
	private Letter letter;
	private String paraName;
	private String paraValue;
	private Date updateTime;

	// Constructors

	/** default constructor */
	public LetterParas() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Letter getLetter() {
		return letter;
	}

	public void setLetter(Letter letter) {
		this.letter = letter;
	}

	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public LetterParas(Integer id, Letter letter, String paraName,
			String paraValue, Date updateTime) {
		super();
		this.id = id;
		this.letter = letter;
		this.paraName = paraName;
		this.paraValue = paraValue;
		this.updateTime = updateTime;
	}

}