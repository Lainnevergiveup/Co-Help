package com.cohelp.task_for_stu.net.OKHttpTools;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class MyOKHTTPClient {
    private static final int CACHE_SIZE = 30 * 1024 * 1024; // 10 MiB
    private static MyOKHTTPClient instance;
    private final OkHttpClient client;

    private MyOKHTTPClient(Context context) {
        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);

        client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }
    public static synchronized MyOKHTTPClient getInstance(Context context) {
        if (instance == null) {
            instance = new MyOKHTTPClient(context);
        }
        return instance;
    }
    public OkHttpClient getClient() {
        return client;
    }
}
