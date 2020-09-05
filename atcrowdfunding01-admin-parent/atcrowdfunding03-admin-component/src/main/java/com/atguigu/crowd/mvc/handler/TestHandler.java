package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.Admin;
import com.atguigu.crowd.service.api.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;
    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap){
        Logger logger = LoggerFactory.getLogger(TestHandler.class);
        logger.info("跳转过来了");
        logger.info("跳转过来了");
        logger.info("跳转过来了");
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList",adminList);
        return "target";
    }
}
