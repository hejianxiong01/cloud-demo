package com.example.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 第三方源流文件请求处理类，支持post与get请求
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 封装HTTP POST方法
     *
     * @param url          请求链接
     * @param data         json请求体
     * @param outputStream 输出流
     */
    public static HttpEntity post(final String url, final String data, final OutputStream outputStream) {

        HttpClientUtil.logger.info("1.请求第三方TTS开始");
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpClientUtil.logger.debug("1.1.建立第三方TTS，HttpClient对象成功");
        final HttpPost httpPost = new HttpPost(url);
        HttpClientUtil.logger.debug("1.2.建立第三方TTS，HTTPPost对象成功");
        //设置请求和传输超时时间
        final RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(60000)
            .setConnectTimeout(60000)
            .build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
        //文章朗读，在调用接口的时候需要加上header
        //Authorization
        //值是eac84f29ed0643118f665e47accf64e6
        //2021年11月10日14:34:24 by 王彪
        httpPost.setHeader("Authorization", "eac84f29ed0643118f665e47accf64e6");
        httpPost.setEntity(new StringEntity(data, "UTF-8"));
        HttpClientUtil.logger.debug("1.3.请求第三方TTS链接配置初始化成功");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (response.getStatusLine()
                .getStatusCode() == HttpURLConnection.HTTP_OK) {
                HttpClientUtil.logger.debug("1.4.第三方TTS请求成功，返回数据");
                return response.getEntity();
            } else {
                HttpClientUtil.logger.error("第三方TTS请求失败，返回编码为[{}]", response.getStatusLine()
                    .getStatusCode());
                throw new RuntimeException("网络繁忙，请稍后重试");
            }
        } catch (final IOException e) {
            HttpClientUtil.logger.error("请求异常，异常信息为[{}]", JSON.toJSONString(e));
            throw new RuntimeException("网络繁忙，请稍后重试");
        }
    }

    /**
     * 封装HTTP GET方法
     *
     * @param url 访问链接
     */
    public static void get(final String url, final OutputStream outputStream) {

        final CloseableHttpClient httpClient = HttpClients.createDefault();
        final HttpGet httpGet = new HttpGet();
        //设置请求和传输超时时间
        final RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(60000)
            .setConnectTimeout(60000)
            .build();
        httpGet.setConfig(requestConfig);
        httpGet.setURI(URI.create(url));
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            response.getEntity()
                .writeTo(outputStream);
        } catch (final IOException e) {
            HttpClientUtil.logger.error("请求异常，异常信息为[{}]", JSON.toJSONString(e));
            throw new RuntimeException("请求异常，请稍后重试");
        } finally {
            //使用完成后直接清空
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

}