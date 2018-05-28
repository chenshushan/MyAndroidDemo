package com.example.chen.myapplication.app.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.MyAddressActivity;
import com.example.chen.myapplication.app.SettlementActivity;
import com.example.chen.myapplication.app.bean.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {


	private MyAddressActivity activity;
	private List<Address> addressList;
	LayoutInflater inflater;
	private RecyclerView addressRv;

	public AddressAdapter(MyAddressActivity activity, List<Address> addressList) {
		this.activity = activity;
		this.addressList = addressList;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		addressRv = recyclerView;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.list_address_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Address address = addressList.get(position);
		holder.bind(address);
	}

	@Override
	public int getItemCount() {
		if(addressList==null) {
			return 0;
		}
		return addressList.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		TextView mNameTxt;
		TextView mGenderTxt;
		TextView mMobileTxt;

		TextView mAddressTxt;

		ImageButton mDeleteBtn;
		ImageButton mEditBtn;
		public ViewHolder(View itemView) {
			super(itemView);
			mNameTxt = (TextView) itemView.findViewById(R.id.txt_name);
			mGenderTxt = (TextView) itemView.findViewById(R.id.txt_gender);
			mMobileTxt = (TextView) itemView.findViewById(R.id.txt_mobile);
			mAddressTxt = (TextView) itemView.findViewById(R.id.txt_address);
			mDeleteBtn = (ImageButton) itemView.findViewById(R.id.btn_delete);
			mEditBtn = (ImageButton) itemView.findViewById(R.id.btn_edit);

			itemView.setOnClickListener(this);
		}


		public void bind(Address address) {
			mNameTxt.setText(address.getName());
			mGenderTxt.setText(address.getSex());
			mMobileTxt.setText(address.getPhoneNumber());
			mAddressTxt.setText(address.getAddress() + address.getAddressDetail());

		}

		@Override
		public void onClick(View view) {
			// 点击之后返回对应的地址信息
			int position = addressRv.getChildAdapterPosition(view);
			Address address = addressList.get(position);
			Intent intent = new Intent(activity, SettlementActivity.class);
			intent.putExtra("address", address);
			activity.setResult(SELECT_ADDRESS_OK, intent);
			activity.finish();
		}
	}
	public static final int SELECT_ADDRESS_OK = 0;

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}
}
