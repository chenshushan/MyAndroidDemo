package com.example.chen.myapplication.app.service;

import com.example.chen.myapplication.app.adapter.BaseResult;
import com.example.chen.myapplication.app.bean.News;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface RetrofitService {
	@GET("news/qihoo?apikey=5ocMM9S1qZusEwbiTJzBE3kiSRj1cBWjxRWzKkmtHaCyWderapHNB2mILVvOcA67")
	Observable<BaseResult<List<News>>> getNews(@Query("site") String site, @Query("kw") String kw, @Query("pageToken") String pageToken);
}
