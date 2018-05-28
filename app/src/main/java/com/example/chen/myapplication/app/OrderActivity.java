package com.example.chen.myapplication.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.OrdersAdapter;
import com.example.chen.myapplication.app.bean.Order;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static com.example.chen.myapplication.app.bean.Order.ORDER_LIST;

public class OrderActivity extends AppCompatActivity {

	RecyclerView ordersRv;
	TextView label;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_list);
		ordersRv = (RecyclerView) findViewById(R.id.rv_orders);
		label = (TextView) findViewById(R.id.tv_order_text);

		Type type = new TypeToken<List<Order>>() {}.getType();
		List<Order> orders =PreferenceUtil.getObject(ORDER_LIST, type);

		if(orders == null) {
			ordersRv.setVisibility(View.GONE);
			return;
		}else {
			label.setVisibility(View.GONE);
		}
		ordersRv.setLayoutManager(new LinearLayoutManager(this));
		ordersRv.setAdapter(new OrdersAdapter(this, orders));
	}

}
