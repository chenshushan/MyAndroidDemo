package com.example.chen.myapplication.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.ui.GridViewActivity;
import com.example.chen.myapplication.ui.ListViewActivity;

/**
 * Created by Administrator on 2017/4/23.
 */
public class LayoutActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
	}

	public void btnTurnToLinear(View v){
		Intent intent = new Intent(this, LinearLayoutActivity.class);
		startActivity(intent);
	}
	public void btnTurnToRelative(View v){
		Intent intent = new Intent(this, RelativeLayoutActivity.class);
		startActivity(intent);
	}
	public void btnTurnToGird(View v){
		Intent intent = new Intent(this, GridLayoutActivity.class);
		startActivity(intent);
	}
	public void btnTurnToFrame(View v){
		Intent intent = new Intent(this, FrameLayoutActivity.class);
		startActivity(intent);
	}
	public void btnTurnTolist(View v){
		Intent intent = new Intent(this, ListViewActivity.class);
		startActivity(intent);
	}
	public void btnTurnToGridView(View v){
		Intent intent = new Intent(this, GridViewActivity.class);
		startActivity(intent);
	}
	public void btnTurnToFrag(View v){
		Intent intent = new Intent(this, FragmentActivity.class);
		startActivity(intent);
	}
	public void btnTurnToBMenu(View v){
		Intent intent = new Intent(this, BottomMenuActivity.class);
		startActivity(intent);
	}
}
