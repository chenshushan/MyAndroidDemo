package com.example.chen.myapplication.frame.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.example.chen.myapplication.frame.eventbus.event.BaseEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.example.chen.myapplication.app.BaseActivity.eventBus;

public class EventBusActivity extends AppCompatActivity {

	@BindView(R.id.bus_text)
	TextView busText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_bus);
		ButterKnife.bind(this);
		eventBus.register(this);
	}

	@Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND)
	public void getText(BaseEvent<List<String>> baseEvent) throws InterruptedException {
		Thread.sleep(10*1000);
		List<String> data = baseEvent.getData();
		final String s = data.get(0);

		Observable.fromArray(data).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<String>>() {
			@Override
			public void accept(List<String> list) throws Exception {
				String event = list.get(0);
				ToastUtil.showToast("已通过EventBus休眠10秒，设置TextView的值为：" + event);
				busText.setText(event);
			}
		});


	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		eventBus.unregister(this);
	}
}
