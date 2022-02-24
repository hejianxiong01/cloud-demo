package com.example.moudle1.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.example.common.utils.RedisUtil;
import com.example.moudle1.service.ModuleFirstService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hejianxiong
 */
@Service
@Slf4j
public class ModuleFirstServiceImpl implements ModuleFirstService {

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
        final String reqBody = StreamUtils.copyToString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);

        System.out.println(reqBody);

        try (final InputStream in = new FileInputStream("src/main/resources/file/bill.mp3");
             final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

            final byte[] bytes = new byte[1024 * 1024];

            int rc;
            while ((rc = in.read(bytes, 0, 100)) > 0) {
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

            final byte[] keys = java.util.Base64.getDecoder().decode(encodeToString);

            httpServletResponse.setContentType("audio/mp3");

            httpServletResponse.getOutputStream()
                .write(keys);

            httpServletResponse.getWriter().flush();

        }
    }

}
