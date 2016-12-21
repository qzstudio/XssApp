package com.xss.web.test;

import com.xss.web.model.base.BaseModel;

public class Address extends BaseModel {

	private Integer code;
	private String msg;
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	private AddressInfo data;
	
	public AddressInfo getData() {
		return data;
	}

	public void setData(AddressInfo data) {
		this.data = data;
	}

	private static class AddressInfo{
		
		private Integer aid;
		private Integer uid;
		private String consignee;
		private String phone;
		private String province;
		private String city;
		private String area;
		private String address;
		private Integer use;
		public Integer getAid() {
			return aid;
		}
		public void setAid(Integer aid) {
			this.aid = aid;
		}
		public Integer getUid() {
			return uid;
		}
		public void setUid(Integer uid) {
			this.uid = uid;
		}
		public String getConsignee() {
			return consignee;
		}
		public void setConsignee(String consignee) {
			this.consignee = consignee;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public Integer getUse() {
			return use;
		}
		public void setUse(Integer use) {
			this.use = use;
		}
		
	} 
}
