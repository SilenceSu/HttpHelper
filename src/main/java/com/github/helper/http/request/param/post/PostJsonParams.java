package com.github.helper.http.request.param.post;

/**
 * @author SilenceSu
 * @Email Silence.Sx@Gmail.com
 * Created by Silence on 2020/10/15.
 */
@FunctionalInterface
public interface PostJsonParams extends PostParams {
    Object params();
}
