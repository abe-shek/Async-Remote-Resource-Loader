package com.example.abhishek.remoteresourceloader;

import android.content.Context;
import android.os.Build;
import android.util.LruCache;

public class ResourceManager {

    private ConvertersUtils convertersUtils;
    private Context mContext;
    private static ResourceManager instance;
    private LruCache<String, byte[]> memCache;
    private int memSize;
    ResourceLoader resourceLoader;

    public ResourceManager(Context context) {
        mContext = context;
        convertersUtils = new ConvertersUtils();
        memSize = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
        memCache = new LruCache<String, byte[]>(memSize) {};
    }

    public static ResourceManager getInstance(Context context) {
        if (instance == null)
            return instance = new ResourceManager(context);
        else
            return instance;
    }

    protected LruCache<String, byte[]> getMemoryCache() {
        return memCache;
    }

    protected boolean setMaxMemorySize(int newMaxSize) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            memSize = newMaxSize;
            memCache.resize(newMaxSize);
            return true;
        }
        return false;
    }

    public void writeToMemoryCache(String key, byte[] bytes) {
        if (memCache.get(key) == null)
            memCache.put(key, bytes);
    }

    public byte[] readFromMemoryCache(String key) {
        return memCache.get(key);

    }

    public ConvertersUtils getConvertersUtils() {
        return this.convertersUtils;
    }

    public ResourceLoader getResourceLoader(){
        if(resourceLoader !=null)
            return resourceLoader;
        else
            return resourceLoader = new ResourceLoader(mContext, instance);
    }
}
