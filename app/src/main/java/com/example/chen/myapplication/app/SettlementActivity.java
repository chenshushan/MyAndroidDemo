package com.example.chen.myapplication.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.OrderItemAdapter;
import com.example.chen.myapplication.app.bean.GoodsItem;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.view.TitleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Map;

// 订单结算页
public class SettlementActivity extends AppCompatActivity {

	SparseArray  goodsList;

	RelativeLayout rlAddressSelectLayout; // 请选择收货地址Layout
	LinearLayout llAddressMsgLayout; // 收货人信息Layout
	TextView customerName; // 顾客姓名
	TextView customerPhone; // 顾客电话
	TextView customerAddress; // 送货地址
	TextView sendTime; // 送货时间
	EditText remark; // 备注


	TextView sendMoney; // 配送费
	TextView orderTotal; // 配送费
	RecyclerView foods; // 菜品列表
	OrderItemAdapter orderItemAdapter; // 菜品列表的adapter
	Shop shop;// 店铺信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_settlement);

		Intent intent = getIntent();
		String json = intent.getStringExtra("data");
		String shopJson = intent.getStringExtra("shopJson");
		System.out.println(json);
		Gson gson = new Gson();
		Type type = new TypeToken<SparseArray>() {}.getType();
		goodsList = gson.fromJson(json, type);
		shop = gson.fromJson(shopJson, Shop.class);
		initView();
	}

	public void initView(){


		rlAddressSelectLayout = (RelativeLayout)findViewById(R.id.rl_address_select);
		llAddressMsgLayout = (LinearLayout)findViewById(R.id.ll_address_msg);
		customerName = (TextView)findViewById(R.id.tv_customer_name);
		customerPhone = (TextView)findViewById(R.id.tv_customer_phone);
		customerAddress = (TextView)findViewById(R.id.tv_customer_address);
		sendTime = (TextView)findViewById(R.id.tv_customer_sendtime);
		remark = (EditText)findViewById(R.id.et_order_remark);
		sendMoney = (TextView)findViewById(R.id.tv_order_send);
		orderTotal = (TextView)findViewById(R.id.order_total);

		TitleView titleView = (TitleView) findViewById(R.id.title);
		titleView.setTitleText("订单提交");

		foods = (RecyclerView)findViewById(R.id.rv_goods);

		sendMoney.setText(String.format("%.0f", shop.getPeisong()));
		orderTotal.setText(getTotalMoney());
		double jian = shop.getJian();

		foods.setLayoutManager(new LinearLayoutManager(this));
		orderItemAdapter = new OrderItemAdapter(this, goodsList);
		foods.setAdapter(orderItemAdapter);
	}

	public String getTotalMoney() {

		int size = goodsList.size();
		Double sum = 0.0;
		for (int i = 0; i < size; i++) {
			Map properties = (Map) goodsList.valueAt(i);
			double count = (double) properties.get("count");
			double price = (double) properties.get("price");
			double itemTotal = count * price;
			sum += itemTotal;
		}
		return String.format("%.2f", sum);
	}


}
