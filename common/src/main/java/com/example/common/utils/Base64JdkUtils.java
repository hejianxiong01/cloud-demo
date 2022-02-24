package com.example.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64JdkUtils {
    public static String encode(final String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(final String data) {
        return new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
    }

    public static byte[] decodeArray(final String data) {
        return Base64.getDecoder().decode(data);
    }
}
