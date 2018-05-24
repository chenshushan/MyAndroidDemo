package com.example.chen.myapplication.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ShopDetailActivity;
import com.example.chen.myapplication.app.bean.GoodsItem;
import com.squareup.picasso.Picasso;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import java.util.List;

public class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

	private ShopDetailActivity context;
	private List<GoodsItem> goodsList;
	private LayoutInflater mInflater;

	public GoodsAdapter(ShopDetailActivity context, List<GoodsItem> goodsList) {
		this.context = context;
		this.goodsList = goodsList;
		this.mInflater = LayoutInflater.from(context);
	}
	// -- StickyListHeadersAdapter
	// 右侧菜品的分类头
	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.shop_type_header_view, parent, false);
		}
		((TextView)convertView).setText(goodsList.get(position).typeName);
		return convertView;
	}
	// 分类的主键
	@Override
	public long getHeaderId(int position) {
		return goodsList.get(position).typeId;
	}

	//  -- BaseAdapter
	// 菜品数量
	@Override
	public int getCount() {
		return goodsList.size();
	}

	@Override
	public Object getItem(int i) {
		return goodsList.get(i);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ItemViewHolder viewHolder = null;
		if(view == null) {
			view = mInflater.inflate(R.layout.shop_shop_view, viewGroup, false);
			viewHolder = new ItemViewHolder(view);
			view.setTag(viewHolder);
		}else {
			viewHolder = (ItemViewHolder)view.getTag();
		}
		GoodsItem goodsItem = goodsList.get(i);
		viewHolder.bindData(goodsItem);
		return view;
	}

	class ItemViewHolder implements View.OnClickListener {
		private TextView name,price,tvAdd,tvMinus,tvCount;
		private GoodsItem item;
		private RatingBar ratingBar;
		private ImageView img;

		public ItemViewHolder(View itemView) {
			name = (TextView) itemView.findViewById(R.id.tvName);
			price = (TextView) itemView.findViewById(R.id.tvPrice);
			tvCount = (TextView) itemView.findViewById(R.id.count);
			tvMinus = (TextView) itemView.findViewById(R.id.tvMinus);
			tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
			ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
			img = (ImageView) itemView.findViewById(R.id.img);
		}

		public void bindData(GoodsItem item){
			this.item = item;
			name.setText(item.name);
			ratingBar.setRating(item.rating);
			item.count = 2;
			tvCount.setText(String.valueOf(item.count));
			price.setText(String.format("%.0f",item.price));
			Picasso.with(context).load(item.img).into(img);
			if(item.count<1){
				tvCount.setVisibility(View.GONE);
				tvMinus.setVisibility(View.GONE);
			}else{
				tvCount.setVisibility(View.VISIBLE);
				tvMinus.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onClick(View view) {
			ShopDetailActivity activity = context;
			switch (view.getId()) {
				case R.id.tvAdd : { // 添加菜品添加圆钮
					int foodCount = activity.getSelectedItemCountById(item.id);
					if(foodCount < 1) { // // 当数量为0时，点击添加后显示减号圆钮和数量
						tvMinus.setVisibility(View.VISIBLE);
						tvCount.setVisibility(View.VISIBLE);
					}

				}
			}


		}
	}
}
