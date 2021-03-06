package com.example.chen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.chen.myapplication.app.AppActivity;
import com.example.chen.myapplication.app.BaseActivity;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.example.chen.myapplication.frame.eventbus.EventBusActivity;
import com.example.chen.myapplication.frame.eventbus.event.BaseEvent;
import com.example.chen.myapplication.frame.rxjava.RxJavaActivity;
import com.example.chen.myapplication.layout.LayoutActivity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


	@BindView(R.id.button11)
	Button app;
	@BindView(R.id.button12)
	Button demo;
	@BindView(R.id.button13)
	Button rxJava;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		eventBus.register(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		eventBus.unregister(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void btnTurnToLayout(View v) {
		Intent intent = new Intent(this, LayoutActivity.class);
		startActivity(intent);
	}


	@OnClick(R.id.button11)
	public void onAppClicked() {
		Intent intent = new Intent(this, AppActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.button12)
	public void onDemoClicked() {
		Intent intent = new Intent(this, EventBusActivity.class);
		startActivity(intent);

		List<String> list = new ArrayList();
		list.add("BaseEvent");
		BaseEvent baseEvent = BaseEvent.build(12).setData(list);
		eventBus.postSticky(baseEvent);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void getText(BaseEvent<List<String>> baseEvent)   {
		List<String> data = baseEvent.getData();
		String s = data.get(0);
		ToastUtil.showToast(s);
	}

	@OnClick(R.id.button13)
	public void onViewClicked() {
		Intent intent = new Intent(this, RxJavaActivity.class);
		startActivity(intent);
	}
}
