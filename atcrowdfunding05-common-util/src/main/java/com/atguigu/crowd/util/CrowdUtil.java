package com.atguigu.crowd.util;

import com.atguigu.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 常筹网工具类
 *
 * @author cpy
 */
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

    /**
     * 将明文字符串进行加密
     *
     * @param source 明文字符串
     * @return 加密后的字符串
     */
    public static String md5(String source) {
        //1.判断传入的字符串是否为空
        if (source == null || source.equals("")) {
            new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        //2.进行加密操作
        try {
            //2.1创建MessageDigest对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            //2.2获取明文字符数组
            byte[] input = source.getBytes();
            //2.3进行加密
            byte[] output = messageDigest.digest(input);
            //2.4将加密字符数组通过BigInteger转化字符串便于存储于数据库
            //2.4.1创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            //2.4.2通过16位进制转化字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            //2.5返回加密字符串
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

}
