package com.example.abhishek.remoteresourceloader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ResourceRetrofitInterface {
    @GET
    Call<ResponseBody> getResourceFromUrl(@Url String url);
}
