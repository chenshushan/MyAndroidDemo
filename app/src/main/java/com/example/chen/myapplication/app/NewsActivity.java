package com.example.chen.myapplication.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.BaseResult;
import com.example.chen.myapplication.app.adapter.NewsAdapter;
import com.example.chen.myapplication.app.bean.News;
import com.example.chen.myapplication.app.service.RetrofitService;
import com.example.chen.myapplication.app.service.RxSchedulers;
import com.example.chen.myapplication.app.util.RetrofitFactory;
import com.example.chen.myapplication.app.view.TitleView;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Collections;
import java.util.List;

public class NewsActivity extends BaseActivity {

	@BindView(R.id.title_news)
	TitleView titleNews;
	@BindView(R.id.rv_news)
	RecyclerView rvNews;

	List<News> data;
	NewsAdapter newsAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		ButterKnife.bind(this);
		titleNews.setTitleText("美食新闻");
		rvNews.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		newsAdapter = new NewsAdapter(this, data);
		rvNews.setAdapter(newsAdapter);
		rvNews.setLayoutManager(new LinearLayoutManager(this));
		initNews(false);

		RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView rv, int newState) {
				super.onScrollStateChanged(rv, newState);
				LinearLayoutManager manager = (LinearLayoutManager) rvNews.getLayoutManager();
				//不滚动时
				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
					//最后一个item
					int lastItem = manager.findLastVisibleItemPosition();
					//所有的item
					int totalItemCount = manager.getItemCount();
					if (lastItem == totalItemCount - 1) { // 滚动到最后一项时加载数据
						initNews(true);
					}

				}
			}
		};

		rvNews.addOnScrollListener(listener);

	}

	// 请求360美食新闻


 	public void initNews(final boolean isAppend){
		RetrofitService retrofitService = RetrofitFactory.getInstance();
		final Observable<BaseResult<List<News>>> news = retrofitService.getNews( "qq.com", "美食", newsAdapter.getPage() + "");
		news.subscribeOn(Schedulers.io()).map(new Function<BaseResult<List<News>>, List<News>>() {
			@Override
			public List<News> apply(BaseResult<List<News>> baseResult)  {
				return baseResult.getData();
			}
		}).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<News>>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(List<News> data) {
				newsAdapter.setNewsList(data, isAppend);
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onComplete() {

			}
		});
	}
}
