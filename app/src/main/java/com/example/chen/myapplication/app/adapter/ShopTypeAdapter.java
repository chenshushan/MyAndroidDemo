package com.example.chen.myapplication.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.bean.GoodsItem;

import java.util.List;

public class ShopTypeAdapter extends RecyclerView.Adapter {

	private Context context;
	private List<GoodsItem> goodsList;

	public ShopTypeAdapter(Context context, List<GoodsItem> goodsList) {
		this.context = context;
		this.goodsList = goodsList;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_type_item, parent, false);
		ShopTypeHolder shopTypeHolder = new ShopTypeHolder(view);
		return shopTypeHolder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		GoodsItem goodsItem = goodsList.get(position);
		ShopTypeHolder shopTypeHolder = (ShopTypeHolder) holder;
		shopTypeHolder.type.setText(goodsItem.typeName);
	}

	@Override
	public int getItemCount() {
		return goodsList.size();
	}

	class ShopTypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView tvCount,type;
		private GoodsItem item;
		public ShopTypeHolder(View itemView) {
			super(itemView);
			tvCount = (TextView) itemView.findViewById(R.id.tvCount);
			type = (TextView) itemView.findViewById(R.id.type);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {

		}
	}
}
