
package com.example.chen.myapplication.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.bean.Address;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddAddressActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener {

	public static int count = 1;

	EditText name;
	EditText phone;
	EditText address;
	EditText addressDetail;
	CheckBox man;
	CheckBox woman;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);
		initView();
	}

	private void initView() {
		name = (EditText) findViewById(R.id.et_contactName);
		phone = (EditText) findViewById(R.id.et_Phone);
		address = (EditText) findViewById(R.id.tv_haveaddressactivity_location_right);
		addressDetail = (EditText) findViewById(R.id.et_haveaddressactivity_detail_location);
		man = (CheckBox) findViewById(R.id.ck_left);
		woman = (CheckBox) findViewById(R.id.ck_right);

		man.setOnCheckedChangeListener(this);
		woman.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
		int id = compoundButton.getId();
		if(R.id.ck_left == id) { // 男
			if(checked) {
				woman.setChecked(false);
			}
		} else {
			if(checked) {
				man.setChecked(false);
			}
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if(R.id.bt_sure == id) {
			subAddress();
		}
	}

	public void subAddress(){

		boolean manChecked = man.isChecked();
		boolean womanChecked = woman.isChecked();
		String aName = name.getText().toString();//获取输入的联系人姓名
		String aPhone = phone.getText().toString();//获取输入的联系人的手机号
		String aAddress = address.getText().toString();//输入的定位地址
		String aAdressDetail = addressDetail.getText().toString();//获取输入的详细地址

		String sex = "";
		if(manChecked) {
			sex = "先生";
		}
		if(womanChecked) {
			sex = "女士";
		}

		Address address = new Address(aName, sex, aPhone, aAddress, aAdressDetail);
		address.setId(count++);

		Type type = new TypeToken<List<Address>>() {}.getType();
		List<Address> addresses = PreferenceUtil.getObject("addresses", type);
		if(addresses == null) {
			addresses = new ArrayList();
		}
		addresses.add(address);
		PreferenceUtil.set("addresses", addresses);

		Intent intent = new Intent(this, MyAddressActivity.class);
		startActivity(intent);
	}
}
