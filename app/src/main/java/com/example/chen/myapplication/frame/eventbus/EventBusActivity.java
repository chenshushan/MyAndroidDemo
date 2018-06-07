package com.example.chen.myapplication.frame.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.frame.eventbus.event.BaseEvent;
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

	@Subscribe(sticky = true)
	public void getText(BaseEvent<List<String>> baseEvent){
		List<String> data = baseEvent.getData();
		String s = data.get(0);
		busText.setText(s);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		eventBus.unregister(this);
	}
}
