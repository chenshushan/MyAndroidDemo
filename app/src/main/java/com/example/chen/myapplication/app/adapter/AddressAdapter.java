package com.example.chen.myapplication.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ShopDetailActivity;
import com.example.chen.myapplication.app.bean.Address;
import com.example.chen.myapplication.app.bean.GoodsItem;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {


	private Context activity;
	private List<Address> addressList;
	LayoutInflater inflater;

	public AddressAdapter(Context activity, List<Address> addressList) {
		this.activity = activity;
		this.addressList = addressList;
		inflater = LayoutInflater.from(activity);
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

	class ViewHolder extends RecyclerView.ViewHolder {

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
		}


		public void bind(Address address) {
			mNameTxt.setText(address.getName());
			mGenderTxt.setText(address.getSex());
			mMobileTxt.setText(address.getPhoneNumber());
			mAddressTxt.setText(address.getAddress() + address.getAddressDetail());

		}
	}

}
