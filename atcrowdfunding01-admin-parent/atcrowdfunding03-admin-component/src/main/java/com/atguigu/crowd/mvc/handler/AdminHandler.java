package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.Admin;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {
    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(AdminHandler.class);

    @RequestMapping("admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session) {
        //1.验证账号密码是否正确
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
        //2.登录失败则抛出异常 登录成功则将admin对象存入session域中
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        //3.返回
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("admin/do/logout.html")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("admin/get/page.html")
    public String getAdminPage(
            @RequestParam(value = "keyWord", defaultValue = "") String keyWord,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            ModelMap modelMap) {
        //1.通过adminService查询分页结果
        PageInfo<Admin> pageInfo = adminService.getAdminPage(keyWord, pageNum, pageSize);
        //2.将分页结果封装到modelMap中
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @RequestMapping(value = {"admin/remove/{adminId}/{keyWord}/{pageNum}.html",
            "admin/remove/{adminId}/{pageNum}.html"})
    public String removeAdmin(@PathVariable("adminId") Integer adminId,
                              @PathVariable(value = "keyWord", required = false) String keyWord,
                              @PathVariable("pageNum") Integer pageNum) {
        //执行删除操作
        adminService.removeAdminByAdminId(adminId);
        //判断keyWord是否为null
        keyWord = adminService.judgeKeyWord(keyWord);
        //返回页面
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyWord=" + keyWord;
    }

    @RequestMapping("admin/save.html")
    public String saveAdmin(Admin admin) {
        //1执行新增方法
        adminService.saveAdmin(admin);
        //2.通过重定向返回
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId, ModelMap modelMap, HttpServletRequest request) {
        //1.通过adminId查询admin的信息
        Admin admin = adminService.getAdminById(adminId);
        //2.将admin放入到modelMap中
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_ADMIN, admin);
        return "admin-edit";
    }

    @RequestMapping("admin/update.html")
    public String update(Admin admin, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyWord") String keyWord) {
        //1.修改用户信息
        adminService.update(admin);
        //2.重定向
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyWord=" + keyWord;
    }
}
