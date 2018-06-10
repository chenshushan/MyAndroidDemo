package com.example.chen.myapplication.app.util.network;

import android.content.Context;
import com.example.chen.myapplication.app.adapter.BaseResult;
import com.example.chen.myapplication.app.bean.News;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RestApiClient {

    private static final String DEVICE_TYPE = "android";

    private Context mContext;
    private final File mCacheLocation;
    private Retrofit mRetrofit;
    private String mToken;

    public RestApiClient(Context context, File cacheLocation) {
        mContext = context;
        mCacheLocation = cacheLocation;
    }

    public RestApiClient setToken(String token) {
        mToken = token;
        mRetrofit = null;
        return this;
    }

    /**
     * 连接超时时间
     */
    public static final int CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s

    /**
     * 响应超时时间
     */
    public static final int READ_TIMEOUT_MILLIS = 20 * 1000; // 20s

    private OkHttpClient newRetrofitClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置缓存路径
        if (mCacheLocation != null) {
            File cacheDir = new File(mCacheLocation, UUID.randomUUID().toString());
            Cache cache = new Cache(cacheDir, 1024);
            builder.cache(cache);
        }
        // 设置超时时间
        builder.connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        builder.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        // 添加请求签名拦截器
//        builder.addInterceptor(new RequestSignInterceptor());
        // 添加请求头
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder newRequest = chain.request().newBuilder();
//                newRequest.addHeader(Header.HTTP_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
//                newRequest.addHeader(Header.HTTP_APP_VERSION, SystemUtil.getAppVersionName(mContext));
//                newRequest.addHeader(Header.HTTP_APP_KEY, AppConfig.APP_KEY);
//                newRequest.addHeader(Header.HTTP_DEVICE_ID, SystemUtil.getDeviceId(mContext));
//                newRequest.addHeader(Header.HTTP_DEVICE_TYPE, DEVICE_TYPE);
//                if (mToken != null) {
//                    newRequest.addHeader(Header.AUTHORIZATION, "Bearer " + mToken);
//                }

                return chain.proceed(newRequest.build());
            }
        });
        // 添加OkHttp日志打印器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(AppConfig.DEBUG ? Level.BODY : Level.NONE);
        builder.addInterceptor(logging);

        return builder.build();
    }

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.client(newRetrofitClient());
//            builder.baseUrl(AppConfig.SERVER_URL);
            builder.addConverterFactory(GsonConverterFactory.create(GsonHelper.builderGson()));
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

            mRetrofit = builder.build();
        }

        return mRetrofit;
    }

    private <T> T get(Class<T> clazz) {
        return getRetrofit().create(clazz);
    }

}
