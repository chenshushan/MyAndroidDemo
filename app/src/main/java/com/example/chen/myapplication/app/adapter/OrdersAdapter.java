package com.example.chen.myapplication.app.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.bean.Order;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.util.DialogUtil;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import static com.example.chen.myapplication.app.bean.Order.ORDER_LIST;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {


	private Context activity;
	private List<Order> orders;
	LayoutInflater inflater;

	boolean isRereshTime = true;

	public void setOrders(List<Order> orders) {
		Collections.reverse(orders);
		this.orders = orders;
	}

	public OrdersAdapter(Context activity, List<Order> orders) {
		this.activity = activity;
		Collections.reverse(orders);
		this.orders = orders;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.list_order_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Order order = orders.get(position);
		holder.bindData(order);
	}

	@Override
	public int getItemCount() {
		if(orders==null) {
			return 0;
		}
		return orders.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView tvShopName,tvCost,tvCreate,tvStatus,tvSend,operate,down;
		private ImageView img;

		private Order order;
		public ViewHolder(View itemView) {
			super(itemView);

			tvShopName = (TextView) itemView.findViewById(R.id.txt_business_name);
			tvCost = (TextView) itemView.findViewById(R.id.txt_total_price);
			tvCreate = (TextView) itemView.findViewById(R.id.txt_created_at);
			tvStatus = (TextView) itemView.findViewById(R.id.txt_order_status);
			tvSend = (TextView) itemView.findViewById(R.id.send_appointment);
			operate = (TextView) itemView.findViewById(R.id.btn_payment);
			down = (TextView) itemView.findViewById(R.id.tv_count_down);
			img = (ImageView) itemView.findViewById(R.id.shop_image);
			operate.setOnClickListener(this);
		}



		public void bindData(Order order){
			this.order = order;
			Shop shop = order.getShop();
			if(shop != null){
				tvShopName.setText(shop.getName());
			}
			String totalPrice = order.getTotalPrice();
			tvCost.setText("￥" + totalPrice);
			tvCreate.setText(order.getCreatedTime());
			tvStatus.setText(order.getStatusCN());
			operate.setText(order.getStatusOperate());

			tvSend.setText(order.getSendAppointment());
			Picasso.with(activity).load(shop.getPicUrl()).error(R.mipmap.log).placeholder(R.mipmap.log).into(this.img);
			if(order.getStatus() == 1) {
				down.setVisibility(View.VISIBLE);
				if(isRereshTime) {
					countDownTimer.start();
				}
			}
		}

		@Override
		public void onClick(View view) {
			final int status = order.getStatus();
			if(status == 0) {
				DialogUtil.showQuestionDialog(activity, "提示", "确认送达吗？", new DialogUtil.OnClickYesListener() {
					@Override
					public void onClickYes() {
						updateOrderStatus(status);
						ToastUtil.showToast("订单配送完成");
					}
				});
			}else if(status == 1){
				DialogUtil.showQuestionDialog(activity, "提示", "确认支付吗？", new DialogUtil.OnClickYesListener() {
					@Override
					public void onClickYes() {
						updateOrderStatus(status);
						down.setVisibility(View.GONE);
						ToastUtil.showToast("支付成功,订单配送中");
					}
				});
			}else {

			}


		}


		public void updateOrderStatus(int status){
			if(status == 0) {// 确认送达
				status = 2; // 已完成
			}else if(status == 1){// 立即支付
				status = 0;
			}else {

			}
			order.setStatus(status);
			// 遍历所有order 重置该order状态
			Type type = new TypeToken<List<Order>>() {}.getType();
			List<Order> result  = PreferenceUtil.getObject(ORDER_LIST, type);
			for (int i = 0; i < result.size(); i++) {
				Order od = result.get(i);
				int id = od.getId();
				if(id == ViewHolder.this.order.getId()) {
					result.set(i, order);
					PreferenceUtil.set(ORDER_LIST, orders);
					// 更新订单列表
					OrdersAdapter.this.setOrders(result);
					OrdersAdapter.this.notifyDataSetChanged();
				}
			}
		}

		/**
		 * CountDownTimer 实现倒计时
		 */
		private CountDownTimer countDownTimer = new CountDownTimer(15*60*1000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				isRereshTime = false;

				int seconds = (int) (millisUntilFinished / 1000);
				int minutes = seconds / 60;
				int remainingSeconds = seconds % 60;
				if(remainingSeconds < 10) {
					down.setText(minutes + ":0" + remainingSeconds);
				}else {
					down.setText(minutes + ":" + remainingSeconds);
				}

			}

			@Override
			public void onFinish() {
				updateOrderStatus(3);
				down.setVisibility(View.GONE);
			}
		};
	}
}
