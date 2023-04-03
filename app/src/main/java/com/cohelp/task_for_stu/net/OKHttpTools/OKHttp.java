package com.cohelp.task_for_stu.net.OKHttpTools;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttp {
    OkHttpClient client;
//    MyOKHTTPClient myOKHTTPClient;
    Context context;
    Request request;
    Response response;
    RequestBody body;

//    public OKHttp(Context context) {
//        this.context = context;
//        myOKHTTPClient = MyOKHTTPClient.getInstance(this.context);
//        client = myOKHTTPClient.getClient();
//    }

    public OKHttp() {
    }

    public Response getResponse() {
        return response;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public RequestBody getBody() {
        return body;
    }

    public void setBody(RequestBody body) {
        this.body = body;
    }

    public void sendRequest(String ip, String requestBody) {

//        client = MyOKHTTPClient.getInstance(context).getClient();
//        try {
//            client.cache().remove$okhttp(request);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
         client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
         body = RequestBody.create(mediaType, requestBody);
         request = new Request.Builder()
                .url(ip)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGetRequest(String ip) {
        client = new OkHttpClient().newBuilder()
                .build();
        request = new Request.Builder()
                .url(ip)
                .method("GET",null)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendGetRequest(String ip,String session) {
        client = new OkHttpClient().newBuilder()
                .build();
        System.out.println("se"+session);
        request = new Request.Builder()
                .url(ip)
                .method("GET",null)
                .addHeader("Cookie",session)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(String ip,String requestBody,String session) {
         client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
         body = RequestBody.create(mediaType, requestBody);
         request = new Request.Builder()
                .url(ip)
                .method("POST", body)
                .addHeader("Cookie",session)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMediaRequest(String ip, String contentType, String content, Map<String,String> nameAndPath,String cookie){
        Map<String,String> map = new HashMap<>();
//        map.put("签名.PNG","/Users/lain/Pictures/lyh/1_inch_lyh.png");
         client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE);
        builder.addFormDataPart(contentType,content);
        if (nameAndPath!=null) {
            Iterator<Map.Entry<String,String>> fileList = nameAndPath.entrySet().iterator();
            while (fileList.hasNext()){
                Map.Entry<String,String> entry = fileList.next();
                builder.addFormDataPart("file",entry.getValue(),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(entry.getValue())));
            }
        }
        System.out.println(cookie);
        body = builder.build();
        request = new Request.Builder()
                 .url(ip)
                 .addHeader("Cookie",cookie)
                 .method("POST", body)
                 .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
