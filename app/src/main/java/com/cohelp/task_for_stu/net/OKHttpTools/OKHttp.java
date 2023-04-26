package com.cohelp.task_for_stu.net.OKHttpTools;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.cohelp.task_for_stu.MyCoHelp;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.cache.CacheRequest;

@RequiresApi(api = Build.VERSION_CODES.O)
public class OKHttp {
    public static OkHttpClient client;

    public static Cache cache;

    public static CookieJarImpl cookieJar;


    static {
        File httpCacheDirectory = new File(MyCoHelp.getAppContext().getExternalCacheDir(), "okhttpCache");
        int cacheSize = 100 * 1024 * 1024; // 100 MiB
        cache = new Cache(httpCacheDirectory, cacheSize);

        //有网时候的缓存拦截器
        final Interceptor NetCacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                int onlineCacheTime =30;//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
                Response responseCache = response.newBuilder()
                        .header("Cache-Control", "max-age="+onlineCacheTime)
                        .removeHeader("Pragma")
                        .build();
                return responseCache;
            }
        };

        //没有网时候的缓存拦截器
        final Interceptor OfflineCacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!MyCoHelp.isNetworkConnected()) {
                    int offlineCacheTime = Integer.MAX_VALUE;//离线的时候的缓存的过期时间
                    request = request.newBuilder()
                            .header("Cache-Control", "only-if-cached, max-stale=" + offlineCacheTime)
                            .build();
                }
                return chain.proceed(request);
            }
        };


        //初始化OkHttpClient
        cookieJar = new CookieJarImpl(new PersistentCookieStore(MyCoHelp.getAppContext()));
        client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addNetworkInterceptor(NetCacheInterceptor)
                .addInterceptor(OfflineCacheInterceptor)
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public OKHttp() {

    }

    public static Response sendPostRequest(String ip, String jsonBody, Integer cacheTime) {
        Response response = null;
        //创建请求
        Request.Builder requestBuilder = new Request.Builder().url(ip);

        //添加body参数
        if(jsonBody!=null){
            RequestBody body = RequestBody.create( MediaType.parse("application/json"), jsonBody);
            requestBuilder.method("POST", body).addHeader("Content-Type", "application/json");
        }else {
            RequestBody body = RequestBody.create( MediaType.parse("application/json"), "");
            requestBuilder.method("POST", body).addHeader("Content-Type", "application/json");
        }

        Request request = requestBuilder.build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Request buildPostRequest(String ip, String jsonBody, Integer cacheTime) {
        Response response = null;
        //创建请求
        Request.Builder requestBuilder = new Request.Builder().url(ip);


        //添加body参数
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);
        requestBuilder.method("POST", body).addHeader("Content-Type", "application/json");

        Request request = requestBuilder.build();

        return request;
    }

    public static Response sendPostRequest(String ip, String jsonName, String jsonContent,
                                 Map<String, String> filesNameAndPaths,
                                 Integer cacheTime) {
        Response response = null;
        //初始化请求
        Request.Builder requestBuilder = new Request.Builder().url(ip);

        //初始化MultipartBody
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        //添加json参数
        builder.addFormDataPart(jsonName, jsonContent);

        //添加（图片）文件参数
        if (filesNameAndPaths != null) {
            for(Map.Entry<String,String> fileNameAndPath : filesNameAndPaths.entrySet()){
                File file = new File(fileNameAndPath.getValue());
                RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"),file);
                builder.addFormDataPart("file",file.getName(),body);
            }
        }

        //设置请求方式
        requestBuilder.method("POST", builder.build());

        //执行请求
        Request request = requestBuilder.build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
    public static Request buildPostRequest(String ip, String jsonName, String jsonContent,
                                           Map<String, String> filesNameAndPaths,
                                           Integer cacheTime) {
        Response response = null;
        //初始化请求
        Request.Builder requestBuilder = new Request.Builder().url(ip);

        //初始化MultipartBody
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        //添加json参数
        builder.addFormDataPart(jsonName, jsonContent);

        //添加（图片）文件参数
        if (filesNameAndPaths != null) {
            Iterator<Map.Entry<String, String>> fileList = filesNameAndPaths.entrySet().iterator();
            for (Map.Entry<String, String> fileNameAndPath : filesNameAndPaths.entrySet()) {
                String fileName = fileNameAndPath.getKey();
                File file = new File(fileNameAndPath.getValue());
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                builder.addFormDataPart("file", fileName, fileBody);
            }
        }

        //设置请求方式
        requestBuilder.method("POST", builder.build());

        //缓存控制
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(cacheTime, TimeUnit.SECONDS)
                .build();
        requestBuilder.cacheControl(cacheControl);
        Request request = requestBuilder.build();

        return request;
    }
    public static Request buildGetRequest(String ip, Map<String,List<String>> map,Integer cacheTime) {
        Response response = null;
        //创建Url对象
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ip).newBuilder();

        //添加param参数
        if (map != null) {
            for (Map.Entry<String, List<String>> param : map.entrySet()) {
                String key = param.getKey();
                param.getValue().forEach(value -> {
                    urlBuilder.addQueryParameter(key, value);
                });
            }
        }

        //生成url
        String url = urlBuilder.build().toString();

        //创建请求
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .method("GET", null);


        //缓存控制
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(cacheTime, TimeUnit.SECONDS)
                .build();
        requestBuilder.cacheControl(cacheControl);

        //执行请求
        Request request = requestBuilder.build();

        return request;
    }
    public static Response sendGetRequest(String ip, Map<String,List<String>> map,Integer cacheTime) {
        Response response = null;
        //创建Url对象
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ip).newBuilder();

        //添加param参数
        if(map!=null){
            for(Map.Entry<String,List<String>> param : map.entrySet()){
                String key = param.getKey();
                param.getValue().forEach(value->{
                    urlBuilder.addQueryParameter(key,value);
                });
            }
        }

        //生成url
        String url = urlBuilder.build().toString();

        //创建请求
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .method("GET",null);


        //缓存控制
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(cacheTime, TimeUnit.SECONDS)
                .build();
        requestBuilder.cacheControl(cacheControl);

        //执行请求
        Request request = requestBuilder.build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}
