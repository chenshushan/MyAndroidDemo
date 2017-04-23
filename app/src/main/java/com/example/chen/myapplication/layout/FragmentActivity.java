package com.example.chen.myapplication.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.chen.myapplication.R;

/**
 * Created by Administrator on 2017/4/23.
 */
public class FragmentActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentone_ower);
	}
}
