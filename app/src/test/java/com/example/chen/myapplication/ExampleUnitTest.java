package com.example.chen.myapplication;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.junit.Test;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect()  {

		//创建消费者，消费者接受一个String类型的事件
		Consumer<String> consumer = new Consumer<String>() {
			@Override
			public void accept(String s)  {
				System.out.println(s);
			}
		};
		//被观察者发出Hello World, 并且指定该事件的消费者为consumer
		Observable.just("Hello World").subscribe(consumer);
	}

	@Test
	public void f(){
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
}