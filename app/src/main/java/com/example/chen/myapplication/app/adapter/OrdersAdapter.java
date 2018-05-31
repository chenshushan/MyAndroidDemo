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
import com.example.chen.myapplication.app.bean.Order;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.util.DialogUtil;
import com.example.chen.myapplication.app.util.ToastUtil;

import java.util.Collections;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {


	private Context activity;
	private List<Order> oders;
	LayoutInflater inflater;

	public OrdersAdapter(Context activity, List<Order> oders) {
		this.activity = activity;
		Collections.reverse(oders);
		this.oders = oders;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.list_order_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Order order = oders.get(position);
		holder.bindData(order);
	}

	@Override
	public int getItemCount() {
		if(oders==null) {
			return 0;
		}
		return oders.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView tvShopName,tvCost,tvCreate,tvStatus,tvSend,operate;

		public ViewHolder(View itemView) {
			super(itemView);

			tvShopName = (TextView) itemView.findViewById(R.id.txt_business_name);
			tvCost = (TextView) itemView.findViewById(R.id.txt_total_price);
			tvCreate = (TextView) itemView.findViewById(R.id.txt_created_at);
			tvStatus = (TextView) itemView.findViewById(R.id.txt_order_status);
			tvSend = (TextView) itemView.findViewById(R.id.send_appointment);
			operate = (TextView) itemView.findViewById(R.id.btn_payment);
			operate.setOnClickListener(this);
		}



		public void bindData(Order order){
			Shop shop = order.getShop();
			if(shop != null){
				tvShopName.setText(shop.getName());
			}
			String totalPrice = order.getTotalPrice();
			tvCost.setText(totalPrice);
			tvCreate.setText(order.getCreatedTime());
			tvStatus.setText(order.getStatusCN());
			tvSend.setText(order.getSendAppointment());
		}

		@Override
		public void onClick(View view) {
			DialogUtil.showQuestionDialog(activity, "提示", "确认支付吗？", new DialogUtil.OnClickYesListener() {
				@Override
				public void onClickYes() {
					ToastUtil.showToast("支付成功");
				}
			});
		}
	}

}
