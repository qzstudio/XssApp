package com.xss.web.model;

import java.util.HashSet;
import java.util.Set;

import com.xss.web.model.base.BaseModel;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */

public class Role extends BaseModel {

	// Fields

	private Integer id;
	private String name;
	private String menus;
	private Set admins = new HashSet(0);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

	public Set getAdmins() {
		return admins;
	}

	public void setAdmins(Set admins) {
		this.admins = admins;
	}

	public Role(Integer id, String name, String menus) {
		super();
		this.id = id;
		this.name = name;
		this.menus = menus;
	}

	public Role(Integer id, String name, String menus, Set admins) {
		super();
		this.id = id;
		this.name = name;
		this.menus = menus;
		this.admins = admins;
	}

	public Role() {
		super();
	}

}