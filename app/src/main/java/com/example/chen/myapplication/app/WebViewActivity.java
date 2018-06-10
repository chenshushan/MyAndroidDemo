package com.example.chen.myapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.example.chen.myapplication.app.view.TitleView;

public class WebViewActivity extends AppCompatActivity {

	@BindView(R.id.web_title)
	TitleView webTitle;
	@BindView(R.id.web_view)
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		ButterKnife.bind(this);
		webTitle.setTitleText("美食新闻");

		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		if(url == null || url.trim().equals("")) {
			url = "http://www.baidu.com";
			ToastUtil.showToast("页面走丢了！！");
		}

		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(url);
	}
}
