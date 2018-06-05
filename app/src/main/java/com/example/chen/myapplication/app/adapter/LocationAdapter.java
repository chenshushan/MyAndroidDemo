package com.example.chen.myapplication.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.mapapi.search.core.PoiInfo;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.AddAddressActivity;

import java.util.List;

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
		if (pois == null) {
			return 0;
		}
		return pois.size();
	}


	class ViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.txt_name)
		TextView tvName;
		@BindView(R.id.txt_address)
		TextView tvAddress;

		ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public void bindData(PoiInfo poiInfo) {
			tvName.setText(poiInfo.name);
			tvAddress.setText(poiInfo.address);
		}
		@OnClick({R.id.view_content})
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
