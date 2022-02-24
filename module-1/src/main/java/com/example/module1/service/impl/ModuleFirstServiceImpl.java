package com.example.module1.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.utils.HttpClientUtil;
import com.example.common.utils.RedisUtil;
import com.example.module1.service.ModuleFirstService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hejianxiong
 */
@Service
@Slf4j
public class ModuleFirstServiceImpl implements ModuleFirstService {

    @Value("${bill-info-convert.url:}")
    private String url;

    @Value("${bill-info-convert.message:}")
    private String message;

    private final RedisUtil redisUtil;

    public ModuleFirstServiceImpl(final RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 文件转换.
     *
     * @param httpServletRequest  request
     * @param httpServletResponse response
     */
    @Override
    public void fileConvert(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
        throws Exception {
        final JSONObject jsonObject = new JSONObject();

        final String format = String.format(message, "consName", "consNo", "mouth", "mq", "amt");

        System.out.println(format);

        jsonObject.put("aue", "3");
        jsonObject.put("text", "这是测试内容");
        jsonObject.put("language", "");
        jsonObject.put("speed", "");
        jsonObject.put("pitch", "");
        jsonObject.put("volume", "");

        final HttpEntity httpEntity = HttpClientUtil.post(url, jsonObject.toString(), httpServletResponse.getOutputStream());

        try (final InputStream inputStream = httpEntity.getContent();
             final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            final byte[] bytes = new byte[1024 * 1024];

            int rc;
            while ((rc = inputStream.read(bytes, 0, 100)) > 0) {
                byteArrayOutputStream.write(bytes, 0, rc);
            }

            final String encodeToString;

            if (redisUtil.hasKey("key")) {
                ModuleFirstServiceImpl.log.info("存在key");
                encodeToString = String.valueOf(redisUtil.get("key"));
            } else {
                ModuleFirstServiceImpl.log.info("不存在key");
                encodeToString = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
                redisUtil.set("key", encodeToString, 60);
            }

            httpServletResponse.getOutputStream().write(byteArrayOutputStream.toByteArray());
        }

    }

}
