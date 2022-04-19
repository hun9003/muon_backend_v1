package com.muesli.music.common.util;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Map;

public class ClientUtils {

    public static String getRemoteIP(HttpServletRequest request){
        String ip = request.getHeader("X-FORWARDED-FOR");

        //proxy 환경일 경우
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        //웹로직 서버일 경우
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr() ;
        }

        return ip;
    }

    public static String getPlayerKey(Map<String, Object> matadatas) throws Exception {
        JSONObject jsondata = new JSONObject();
        jsondata.put("t", matadatas.get("t"));
        jsondata.put("u", matadatas.get("u"));
        jsondata.put("e", matadatas.get("e"));
        jsondata.put("m", matadatas.get("m"));
        jsondata.put("a", HashGenerator.sha1(matadatas.get("a").toString()).substring(0, 5));
        System.out.println(jsondata);
        return URLEncoder.encode(HashGenerator.encAES(jsondata.toString()), "UTF-8").replace("*", "%2A").replace("+", "%20").replace("%7E", "~");
    }
}