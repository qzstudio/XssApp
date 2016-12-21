package com.xss.web.entity;

import com.xss.web.model.base.BaseModel;


public class IpAddressEntity extends BaseModel{
	private Integer code;
	private AddressInfo data;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public AddressInfo getData() {
		return data;
	}
	public void setData(AddressInfo data) {
		this.data = data;
	}
	
	
	public static class AddressInfo extends BaseModel {

		 private Long id;

		    private String ip;

		    private String country;

		    private String area;

		    private String region;

		    private String city;

		    private String isp;

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public String getIp() {
				return ip;
			}

			public void setIp(String ip) {
				this.ip = ip;
			}

			public String getCountry() {
				return country;
			}

			public void setCountry(String country) {
				this.country = country;
			}

			public String getArea() {
				return area;
			}

			public void setArea(String area) {
				this.area = area;
			}

			public String getRegion() {
				return region;
			}

			public void setRegion(String region) {
				this.region = region;
			}

			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public String getIsp() {
				return isp;
			}

			public void setIsp(String isp) {
				this.isp = isp;
			}

			public AddressInfo(Long id, String ip, String country, String area,
					String region, String city, String isp) {
				super();
				this.id = id;
				this.ip = ip;
				this.country = country;
				this.area = area;
				this.region = region;
				this.city = city;
				this.isp = isp;
			}

		
	}
}



