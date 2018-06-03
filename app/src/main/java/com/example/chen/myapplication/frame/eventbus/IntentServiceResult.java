package com.example.chen.myapplication.frame.eventbus;
// 定义事件
public class IntentServiceResult {
	int mResult;
	String mResultValue;
	public IntentServiceResult(int resultCode, String resultValue) {
		mResult = resultCode;
		mResultValue = resultValue;
	}
	public int getResult() {   return mResult;  }
	public String getResultValue() {  return mResultValue; }
}
