package com.dj.util;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHttpResponse;

/**
 * @author 莫小阳
 */
public class HttpUtils {

    public static HttpResponse getRawHtml(HttpClient client, String personalUrl) {
        HttpResponse response = null;
        try {
            //获取响应文件，即html，采用get方法获取响应数据
            HttpGet getMethod = new HttpGet(personalUrl);
            response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
            response = client.execute(getMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
