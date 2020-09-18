package com.atguigu.crowd.service.api;

import com.atguigu.crowd.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {
    //    保存用户
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getAdminPage(String keyWord, int pageNum, int pageSize);
}
