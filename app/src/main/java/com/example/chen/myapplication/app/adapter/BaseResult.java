package com.example.chen.myapplication.app.adapter;

public class BaseResult<T> {

	private String retcode;
	private String hasNext;
	private String pageToken;
	private T data;

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getHasNext() {
		return hasNext;
	}

	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}

	public String getPageToken() {
		return pageToken;
	}

	public void setPageToken(String pageToken) {
		this.pageToken = pageToken;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BaseResult{" +
				"retcode='" + retcode + '\'' +
				", hasNext='" + hasNext + '\'' +
				", pageToken='" + pageToken + '\'' +
				'}';
	}
}
