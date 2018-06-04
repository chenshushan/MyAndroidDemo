package com.example.chen.myapplication.app.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class NotifyService extends Service {

	NotifyTask notifyTask;
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return new NotifyBinder();
	}
	public class NotifyBinder extends Binder {
		public void createNotify(){
			if(notifyTask == null) {
				notifyTask = new NotifyTask(NotifyService.this);
			}
			// 这里的参数传递到doInBackground中
			notifyTask.execute();
		}
	}
}
