
package com.example.chen.myapplication.app;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.bean.Address;
import com.example.chen.myapplication.app.util.PermissionUtils;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static com.example.chen.myapplication.app.MyAddressActivity.ADDRESS_OK;

public class AddAddressActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

	public static int count = 1;

	EditText name;
	EditText phone;
	EditText address;
	EditText addressDetail;
	CheckBox man;
	CheckBox woman;

	Button submit;
	TextView linker;
	TextView location;
	public static final int SELECT_LINKER = 1;
	public static final int SELECT_ADDRESS = 2;
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

		submit = (Button) findViewById(R.id.bt_sure);
		submit.setOnClickListener(this);

		linker = (TextView) findViewById(R.id.select_linker);
		linker.setOnClickListener(this);

		location = (TextView) findViewById(R.id.select_address);
		location.setOnClickListener(this);

		man.setOnCheckedChangeListener(this);
		woman.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
		int id = compoundButton.getId();
		if (R.id.ck_left == id) { // 男
			if (checked) {
				woman.setChecked(false);
			}
		} else {
			if (checked) {
				man.setChecked(false);
			}
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (R.id.bt_sure == id) { // 提交地址数据
			subAddress();
		} else if (R.id.select_linker == id) { // 选择联系人
			PermissionUtils.checkAndRequestPermission(this, READ_CONTACTS, SELECT_LINKER,
					new PermissionUtils.PermissionRequestSuccessCallBack() {
						@Override
						public void onHasPermission() {
							// 权限已被授予
							Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
							startActivityForResult(intent, SELECT_LINKER);
						}
					});
		} else if(R.id.select_address == id) {
			Intent intent = new Intent(this, BaiduMapActivity.class);
			startActivityForResult(intent, SELECT_ADDRESS);
		}
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == SELECT_LINKER) {
			if (PermissionUtils.isPermissionRequestSuccess(grantResults)) {
				// 权限已被授予
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, SELECT_LINKER);
			}
		}

	}



	public void subAddress() {

		boolean manChecked = man.isChecked();
		boolean womanChecked = woman.isChecked();
		String aName = name.getText().toString();//获取输入的联系人姓名
		String aPhone = phone.getText().toString();//获取输入的联系人的手机号
		String aAddress = address.getText().toString();//输入的定位地址
		String aAdressDetail = addressDetail.getText().toString();//获取输入的详细地址

		String sex = "";
		if (manChecked) {
			sex = "先生";
		}
		if (womanChecked) {
			sex = "女士";
		}

		Address address = new Address(aName, sex, aPhone, aAddress, aAdressDetail);
		address.setId(count++);

		Type type = new TypeToken<List<Address>>() {
		}.getType();
		List<Address> addresses = PreferenceUtil.getObject("addresses", type);
		if (addresses == null) {
			addresses = new ArrayList();
		}
		addresses.add(address);
		PreferenceUtil.set("addresses", addresses);

		Intent intent = new Intent(this, MyAddressActivity.class);
		setResult(ADDRESS_OK, intent);
		finish();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		switch (requestCode) {
			case 1 : {
				if (resultCode == RESULT_OK) {
					Uri contactData = intent.getData();
					Cursor cursor = managedQuery(contactData, null, null, null, null);
					cursor.moveToFirst();
					String phoneNum = this.getContactPhone(cursor);
					phone.setText(phoneNum);
				}
				break;

			}
			case 2: {
				String addressStr = intent.getStringExtra("address");
				address.setText(addressStr);
				break;
			}
		}


	}


	//获取联系人电话
	private String getContactPhone(Cursor cursor) {

		int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
		int phoneNum = cursor.getInt(phoneColumn);
		String phoneResult = "";
		//System.out.print(phoneNum);
		if (phoneNum > 0) {
			// 获得联系人的ID号
			int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			String contactId = cursor.getString(idColumn);
			// 获得联系人的电话号码的cursor;
			Cursor phones = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
					null, null);
			//int phoneCount = phones.getCount();
			//allPhoneNum = new ArrayList<String>(phoneCount);
			if (phones.moveToFirst()) {
				// 遍历所有的电话号码
				for (; !phones.isAfterLast(); phones.moveToNext()) {
					int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
					int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
					int phone_type = phones.getInt(typeindex);
					String phoneNumber = phones.getString(index);
					switch (phone_type) {
						case 2:
							phoneResult = phoneNumber;
							break;
					}
				}
				if (!phones.isClosed()) {
					phones.close();
				}
			}
		}
		return phoneResult;
	}

}
