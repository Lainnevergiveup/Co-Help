package com.cohelp.task_for_stu;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.request.RequestOptions;

import com.lzy.ninegrid.NineGridView;
import com.xuexiang.xui.XUI;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;


public class MyCoHelp extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        XUI.init(this);
        XUI.debug(true);


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(12, 12, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        // 初始化 Glide
        // 设置缓存容量为 100MB
        GlideBuilder builder = new GlideBuilder();
        builder.setMemoryCache(new LruResourceCache(100*1024*1024))
                .setDiskCache(new InternalCacheDiskCacheFactory(this,250*1024*1024))
                .setSourceExecutor(GlideExecutor.newUnlimitedSourceExecutor());
        Glide.init(this, builder);

        //九宫格组件初始化
        NineGridView.setImageLoader(new PicassoImageLoader());


    }
    public static Context getAppContext() {
        return mContext;
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /** Picasso 加载 */
    class PicassoImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.tuku) // 设置占位图
                    .error(R.drawable.tuku) // 设置错误图
                    .diskCacheStrategy(DiskCacheStrategy.ALL); // 缓存所有版本的图片
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }
        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}
