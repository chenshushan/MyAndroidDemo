package com.example.chen.myapplication.app.bean;


import java.io.Serializable;

//用户地址详情的实体类
public class Address implements Serializable {
	private int id;
	private String name;//姓名
	private String sex;//性别
	private String phoneNumber;//电话
	private String address;//大体地址
	private String addressDetail;//详细地址
	private String userId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Address(String name, String sex, String phoneNumber, String address, String addressDetail) {
		this.name = name;
		this.sex = sex;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.addressDetail = addressDetail;
	}


}
