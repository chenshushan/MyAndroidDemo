package com.example.chen.myapplication.ui.data;

/**
 * Created by Administrator on 2017/4/23.
 */
public class Icon {
	private int iId;
	private String iName;

	public Icon() {
	}

	public Icon(int iId, String iName) {
		this.iId = iId;
		this.iName = iName;
	}

	public int getiId() {
		return iId;
	}

	public String getiName() {
		return iName;
	}

	public void setiId(int iId) {
		this.iId = iId;
	}

	public void setiName(String iName) {
		this.iName = iName;
	}
}
