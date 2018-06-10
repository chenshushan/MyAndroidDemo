package com.example.chen.myapplication.app.util.network.service;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.*;

public interface CommonService {

    /**
     * 单个上传文件或者图片
     * @param part
     * @return
     */
    @Multipart
    @POST("files")
    Observable<String> singleFileUpload(@Part MultipartBody.Part part);

}