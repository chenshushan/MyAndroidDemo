package com.example.chen.myapplication.app;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.MainFragmentAdapter;
import com.example.chen.myapplication.app.fragment.HomeFragment;
import com.example.chen.myapplication.app.fragment.MineFragment;
import com.example.chen.myapplication.app.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

	ViewPager viewPager;
	RadioButton homeBtn;
	RadioButton orderbtn;
	RadioButton mineBtn;

	RadioGroup radioGroup;

	private List<Fragment> fragmentList;//  Fragment数组

	public void initView(){
		viewPager = (ViewPager) findViewById(R.id.mainViewPage);
		homeBtn = (RadioButton) findViewById(R.id.rb_firstpage);
		orderbtn = (RadioButton) findViewById(R.id.rb_order);
		mineBtn = (RadioButton) findViewById(R.id.rb_mine);

		radioGroup = (RadioGroup) findViewById(R.id.mainRadioGroup);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {

			}
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
				break;
			case R.id.rb_order:
				current = 1;
				break;
			case R.id.rb_mine:
				current = 2;
				break;
		}
		if(viewPager.getCurrentItem() != current){
			viewPager.setCurrentItem(current);
		}

	}
}
