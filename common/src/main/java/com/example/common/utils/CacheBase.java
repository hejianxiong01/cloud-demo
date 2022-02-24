package com.example.common.utils;

import lombok.SneakyThrows;

public class CacheBase {

    // 设置缓存逻辑
    @SneakyThrows
    public static String cacheDataBase(final String serialNo, final String headerLog, final String key,
                                       final String value, final int seconds) {
        return CacheBase.encode(value);
    }

    public static String encode(final String content) throws Exception {
        return Base64JdkUtils.encode(content);
    }

    // 解密
    public static String dncode(final String content) throws Exception {
        return Base64JdkUtils.decode(content);
    }

}
