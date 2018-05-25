package com.example.chen.myapplication.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ShopDetailActivity;
import com.example.chen.myapplication.app.bean.GoodsItem;

public class GoodsSelectedAdapter extends RecyclerView.Adapter<GoodsSelectedAdapter.ViewHolder> {


	private ShopDetailActivity activity;
	private SparseArray<GoodsItem> selectedGoods;
	LayoutInflater inflater;

	public GoodsSelectedAdapter(ShopDetailActivity activity, SparseArray<GoodsItem> selectedGoods) {
		this.activity = activity;
		this.selectedGoods = selectedGoods;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.item_selected_goods, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		GoodsItem goodsItem = selectedGoods.valueAt(position);
		holder.bindData(goodsItem);
	}

	@Override
	public int getItemCount() {
		if(selectedGoods==null) {
			return 0;
		}
		return selectedGoods.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		private GoodsItem item;
		private TextView tvCost,tvCount,tvAdd,tvMinus,tvName;

		public ViewHolder(View itemView) {
			super(itemView);

			tvName = (TextView) itemView.findViewById(R.id.tvName);
			tvCost = (TextView) itemView.findViewById(R.id.tvCost);
			tvCount = (TextView) itemView.findViewById(R.id.count);
			tvMinus = (TextView) itemView.findViewById(R.id.tvMinus);
			tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);

			tvAdd.setOnClickListener(this);
			tvMinus.setOnClickListener(this);

		}

		@Override
		public void onClick(View view) {
			switch (view.getId()){
				case R.id.tvAdd:
					activity.addFood(item, true);
					break;
				case R.id.tvMinus:
					activity.removeFood(item, true);
					break;
				default:
					break;
			}
		}

		public void bindData(GoodsItem item){
			this.item = item;
			tvName.setText(item.name);
			tvCost.setText(String.format("%.2f",item.count*item.price));
			tvCount.setText(String.valueOf(item.count));
		}
	}

}
