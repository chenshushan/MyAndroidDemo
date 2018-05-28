package com.example.chen.myapplication.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ShopDetailActivity;
import com.example.chen.myapplication.app.bean.GoodsItem;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {


	private Context activity;
	private SparseArray  selectedGoods;
	LayoutInflater inflater;

	public OrderItemAdapter(Context activity, SparseArray<GoodsItem> selectedGoods) {
		this.activity = activity;
		this.selectedGoods = selectedGoods;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.order_foods_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Map properties = (Map) selectedGoods.valueAt(position);
		Gson gson = new Gson();
		String json = gson.toJson(properties);
		GoodsItem goodsItem = gson.fromJson(json, GoodsItem.class);
		holder.bindData(goodsItem);
	}

	@Override
	public int getItemCount() {
		if(selectedGoods==null) {
			return 0;
		}
		return selectedGoods.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		private GoodsItem item;
		private TextView foodName,foodCount,foodCost;

		public ViewHolder(View itemView) {
			super(itemView);

			foodName = (TextView) itemView.findViewById(R.id.food_name);
			foodCount = (TextView) itemView.findViewById(R.id.food_count);
			foodCost = (TextView) itemView.findViewById(R.id.food_cost);
		}


		public void bindData(GoodsItem item){
			this.item = item;
			foodName.setText(item.name);
			foodCost.setText(String.format("%.2f",item.count*item.price));
			foodCount.setText(String.valueOf(item.count));
		}
	}

}
