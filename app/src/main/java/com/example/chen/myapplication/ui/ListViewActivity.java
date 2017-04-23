package com.example.chen.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.ui.adapter.AnimalAdapter;
import com.example.chen.myapplication.ui.data.Animal;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/23.
 */
public class ListViewActivity extends Activity implements AdapterView.OnItemClickListener{
	private List<Animal> mData = null;
	private Context mContext;
	private AnimalAdapter mAdapter = null;
	private ListView list_animal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_demo);



		mContext = ListViewActivity.this;
		list_animal = (ListView) findViewById(R.id.listView);
		mData = new LinkedList<Animal>();
		mData.add(new Animal("狗说", "你是狗么?", R.drawable.test));
		mData.add(new Animal("牛说", "你是牛么?", R.drawable.test));
		mData.add(new Animal("鸭说", "你是鸭么?", R.drawable.test));
		mData.add(new Animal("鱼说", "你是鱼么?", R.drawable.test));
		mData.add(new Animal("马说", "你是马么?", R.drawable.test));
		mAdapter = new AnimalAdapter((LinkedList<Animal>) mData, mContext);
		//动态加载顶部View和底部View
		final LayoutInflater inflater = LayoutInflater.from(this);
		View headView = inflater.inflate(R.layout.view_header, null, false);
		View footView = inflater.inflate(R.layout.view_foot, null, false);

		//添加表头和表尾需要写在setAdapter方法调用之前！！！
		list_animal.addHeaderView(headView);
		list_animal.addFooterView(footView);
		list_animal.setAdapter(mAdapter);
		list_animal.setStackFromBottom(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(mContext,"你点击了第" + position + "项",Toast.LENGTH_SHORT).show();
	}
}
