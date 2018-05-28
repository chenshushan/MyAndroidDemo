package com.example.chen.myapplication.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.AddressAdapter;
import com.example.chen.myapplication.app.bean.Address;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.example.chen.myapplication.app.view.TitleView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyAddressActivity extends AppCompatActivity {

	public static final int ADDRESS_OK = 1;

	TitleView titleView;

	RecyclerView rvAddress;

	LinearLayout addAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_address);

		titleView = (TitleView)findViewById(R.id.address_title);
		titleView.setTitleText("我的地址");
		rvAddress = (RecyclerView)findViewById(R.id.address_list);
		addAddress = (LinearLayout)findViewById(R.id.btn_create_address);
		final Context context = this;
		addAddress.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, AddAddressActivity.class);
				startActivityForResult(intent, ADDRESS_OK);
			}
		});

		rvAddress.setLayoutManager(new LinearLayoutManager(this));
		Type type = new TypeToken<List<Address>>() {}.getType();

		List<Address> addresses = PreferenceUtil.getObject("addresses", type);
		if(addresses == null) {
			addresses = new ArrayList();
		}

		addressAdapter = new AddressAdapter(this, addresses);
		rvAddress.setAdapter(addressAdapter);
	}

	AddressAdapter addressAdapter;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case ADDRESS_OK:
			if(requestCode == ADDRESS_OK) {
				Type type = new TypeToken<List<Address>>() {}.getType();
				List<Address> addresses = PreferenceUtil.getObject("addresses", type);
				addressAdapter.setAddressList(addresses);
				addressAdapter.notifyDataSetChanged();
			}
			break;
		}
	}
}
