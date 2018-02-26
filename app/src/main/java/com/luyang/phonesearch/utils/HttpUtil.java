package com.luyang.phonesearch.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by luyang on 2018/1/13.
 */

public class HttpUtil {

    OkHttpClient mClient = new OkHttpClient();
    String mUrl;
    Map<String, String> mParams;
    HttpResponse mHttpResponse;

    private Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 处理结果的回调接口
     */
    public interface HttpResponse {
        public void onSucess(Object object);

        public void onError(String error);
    }

    public HttpUtil(HttpResponse httpResponse) {
        mHttpResponse = httpResponse;
    }


    public void sendPostRequest(String url, Map<String, String> params) {
        sendRequest(url, params, true);
    }

    public void sendGetRequest(String url, Map<String, String> params) {
        sendRequest(url, params, false);
    }


    private void sendRequest(String url, Map<String, String> params, boolean isPost) {
        if(!isPost){
            mUrl = urlToString(url,params);
        }
        mParams = params;
        final Request request = createRequest(isPost);
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mHttpResponse != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHttpResponse.onError("请求错误");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(mHttpResponse != null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mHttpResponse.onSucess(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

    }


    //Get请求拼接URL TO STRING
    private String urlToString(String url, Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        builder.append(url);


        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            builder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        String getUrl = builder.toString();
        return getUrl.substring(0, getUrl.length() - 1);
    }

    //构造Request请求
    private Request createRequest(boolean isPost) {
        Request request;
        if (isPost) {
            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
            requestBodyBuilder.setType(MultipartBody.FORM);
            //遍历map请求增加到builder中
            Iterator it = mParams.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                requestBodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }

            request = new okhttp3.Request.Builder().url(mUrl).post(requestBodyBuilder.build()).build();
        } else {
            request = new Request.Builder().url(mUrl).build();
        }
        return request;
    }

}
