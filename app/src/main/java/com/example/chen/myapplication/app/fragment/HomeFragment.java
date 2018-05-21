package com.example.chen.myapplication.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.HomeAdapter;

public class HomeFragment extends Fragment {

	RecyclerView recyclerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_tab,container,false);
		recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		HomeAdapter homeAdapter = new HomeAdapter(getActivity(), getActivity().getSupportFragmentManager());
		recyclerView.setAdapter(homeAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

	}
}
