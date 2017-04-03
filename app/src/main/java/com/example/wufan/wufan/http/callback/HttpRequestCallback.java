package com.example.wufan.wufan.http.callback;

import com.example.wufan.wufan.http.exception.ApiException;

/**
 * Created by chenlongfei on 2016/11/19.
 */

public interface HttpRequestCallback<T> {
    void onStart();

    void onSuccess(T responseData);

    void onFail(ApiException apiException);
}
