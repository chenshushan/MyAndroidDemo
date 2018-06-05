package com.example.chen.myapplication.app;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.MainFragmentAdapter;
import com.example.chen.myapplication.app.fragment.HomeFragment;
import com.example.chen.myapplication.app.fragment.MineFragment;
import com.example.chen.myapplication.app.fragment.OrderFragment;
import com.example.chen.myapplication.app.service.NotifyService;

import java.util.ArrayList;
import java.util.List;
// 主页
public class AppActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

	ViewPager viewPager;
	RadioButton homeBtn;
	RadioButton orderbtn;
	RadioButton mineBtn;

	RadioGroup radioGroup;

	public static final String HOME_PAGE = "set_home_page";

	private List<Fragment> fragmentList;//  Fragment数组

	NotifyService.NotifyBinder notifyBinder;

	private ServiceConnection conn = new ServiceConnection() {

		//Activity与Service断开连接时回调该方法
		@Override
		public void onServiceDisconnected(ComponentName name) {
			System.out.println("------Service DisConnected-------");
		}

		//Activity与Service连接成功时回调该方法
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			System.out.println("------Service Connected-------");
			notifyBinder = (NotifyService.NotifyBinder) binder;

			notifyBinder.createNotify();

		}
	};

	@Override
	protected void onStop() {
		super.onStop();
		notifyBinder.stopNotify();
	}

	public void initView(){
		viewPager = (ViewPager) findViewById(R.id.mainViewPage);
		homeBtn = (RadioButton) findViewById(R.id.rb_firstpage);
		orderbtn = (RadioButton) findViewById(R.id.rb_order);
		mineBtn = (RadioButton) findViewById(R.id.rb_mine);

		radioGroup = (RadioGroup) findViewById(R.id.mainRadioGroup);

		Intent intent = new Intent(this, NotifyService.class);
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app);
		initView();
		fragmentList = new ArrayList();
		HomeFragment homeFragment = new HomeFragment();
		OrderFragment orderFragment = new OrderFragment();
		MineFragment mineFragment = new MineFragment();

		fragmentList.add(homeFragment);
		fragmentList.add(orderFragment);
		fragmentList.add(mineFragment);
		MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList);

		viewPager.setAdapter(mainFragmentAdapter);

		radioGroup.setOnCheckedChangeListener(this);

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

			@Override
			public void onPageSelected(int position) {}
			//重写ViewPager页面切换的处理方法
			@Override
			public void onPageScrollStateChanged(int state) {
				//state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
				if(state != 2) {
					return;
				}
				switch (viewPager.getCurrentItem()) {
					case 0:
						radioGroup.check(R.id.rb_firstpage);
						break;
					case 1:
						radioGroup.check(R.id.rb_order);
						break;
					case 2:
						radioGroup.check(R.id.rb_mine);
						break;
				}

			}
		});

		homeBtn.setTextColor(getResources().getColor(R.color.color_table_text_press));
		// 判断是否是从登录页面返回的 需要设置特定的页面
		Intent intent = getIntent();
		int index = intent.getIntExtra(HOME_PAGE, -1);
		if(index ==2) {
			radioGroup.check(R.id.rb_mine);
		} else if(index == 1){
			radioGroup.check(R.id.rb_order);
		}
	}


	/** 监听底部button事件
	 * @param radioGroup
	 * @param checkedId
	 */
	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		int current = 0;
		switch (checkedId) {
			case R.id.rb_firstpage:
				current = 0;
				homeBtn.setTextColor(getResources().getColor(R.color.color_table_text_press));
				orderbtn.setTextColor(getResources().getColor(R.color.color_table_text));
				mineBtn.setTextColor(getResources().getColor(R.color.color_table_text));

				break;
			case R.id.rb_order:
				current = 1;
				homeBtn.setTextColor(getResources().getColor(R.color.color_table_text));
				orderbtn.setTextColor(getResources().getColor(R.color.color_table_text_press));
				mineBtn.setTextColor(getResources().getColor(R.color.color_table_text));
				break;
			case R.id.rb_mine:
				current = 2;
				homeBtn.setTextColor(getResources().getColor(R.color.color_table_text));
				orderbtn.setTextColor(getResources().getColor(R.color.color_table_text));
				mineBtn.setTextColor(getResources().getColor(R.color.color_table_text_press));
				break;
		}
		if(viewPager.getCurrentItem() != current){
			viewPager.setCurrentItem(current);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
		System.out.println("AppActivity onDestroy");
	}
}
