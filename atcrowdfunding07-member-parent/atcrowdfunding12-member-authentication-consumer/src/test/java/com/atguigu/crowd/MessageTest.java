package com.atguigu.crowd;

import com.atguigu.crowd.config.ShortMessageConfig;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageTest {
    @Autowired
    private ShortMessageConfig shortMessageConfig;
    @Test
    public void messageTest(){
        CrowdUtil.sendCodeByShortMessage(
                shortMessageConfig.getHost(),
                shortMessageConfig.getPath(),
                shortMessageConfig.getMethod(),
                shortMessageConfig.getAppCode(),
                "15270743750");
    }
    @Test
    public void modeTest(){
        String host = "https://zwp.market.alicloudapi.com";
        String path = "/sms/tmplist";
        String method = "GET";
        String appcode = "e76b2384223448eaa1e61a0c9fc8221e";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
