package com.github.silencesu.helper.http;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Http回调参数
 *
 * @author SilenceSu
 * @Email Silence.Sx@Gmail.com
 * Created by Silence on 2020/10/15.
 */
public abstract class HttpCallBack implements Callback {
    private static final Logger logger = LoggerFactory.getLogger(HttpCallBack.class);

    @Override
    public void onFailure(Call call, IOException e) {
        logger.error("Http调用失败" + e);

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        success(response.request().url().toString(), response.body().string());
    }


    public abstract void success(String url, String result);
}
