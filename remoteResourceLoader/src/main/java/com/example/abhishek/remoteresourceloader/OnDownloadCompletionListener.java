package com.example.abhishek.remoteresourceloader;

public interface OnDownloadCompletionListener {
    void resourceFetchedFromRemote(String url , byte[] bytes);
    void resourceFetchedFromMemory(String url , byte[] bytes);
    void resourceDownloadCancelled(String url);
}
