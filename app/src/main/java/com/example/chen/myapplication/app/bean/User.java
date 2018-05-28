package com.example.chen.myapplication.app.bean;

public class User {
	public static final String USER_INFO = "userInfo";

	private int userId;
	private String username;
	private String password;
	private String sex;
	private String phone;

	public User() {
	}

	public User(String username, String password, String sex, String phone) {
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.phone = phone;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
