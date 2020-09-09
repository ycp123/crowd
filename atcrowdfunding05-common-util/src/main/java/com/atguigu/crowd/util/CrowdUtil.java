package com.atguigu.crowd.util;

import javax.servlet.http.HttpServletRequest;

public class CrowdUtil {
    /**
     * 判断是否为ajax请求
     *
     * @param request
     * @return true 是
     * false 否
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        //1.获取请求体
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");
        //2.判断
        if (acceptHeader != null && acceptHeader.contains("application/json")
                || xRequestHeader != null && xRequestHeader.contains("XMLHttpRequest")) {
            return true;
        }
        return false;
    }
}
