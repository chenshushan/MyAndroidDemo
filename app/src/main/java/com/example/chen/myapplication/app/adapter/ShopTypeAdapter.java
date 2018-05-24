package com.example.chen.myapplication.app.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ShopDetailActivity;
import com.example.chen.myapplication.app.bean.GoodsItem;

import java.util.List;

public class ShopTypeAdapter extends RecyclerView.Adapter {

	private ShopDetailActivity context;
	private List<GoodsItem> goodsList;

	public ShopTypeAdapter(ShopDetailActivity context, List<GoodsItem> goodsList) {
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
		shopTypeHolder.bindData(goodsItem);
	}

	@Override
	public int getItemCount() {
		return goodsList.size();
	}
	public int selectTypeId; // 当前选中的类别

	class ShopTypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView tvCount,type;
		GoodsItem item;
		public ShopTypeHolder(View itemView) {
			super(itemView);
			tvCount = (TextView) itemView.findViewById(R.id.tvCount);
			type = (TextView) itemView.findViewById(R.id.type);
			itemView.setOnClickListener(this);
		}

		public void bindData(GoodsItem item){
			this.item = item;
			type.setText(item.typeName);
		    if(item.typeId == selectTypeId) { // 如果当前选中的类别与当前创建的类别相同 则设置背景色
		    	itemView.setBackgroundColor(Color.WHITE);
			}   else {// 取消背景色
				itemView.setBackgroundColor(Color.TRANSPARENT);
			}
		}
		
		@Override
		public void onClick(View view) {
			context.onTypeClicked(item.typeId);// 点击了哪个分类 在ShopDetailActivity中设置右侧菜品随着变化位置
		}
	}
}
