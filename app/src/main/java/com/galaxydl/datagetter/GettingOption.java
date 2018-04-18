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
    private boolean post;
    /**
     * 若请求类型为true则需要{@see okhttp3.RequestBody}
     */
    private RequestBody requestBody;
    /**
     * 是否使用https，true为使用，默认为false
     */
    private boolean https;

    private GettingOption(Builder builder) {
        this.url = builder.url;
        this.post = builder.post;
        this.requestBody = builder.requestBody;
        this.https = builder.https;
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPost() {
        return post;
    }

    public void setPost(boolean post) {
        this.post = post;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public static class Builder {
        String url;
        boolean post;
        RequestBody requestBody;
        boolean https;

        public Builder(String url) {
            this.url = url;
            https = false;
            post = false;
        }

        public GettingOption build() {
            return new GettingOption(this);
        }

        public Builder post() {
            post = true;
            return this;
        }

        public Builder requestBody(RequestBody requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public Builder https() {
            https = true;
            return this;
        }

    }
}
