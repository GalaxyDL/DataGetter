package com.galaxydl.datagetter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Galaxy on 2018/3/15.
 */

public enum CookieManager implements CookieJar {
    INSTANCE;
    private static final String TAG = "CookieManager";

    private PersistentCookieStore cookieStore;

    public void init(Context context) {
        cookieStore = new PersistentCookieStore(context);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.add(cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }
}
