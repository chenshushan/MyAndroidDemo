package com.example.chen.myapplication.http;


import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.chen.myapplication.MyApplication;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Administrator on 2017/4/19.
 */

public class VolleyManager {
	private static RequestQueue requestQueue;
	//超时时间 30s
	private final static int TIME_OUT = 30000;
	public static volatile String cookies;
	private static RequestQueue getRequestQueue() {
		if (requestQueue == null) {
			synchronized (VolleyManager.class) {
				if (requestQueue == null) {
					//使用全局context对象
					requestQueue = Volley.newRequestQueue(MyApplication.getContext());
				}
			}
		}
		return requestQueue;
	}

	private static <T> void addRequest(RequestQueue requestQueue, Request<T> request) {
		request.setShouldCache(true);
		request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		requestQueue.add(request);
	}

	public static void sendJsonObjectRequest(int method, String url,
											 JSONObject params,
											 final Response.Listener<JSONObject> listener,
											 final Response.ErrorListener errorListener) {
		try {
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method,
					url, params, listener, errorListener) {
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String, String> headers = new HashMap<>();
					headers.put("accept-encoding", "utf-8");
					return headers;
				}

			};
			addRequest(getRequestQueue(), jsonObjectRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendStringRequest(int method, String url,
										 final Map<String, String> params,
										 final Response.Listener<String> listener,
										 final Response.ErrorListener errorListener) {
		try {
			StringRequest stringRequest = new StringRequest(method, url, listener, errorListener) {
				@Override
				protected Map<String, String> getParams() throws AuthFailureError {
					return params;
				}

				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String, String> headers = new HashMap<>();
					headers.put("accept-encoding", "utf-8");
					return headers;
				}

				@Override
				protected Response<String> parseNetworkResponse(NetworkResponse response) {
					Response<String> superResponse = super
							.parseNetworkResponse(response);
					Map<String, String> responseHeaders = response.headers;
					String rawCookies = responseHeaders.get("Set-Cookie");
					cookies = rawCookies.substring(0, rawCookies.indexOf(";"));
					// String cookies = part1;
					Log.d("sessionid", "sessionid----------------" + cookies);
					return superResponse;
				}
			};
			addRequest(getRequestQueue(), stringRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}