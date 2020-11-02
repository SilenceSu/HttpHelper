package com.github.helper.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.helper.http.request.param.extend.CookicesParams;
import com.github.helper.http.request.param.extend.HeaderParams;
import com.github.helper.http.request.param.get.GetParams;
import com.github.helper.http.request.param.post.PostFormParams;
import com.github.helper.http.request.param.post.PostJsonParams;
import com.github.helper.http.request.param.post.PostParams;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Http调用助手
 *
 * @author SilenceSu
 * @Email Silence.Sx@Gmail.com
 * Created by Silence on 2020/10/13.
 */
public class HttpHelper {


    private static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    private static  final ObjectMapper objectMapper = new ObjectMapper();



    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static final OkHttpClient okClient = new OkHttpClient();

    static {
        //设置基本参数
        okClient.dispatcher().setMaxRequests(500);
        okClient.dispatcher().setMaxRequestsPerHost(10);

    }


    public static <T extends GetParams> String get(String url, T params) {
        return get(url, params, null);
    }


    public static <T extends GetParams> String get(String url, T getRequest, HttpCallBack callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                .newBuilder();

        Map<String, String> params = getRequest.getParams();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        Request request = new Request.Builder().url(urlBuilder.build()).get().build();


        if (callback == null) {

            try (Response response = okClient.newCall(request).execute()) {
                return response.body().string();
            } catch (IOException e) {
                logger.error("请求地址错误" + url + e.getMessage());
            }
        } else {
            okClient.newCall(request).enqueue(callback);
        }


        return null;

    }


    public static <T extends PostParams> String post(String url, T params) {
        return post(url, params, null);
    }

    public static <T extends PostParams> String post(String url, T params, HttpCallBack callback) {

        /**
         * 构建requestbody
         */
        RequestBody requestBody = null;
        if (params instanceof PostJsonParams) {
            String jsonStr = null;
            try {
                jsonStr = objectMapper.writeValueAsString(((PostJsonParams) params).params());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            requestBody = RequestBody.create(jsonStr, JSON);
        } else if (params instanceof PostFormParams) {
            FormBody.Builder builder = new FormBody.Builder();
            Map<String, String> paramMap = ((PostFormParams) params).params();
            if (!paramMap.isEmpty()) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());

                }
            }
            requestBody = builder.build();
        }


        /**
         * 检查是否有headers
         */
        Headers requestHeaders = null;
        if (params instanceof HeaderParams) {
            Headers.Builder headerBuilder = new Headers.Builder();
            Map<String, String> headsMap = ((HeaderParams) params).headers();
            if (!headsMap.isEmpty()) {
                for (Map.Entry<String, String> entry : headsMap.entrySet()) {
                    headerBuilder.add(entry.getKey(), entry.getValue());
                }
            }
            //cookie加上
            requestHeaders = headerBuilder.build();

        }

        /**
         * 构建请求对象
         */
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);

        if (requestBody != null) {
            requestBuilder.post(requestBody);
        }
        if (requestHeaders != null) {
            requestBuilder.headers(requestHeaders);
        }

        /**
         * 检查是否有cookic参数
         */
        if (params instanceof CookicesParams) {

            Map<String, String> cookiceMap = ((CookicesParams) params).cookices();
            if (!cookiceMap.isEmpty()) {
                StringBuilder buffer = new StringBuilder();
                for (Map.Entry<String, String> entry : cookiceMap.entrySet()) {

                    buffer.append(entry.getKey());
                    buffer.append("=");
                    buffer.append(entry.getValue());
                    buffer.append(";");
                }
                requestBuilder.addHeader("Cookie", buffer.toString());
            }
        }


        if (callback == null) {

            try (Response response = okClient.newCall(requestBuilder.build()).execute()) {
                return response.body().string();
            } catch (IOException e) {
                logger.error("请求地址错误" + url + e.getMessage());
            }

        } else {
            okClient.newCall(requestBuilder.build()).enqueue(callback);
        }


        return null;
    }


}
