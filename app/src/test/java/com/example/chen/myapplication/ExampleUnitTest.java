package com.example.chen.myapplication;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import org.junit.Test;

import java.util.Arrays;


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


}