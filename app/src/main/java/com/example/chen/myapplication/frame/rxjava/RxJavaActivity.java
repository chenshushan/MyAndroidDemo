package com.example.chen.myapplication.frame.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.util.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

	@BindView(R.id.textView3)
	TextView one;
	@BindView(R.id.textView4)
	TextView two;
	@BindView(R.id.textView5)
	TextView three;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_java);
		ButterKnife.bind(this);
		two.setText("子线程休眠5秒");
		Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> e) throws Exception {
				Thread.sleep(5*1000);
				e.onNext("hello RxJava");
				e.onComplete();
			}
		}).subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<String>() {
			@Override
			public void accept(String s)  {
				one.setText(s);
				three.setText("切换回主线程在Text设置Observable发出的值" + s);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		ToastUtil.showToast("onResume");
	}
}
