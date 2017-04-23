package com.example.chen.myapplication.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.ui.adapter.MyAdapter;
import com.example.chen.myapplication.ui.data.Icon;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/23.
 */
public class GridViewActivity extends AppCompatActivity {
	private Context mContext;
	private GridView grid_photo;
	private BaseAdapter mAdapter = null;
	private ArrayList<Icon> mData = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview_main);
		mContext = GridViewActivity.this;
		grid_photo = (GridView) findViewById(R.id.grid_photo);

		mData = new ArrayList<Icon>();
		mData.add(new Icon(R.mipmap.ic_launcher, "图标1"));
		mData.add(new Icon(R.mipmap.ic_launcher, "图标2"));
		mData.add(new Icon(R.mipmap.ic_launcher, "图标3"));
		mData.add(new Icon(R.mipmap.ic_launcher, "图标4"));
		mData.add(new Icon(R.mipmap.ic_launcher, "图标5"));
		mData.add(new Icon(R.mipmap.ic_launcher, "图标6"));
		mData.add(new Icon(R.mipmap.ic_launcher, "图标7"));

		mAdapter = new MyAdapter<Icon>(mData, R.layout.gridview_item) {
			@Override
			public void bindView(ViewHolder holder, Icon obj) {
				holder.setImageResource(R.id.img_icon, obj.getiId());
				holder.setText(R.id.txt_icon, obj.getiName());
			}
		};

		grid_photo.setAdapter(mAdapter);

		grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(mContext, "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
			}
		});

	}

}
