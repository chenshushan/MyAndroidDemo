package com.example.chen.myapplication.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.OrdersAdapter;
import com.example.chen.myapplication.app.bean.Order;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static com.example.chen.myapplication.app.bean.Order.ORDER_LIST;

public class OrderFragment extends Fragment {

	RecyclerView ordersRv;
	TextView label;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_order_tab, container, false);
		ordersRv = (RecyclerView) view.findViewById(R.id.rv_orders);
		label = (TextView) view.findViewById(R.id.tv_order_text);

		Type type = new TypeToken<List<Order>>() {}.getType();
		List<Order> orders =PreferenceUtil.getObject(ORDER_LIST, type);

		if(orders == null) {
			ordersRv.setVisibility(View.GONE);
			return view;
		}else {
			label.setVisibility(View.GONE);
		}
		ordersRv.setLayoutManager(new LinearLayoutManager(getActivity()));
		ordersRv.setAdapter(new OrdersAdapter(getActivity(), orders));
		return view;
	}





}
