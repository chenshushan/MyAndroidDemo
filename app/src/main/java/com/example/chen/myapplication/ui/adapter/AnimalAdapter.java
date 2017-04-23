package com.example.chen.myapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.ui.data.Animal;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/4/23.
 */
public class AnimalAdapter extends BaseAdapter {
	private LinkedList<Animal> mData;
	private Context mContext;

	public AnimalAdapter(LinkedList<Animal> mData, Context mContext) {
		this.mData = mData;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
			holder = new ViewHolder();
			holder.img_icon = (ImageView) convertView.findViewById(R.id.imgtou);
			holder.txt_aName = (TextView) convertView.findViewById(R.id.txt_aName);
			holder.txt_aSpeak = (TextView) convertView.findViewById(R.id.txt_aSpeak);
			convertView.setTag(holder);   //将Holder存储到convertView中
		}else {
			holder = (ViewHolder) convertView.getTag();
		}

		Animal animal = mData.get(position);
		holder.img_icon.setBackgroundResource(animal.getaIcon());
		holder.txt_aName.setText(animal.getaName());
		holder.txt_aSpeak.setText(animal.getaSpeak());
		return convertView;
	}

	class ViewHolder{
		ImageView img_icon;
		TextView txt_aName;
		TextView txt_aSpeak;
	}
}
