package com.atguigu.crowd.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Component
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("agree/protocol/page.html").setViewName("project-agree");
        registry.addViewController("launch/project/page.html").setViewName("project-launch");
        registry.addViewController("return/info/page.html").setViewName("project-return");
        registry.addViewController("create/confirm/page.html").setViewName("project-confirm");
        registry.addViewController("create/success.html").setViewName("project-success");
    }
}
