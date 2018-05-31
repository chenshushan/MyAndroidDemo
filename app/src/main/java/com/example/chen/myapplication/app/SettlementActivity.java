package com.example.chen.myapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.OrderItemAdapter;
import com.example.chen.myapplication.app.bean.*;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.example.chen.myapplication.app.view.TitleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.chen.myapplication.app.adapter.AddressAdapter.SELECT_ADDRESS_OK;
import static com.example.chen.myapplication.app.bean.Order.ORDER_LIST;
import static com.example.chen.myapplication.app.bean.User.USER_INFO;

// 订单结算页
public class SettlementActivity extends BaseActivity implements View.OnClickListener {

	SparseArray  goodsList;

	RelativeLayout rlAddressSelectLayout; // 请选择收货地址Layout
	LinearLayout llAddressMsgLayout; // 收货人信息Layout
	TextView customerName; // 顾客姓名
	TextView customerPhone; // 顾客电话
	TextView customerAddress; // 送货地址
	TextView sendTime; // 送货时间
	EditText remark; // 备注
	TextView subOrder;// 提交订单

	TextView sendMoney; // 配送费
	TextView orderTotal; // 配送费
	TextView sub; // 配送费
	RecyclerView foods; // 菜品列表
	OrderItemAdapter orderItemAdapter; // 菜品列表的adapter
	Shop shop;// 店铺信息

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_settlement);

		Intent intent = getIntent();
		String json = intent.getStringExtra("data");
		String shopJson = intent.getStringExtra("shopJson");
		Gson gson = new Gson();
		Type type = new TypeToken<SparseArray>() {}.getType();
		goodsList = gson.fromJson(json, type);
		shop = gson.fromJson(shopJson, Shop.class);
		initView();
	}

	public void initView(){


		rlAddressSelectLayout = (RelativeLayout)findViewById(R.id.rl_address_select);
		rlAddressSelectLayout.setOnClickListener(this);

		llAddressMsgLayout = (LinearLayout)findViewById(R.id.ll_address_msg);
		customerName = (TextView)findViewById(R.id.tv_customer_name);
		customerPhone = (TextView)findViewById(R.id.tv_customer_phone);
		customerAddress = (TextView)findViewById(R.id.tv_customer_address);
		sendTime = (TextView)findViewById(R.id.tv_customer_sendtime);
		remark = (EditText)findViewById(R.id.et_order_remark);
		sendMoney = (TextView)findViewById(R.id.tv_order_send);
		orderTotal = (TextView)findViewById(R.id.order_total);
		sub = (TextView)findViewById(R.id.order_sub_price);

		subOrder = (TextView)findViewById(R.id.subOrder);
		subOrder.setOnClickListener(this);

		TitleView titleView = (TitleView) findViewById(R.id.title);
		titleView.setTitleText("订单提交");

		foods = (RecyclerView)findViewById(R.id.rv_goods);

		double peisong = shop.getPeisong();
		sendMoney.setText(String.format("%.0f", peisong));
		totalMoney = getTotalMoney();
		orderTotal.setText(totalMoney);
		double jian = shop.getJian();
		double full = shop.getFull();

		if(Double.valueOf(totalMoney) >= full){
			String format = String.format("%.2f", Double.valueOf(totalMoney) - jian + peisong);
			sub.setText("已优惠：￥" + jian);
			orderTotal.setText("￥" + format);

			totalMoney = format;
		}else {
			sub.setVisibility(View.GONE);
		}


		foods.setLayoutManager(new LinearLayoutManager(this));
		orderItemAdapter = new OrderItemAdapter(this, goodsList);
		foods.setAdapter(orderItemAdapter);
	}
	String totalMoney;
	private List<GoodsItem> goodsItemList = new ArrayList();
	// 得到已选菜品的总价
	public String getTotalMoney() {

		int size = goodsList.size();
		Double sum = 0.0;
		for (int i = 0; i < size; i++) {
			Map properties = (Map) goodsList.valueAt(i);

			Gson gson = new Gson();
			String json = gson.toJson(properties);
			GoodsItem goodsItem = gson.fromJson(json, GoodsItem.class);
			goodsItemList.add(goodsItem);

			double count = (double) properties.get("count");
			double price = (double) properties.get("price");
			double itemTotal = count * price;
			sum += itemTotal;
		}
		return String.format("%.2f", sum);
	}

	public static  int orderId = 1;

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if(id == R.id.rl_address_select) {
			// 选择地址
			Intent intent = new Intent(this, MyAddressActivity.class);
			startActivityForResult(intent, SELECT_ADDRESS_OK);
		}else if(id == R.id.subOrder) {
			// 提交订单

			Order order = new Order();
			order.setId(orderId++);
			order.setShop(shop);
			User user = PreferenceUtil.getObject(USER_INFO, User.class);
			order.setUser(user);
			if(address == null) {
				ToastUtil.showToast("请选择地址");
				return;
			}
			order.setAddress(address);
			order.setStatus(1);
			order.setTotalPrice(totalMoney);
			order.setRemark(remark.getText().toString());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			order.setCreatedTime(format.format(new Date()));
			order.setSendAppointment(sendTime.getText().toString());
			order.setFoods(goodsItemList);
			Type type = new TypeToken<List<Order>>() {}.getType();
			List<Order> orders =PreferenceUtil.getObject(ORDER_LIST, type);
			if(orders == null) {
				orders =  new ArrayList();
			}
			orders.add(order);
			PreferenceUtil.set(ORDER_LIST, orders);

			// 保存成功，转向订单列表页
			Intent intent = new Intent(this, AppActivity.class);
			startActivity(intent);
		}
	}
	Address address;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch (requestCode) {
			case SELECT_ADDRESS_OK:
				if(requestCode == SELECT_ADDRESS_OK) {
					address = (Address) intent.getSerializableExtra("address");
					if(address != null){
						customerName.setText(address.getName());
						customerPhone.setText(address.getPhoneNumber());
						customerAddress.setText(address.getAddress()+address.getAddressDetail());
						// 隐藏选择地址  显示地址信息
						rlAddressSelectLayout.setVisibility(View.GONE);
						llAddressMsgLayout.setVisibility(View.VISIBLE);
					}
				}
				break;
		}
	}
}
