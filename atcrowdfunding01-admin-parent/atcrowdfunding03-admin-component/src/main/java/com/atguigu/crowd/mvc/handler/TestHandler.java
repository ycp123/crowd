package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.Admin;
import com.atguigu.crowd.Student;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;
    private Logger logger = LoggerFactory.getLogger(TestHandler.class);
    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap, HttpServletRequest request){
        boolean judge = CrowdUtil.judgeRequestType(request);
        logger.info("judge="+judge);
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList",adminList);
        return "target";
    }
    @RequestMapping("send/array1.html")
    public String sendArray1(@RequestParam("array[]") List<Integer> array){
        for(Integer number:array){
            System.out.println(number);
        }
        return "target";
    }
    @RequestMapping("send/array2.json")
    @ResponseBody
    public String sendArray2(@RequestBody List<Integer> array){
        for(Integer number:array){
            System.out.println(number);
        }
        return "target";
    }
    @RequestMapping("send/compose.json")
    @ResponseBody
    public ResultEntity<Student> sendCompose(@RequestBody Student student){
        logger.info(student.toString());
        String a = null;
        System.out.println(a.length());
        ResultEntity<Student> resultEntity = ResultEntity.successWithData(student);
        return resultEntity;
    }
}
