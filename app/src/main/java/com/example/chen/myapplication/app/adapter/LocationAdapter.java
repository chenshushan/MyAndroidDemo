package com.example.chen.myapplication.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.baidu.location.Poi;
import com.baidu.mapapi.search.core.PoiInfo;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.AddAddressActivity;
import com.example.chen.myapplication.app.bean.GoodsItem;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import static com.example.chen.myapplication.app.AddAddressActivity.SELECT_ADDRESS;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {


	private Activity activity;
	private List<PoiInfo> pois;
	LayoutInflater inflater;
	private RecyclerView addressRv;

	public void setPois(List<PoiInfo> pois) {
		this.pois = pois;
	}
	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		addressRv = recyclerView;
	}
	public LocationAdapter(Activity activity, List<PoiInfo> pois) {
		this.activity = activity;
		this.pois = pois;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.list_location_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		PoiInfo poiInfo = pois.get(position);
		holder.bindData(poiInfo);
	}

	@Override
	public int getItemCount() {
		if(pois==null) {
			return 0;
		}
		return pois.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView tvName,tvAddress;

		public ViewHolder(View itemView) {
			super(itemView);

			tvName = (TextView) itemView.findViewById(R.id.txt_name);
			tvAddress = (TextView) itemView.findViewById(R.id.txt_address);
			itemView.setOnClickListener(this);
		}

		public void bindData(PoiInfo poiInfo){
			tvName.setText(poiInfo.name);
			tvAddress.setText(poiInfo.address);
		}

		@Override
		public void onClick(View view) {
			int position = addressRv.getChildAdapterPosition(view);
			PoiInfo poiInfo = pois.get(position);
			Intent intent = new Intent(activity, AddAddressActivity.class);

			intent.putExtra("address", poiInfo.name);
			activity.setResult(SELECT_ADDRESS, intent);
			activity.finish();

		}
	}

}
