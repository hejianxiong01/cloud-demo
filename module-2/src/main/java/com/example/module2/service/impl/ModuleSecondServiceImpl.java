package com.example.module2.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.example.common.utils.RedisUtil;
import com.example.module2.service.ModuleSecondService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hejianxiong
 */
@Service
@Slf4j
public class ModuleSecondServiceImpl implements ModuleSecondService {

    private final RedisUtil redisUtil;

    public ModuleSecondServiceImpl(final RedisUtil redisUtil) {

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

        final ClassPathResource classPathResource = new ClassPathResource("/file/bill.mp3");

        try (final InputStream in = classPathResource.getInputStream();
             final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

            final byte[] bytes = new byte[1024 * 1024];

            int rc;
            while ((rc = in.read(bytes, 0, 100)) > 0) {
                byteArrayOutputStream.write(bytes, 0, rc);
            }

            final String encodeToString;

           
            final byte[] keys = Base64.getDecoder().decode(encodeToString);

            httpServletResponse.setContentType("audio/mp3");

            httpServletResponse.getOutputStream()
                .write(keys);
        }
    }

}
