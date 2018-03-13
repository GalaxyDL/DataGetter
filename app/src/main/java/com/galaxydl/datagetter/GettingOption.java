package com.galaxydl.datagetter;

import okhttp3.RequestBody;

/**
 * 提供对{@see DataGetter}中的请求进行定制的功能
 * 在{@see DataGetter#get} 和 {@see DataGetter#post} 中需要提供此类的对象
 */
public class GettingOption {
    /**
     * api url
     */
    private String url;
    /**
     * 请求类型，true为POST，false为GET，默认为false
     */
    private boolean isPost = false;
    /**
     * 若请求类型为true则需要{@see okhttp3.RequestBody}
     */
    private RequestBody requestBody;

    public GettingOption(String url) {
        this.url = url;
    }

    public GettingOption(String url, RequestBody requestBody) {
        this.url = url;
        this.requestBody = requestBody;
    }

    public GettingOption(String url, boolean isPost, RequestBody requestBody) {
        this.url = url;
        this.isPost = isPost;
        this.requestBody = requestBody;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(boolean post) {
        isPost = post;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
}
