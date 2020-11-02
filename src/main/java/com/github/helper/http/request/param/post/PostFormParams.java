package com.github.helper.http.request.param.post;

import java.util.Map;

/**
 * @author SilenceSu
 * @Email Silence.Sx@Gmail.com
 * Created by Silence on 2020/10/15.
 */
@FunctionalInterface
public interface PostFormParams extends PostParams {
    Map<String, String> params();
}
