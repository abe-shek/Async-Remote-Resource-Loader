package com.example.abhishek.remoteresourceloader;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResourceLoader {
    private Context mContext;
    ResourceManager resourceManager;

    public ResourceLoader(Context context, ResourceManager instance) {
        this.mContext = context;
        this.resourceManager = instance;
    }

    private void fetchResourceFromRemote(final String url, final OnDownloadCompletionListener listener, View onCancel) {
        ResourceRetrofitInterface retrofitInterface = new RetrofitBuilder().createRetrofitService(ResourceRetrofitInterface.class);
        final Call<ResponseBody> call = retrofitInterface.getResourceFromUrl(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String url = call.request().url().toString();
                byte[] responseBytes = convertToByteArray(url, response);
                listener.resourceFetchedFromRemote(url, responseBytes);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException)
                    Toast.makeText(mContext, "Please check your network connection", Toast.LENGTH_LONG).show();

            }
        });
        if (onCancel != null) {
            onCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call.cancel();
                    listener.resourceDownloadCancelled(url);
                }
            });
        }
    }

    public void fetchResource(String url, OnDownloadCompletionListener listener, View onCancel) {
        byte[] responseBytes = resourceManager.readFromMemoryCache(url);
        if (responseBytes != null) {
            listener.resourceFetchedFromMemory(url, responseBytes);
        } else {
            fetchResourceFromRemote(url, listener, onCancel);
        }
    }

    private class RetrofitBuilder {
        public RetrofitBuilder() {
        }

        public <T> T createRetrofitService(Class<T> retrofitApiClass) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(mContext.getString(R.string.APP_BASE_URL)).build();
            return retrofit.create(retrofitApiClass);
        }
    }

    private byte[] convertToByteArray(String url, Response<ResponseBody> response) {
        byte[] bytes = null;
        try {
            bytes = response.body().bytes();
            resourceManager.writeToMemoryCache(url, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}