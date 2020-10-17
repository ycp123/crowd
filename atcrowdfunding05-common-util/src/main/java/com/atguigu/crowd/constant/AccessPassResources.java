package com.atguigu.crowd.constant;

import java.util.HashSet;
import java.util.Set;

public class AccessPassResources {
    public static final Set<String> PASS_RES_SET = new HashSet<>();

    static {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/to/reg/page.html");
        PASS_RES_SET.add("/auth/member/to/login/page.html");
        PASS_RES_SET.add("/auth/member/logout.html");
        PASS_RES_SET.add("/auth/member/do/login.html");
        PASS_RES_SET.add("/auth/do/member/register.html");
        PASS_RES_SET.add("/auth/member/send/short/message.json");
    }

    public static final Set<String> STATIC_RES_SET = new HashSet<>();

    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }
    public static boolean judgeCurrentServletPathWeatherStaticResource(String servletPath){
        //1.判断servletPath是否有效
        if(servletPath==null||servletPath.length()==0){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        System.out.println(servletPath);
        //2.根据"/"化分servlet
        String[] split = servletPath.split("/");
        //3.判断是否在集合里面
        return STATIC_RES_SET.contains(split[1]);
    }
}
