package com.example.chen.myapplication.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClasstwoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClasstwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class ClasstwoFragment extends Fragment {



	private Context context;
	private LayoutInflater inflater;

	public ClasstwoFragment(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fg_classtwo, container, false);
	}
}
