package com.example.chen.myapplication;

import com.example.chen.myapplication.app.adapter.BaseResult;
import com.example.chen.myapplication.app.bean.News;
import com.example.chen.myapplication.app.util.network.RestApiClient;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() {

		//创建消费者，消费者接受一个String类型的事件
		Consumer<String> consumer = new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println(s);
			}
		};
		//被观察者发出Hello World, 并且指定该事件的消费者为consumer
		Observable.just("Hello World").subscribe(consumer);
	}

	@Test
	public void f() {
		// ObservableEmitter： Emitter是发射器的意思
		//创建一个上游 Observable：
		Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) {
				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);
				emitter.onComplete();
			}
		});
		//创建一个下游 Observer
		Observer<Integer> observer = new Observer<Integer>() {
			@Override
			public void onSubscribe(Disposable d) {
				System.out.println("subscribe");
			}

			@Override
			public void onNext(Integer value) {
				System.out.println("onNext" + value);
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError");
			}

			@Override
			public void onComplete() {
				System.out.println("complete");
			}
		};
		//建立连接
		observable.subscribe(observer);
	}

	@Test
	public void filter() {
		//把Consumer可以看做精简版的Observer
		Consumer<String> consumer = new Consumer<String>() {
			//accept可以简单的看做onNext
			@Override
			public void accept(String s) throws Exception {
				System.out.println("accept: " + s); //这里只能吃上饺子
			}
		};

		Observable.just("包子", "馒头", "肠粉", "春卷", "饺子", "炒粉")
				.filter(new Predicate<String>() {
					@Override
					public boolean test(String s) throws Exception {
						return s.equals("饺子");//只允许饺子通过测试
					}
				})
				.subscribe(consumer);
	}
	public interface Api {
		@GET("top250")
		Observable<Map> listTop250(@Query("start") int start, @Query("count") int count);

		@GET("news/qihoo?apikey=5ocMM9S1qZusEwbiTJzBE3kiSRj1cBWjxRWzKkmtHaCyWderapHNB2mILVvOcA67&site=qq.com")
		Observable<BaseResult<List<News>>> getNews(@Query("kw") String kw, @Query("pageToken") String pageToken);
	}

	@Test
	public void retrofitDemo(){
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api01.bitspaceman.com:8000/")
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();
		mApi = retrofit.create(Api.class);
//		String url = "apikey=5ocMM9S1qZusEwbiTJzBE3kiSRj1cBWjxRWzKkmtHaCyWderapHNB2mILVvOcA67&kw=%e7%be%8e%e9%a3%9f&site=baidu.com";
		Observable<BaseResult<List<News>>> news = mApi.getNews("美食", "7");

		news//在io线程池中执行map
				//将网络的结果转换成我们要的电影名的列表
				.map(new Function<BaseResult<List<News>>, List<News>>() {
					@Override
					public List<News> apply(BaseResult<List<News>> baseResult)  {
						System.out.println(baseResult);
						return baseResult.getData();
					}
				})
//				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<List<News>>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(List<News> value) {
				for (News s : value) {
					System.out.println("onNext:" + s.getTitle());
				}
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}
		});

	}
	private  Api mApi;
	public Api getApi() {
		return mApi;
	}


	OkHttpClient client = new OkHttpClient();
	@Test
	public void post() throws IOException {
		RequestBody formBody = new FormBody.Builder()
				.add("start", "1")
				.add("count", "10")
				.build();

		Request request = new Request.Builder()
				.url("https://api.douban.com/v2/movie/")
				.post(formBody)
				.build();

		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			String string = response.body().string();
			System.out.println(string);
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

	@Test
	public void listTest(){
		List list = new ArrayList();
		for (int i = 0; i < 10; i++) {
			list.add(1);
		}

		int size = list.size();
		for (int i = 0; i <= size; i++) {
			if(i == size) {
				list.add(i,2);
			}
		}


	}
}