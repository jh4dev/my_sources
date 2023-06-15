package com.lottewellfood.sfa.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class RestAPIUtil {

	public static String buildUrl(String baseUrl, Map<String, Object> params) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        boolean isFirst = true;

        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (isFirst) {
                    urlBuilder.append("?");
                    isFirst = false;
                } else {
                    urlBuilder.append("&");
                }
                urlBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                urlBuilder.append("=");
                urlBuilder.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
            }
            
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: UTF-8");
        }

        return urlBuilder.toString();
    }
}
