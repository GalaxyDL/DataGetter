package com.galaxydl.datagetter;

import android.app.Activity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import okhttp3.*;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * DataGetter 提供了利用okhttp向后端请求数据并用gson解析的功能。
 *
 * @author Galaxy
 * @since 2017-11-27
 */
public enum DataGetter {
    INSTANCE;
    private final static String TAG = "DataGetter";

    private final static int THREAD_CORE_SIZE = 5;
    private final static int THREAD_MAX_SIZE = 20;
    private final static long THREAD_KEEP_ALIVE_TIME = 10;
    private final static TimeUnit THREAD_TIME_UNIT = TimeUnit.SECONDS;

    private ThreadPoolExecutor threadPool;

    private OkHttpClient client;

    @FunctionalInterface
    public interface OnFinishListener<T> {
        /**
         * 当请求完成返回结果时被调用。
         * 对结果的获取和处理应该在回调中完成。
         * 回调将会在ui线程执行。
         *
         * @param results 请求所返回的结果
         * @param e       请求中发生的错误，若正常则为null
         */
        void onFinish(List<T> results, @Nullable Exception e);
    }

    /**
     * 通过GET请求获取json数据
     * 并进行解析
     *
     * @param url      api url
     * @param listener 完成结果的监听器
     * @param clazz    要解析成的对象的类型
     * @param <T>      要解析成的对象的类型
     */
    public <T> void get(String url,
                        OnFinishListener<T> listener,
                        Class clazz,
                        Activity currentActivity) {
        get(new GettingOption(url), listener, clazz, currentActivity);
    }

    /**
     * @param option   见{@see GettOption}
     * @param listener 完成结果的监听器
     * @param clazz    要解析成的对象的类型
     * @param <T>      要解析成的对象的类型
     */
    public <T> void get(GettingOption option,
                        OnFinishListener<T> listener,
                        Class clazz,
                        Activity currentActivity) {
        threadPool.execute(() -> {
            Request request = new Request.Builder()
                    .url(option.getUrl())
                    .build();
            doRequestAndCallback(request, listener, clazz, currentActivity);
        });
    }

    /**
     * 通过POST请求获取json数据
     * 并进行解析
     *
     * @param option   见{@see GetterOption}
     * @param listener 完成结果的监听器
     * @param clazz    要解析成的对象的类型
     * @param <T>      要解析成的对象的类型
     */
    public <T> void post(GettingOption option,
                         OnFinishListener<T> listener,
                         Class clazz,
                         Activity currentActivity) {
        threadPool.execute(() -> {
            Request request = new Request.Builder()
                    .url(option.getUrl())
                    .post(option.getRequestBody())
                    .build();
            doRequestAndCallback(request, listener, clazz, currentActivity);
        });
    }

    private <T> void doRequestAndCallback(Request request,
                                          OnFinishListener<T> listener,
                                          Class clazz,
                                          Activity currentActivity) {
        Response response = null;
        try {
            response = client.newCall(request)
                    .execute();
        } catch (IOException e) {
            doOnFinish(null, e, listener, currentActivity);
        }
        if (response != null) {
            List<T> results;
            try {
                results = parseJSON(response.body().string(), clazz);
                doOnFinish(results, null, listener, currentActivity);
            } catch (IOException e) {
                doOnFinish(null, e, listener, currentActivity);
            }
        }
    }

    /**
     * 注意，c的类型必须和接收该方法返回值List中的类型一致。
     *
     * @param s   json字符串
     * @param c   要解析成的对象的类型
     * @param <T> 要解析成的对象的类型
     * @return 该类型的ArrayList
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> parseJSON(String s, Class c) {
        List<T> result = new ArrayList<>();
        if (s == null) return result;
        JSONArray jsonArray = JSON.parseArray(s);
        for (Object aJson : jsonArray) {
            result.add((T) JSON.parseObject(aJson.toString(), c));
        }
        return result;
    }

    /**
     * 在ui线程进行回调
     */
    @SuppressWarnings("unchecked")
    private <T> void doOnFinish(List<T> result,
                                Exception e,
                                OnFinishListener listener,
                                Activity currentActivity) {
        currentActivity.runOnUiThread(() -> listener.onFinish(result, e));
    }

    private void init() {
        threadPool = new ThreadPoolExecutor(THREAD_CORE_SIZE,
                THREAD_MAX_SIZE,
                THREAD_KEEP_ALIVE_TIME,
                THREAD_TIME_UNIT,
                new LinkedBlockingDeque<>());

        client = new OkHttpClient();
    }

    DataGetter(){
        init();
    }

}