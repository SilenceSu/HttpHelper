package com.github.helper.http.request.param.get;


import com.github.helper.http.request.RequestParams;

import java.util.Map;

/**
 * Http Help请求参数
 * @author SilenceSu
 * @Email Silence.Sx@Gmail.com
 * Created by Silence on 2020/10/15.
 */
@FunctionalInterface
public interface GetParams extends RequestParams {
    Map<String, String> getParams();
}
