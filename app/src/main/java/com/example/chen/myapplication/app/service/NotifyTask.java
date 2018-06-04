package com.example.chen.myapplication.app.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.BaiduMapActivity;
import com.example.chen.myapplication.app.util.ToastUtil;

import java.util.Date;
import java.util.Random;

public class NotifyTask extends AsyncTask<String, Integer, String> {
	Service service;

	public NotifyTask(Service service) {
		this.service = service;
	}

	@Override
	protected void onPreExecute() {
		ToastUtil.showToast("NotifyTask start !!");
	}

	@Override
	protected void onPostExecute(String s) {
		Notification.Builder localBuilder = new Notification.Builder(service);
		localBuilder.setContentIntent(PendingIntent.getActivity(service, 0, new Intent(service, BaiduMapActivity.class), 0));
		localBuilder.setAutoCancel(false);
		localBuilder.setSmallIcon(R.mipmap.ic_launcher);
		localBuilder.setTicker("Foreground Service Start");
		localBuilder.setContentTitle("Socket服务端");

		localBuilder.setContentText(s + " " );
		service.startForeground(1, localBuilder.build());
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	// 返回的String在onPostExecute的参数中
	@Override
	protected String doInBackground(String... params) {
		Random random = new Random();
		int rand = random.nextInt(30) + 15;
		int i = 0;
		while (true) {
			try {
				Thread.sleep(rand * 1000);
				i++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i == 3) {
				break;
			}else {
				ToastUtil.showToast("NotifyTask任务已休眠次数：" + i);
			}
		}
		return "NotifyTask finish";
	}
}
