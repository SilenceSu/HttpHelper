package com.github.helper.http;

import com.github.helper.http.request.param.post.PostFormParams;
import com.github.helper.http.request.param.post.PostJsonParams;

import java.util.HashMap;

/**
 * @author SilenceSu
 * @Email Silence.Sx@Gmail.com
 * Created by Silence on 2020/11/2.
 */
public class HttpHelperTest {
    private static  String url = "https://www.baidu.com";

    @Test
    public void helloBaidu(){
        String result = HttpHelper.get(url, () -> new HashMap<String, String>() {{
            put("wd", "hello");
        }});
        System.out.println(result);
    }

    @Test
    public void helloAsync() throws InterruptedException {

        HttpHelper.get(url, () -> new HashMap<String, String>() {{
            put("wd", "hello");
        }}, new HttpCallBack() {
            @Override
            public void success(String url, String result) {
                System.out.println("访问成功");
                System.out.println(result);
            }
        });
        System.out.println("开始睡觉");
        Thread.sleep(2000);

    }

    @Test
    public void  helloFormPost(){
        String result=HttpHelper.post(url, (PostFormParams) () -> new HashMap<String, String>() {{
            put("wd", "hello");
        }});
        System.out.println(result);

    }
    @Test
    public void  helloJsonPost(){
        String result = HttpHelper.post(url, (PostJsonParams) () -> new Object());
        System.out.println(result);
    }

    @Test
    public void helloPostAsync() throws InterruptedException {
        HttpHelper.post(url, (PostFormParams) () -> new HashMap<String, String>() {{
            put("wd", "hello");
        }}, new HttpCallBack() {
            @Override
            public void success(String url, String result) {
                System.out.println("异步访问成功");
            }
        });
        System.out.println("开始睡觉");
        Thread.sleep(2000);

    }

}
