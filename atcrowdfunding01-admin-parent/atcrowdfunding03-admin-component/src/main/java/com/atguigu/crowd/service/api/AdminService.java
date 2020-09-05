package com.atguigu.crowd.service.api;

import com.atguigu.crowd.Admin;

import java.util.List;

public interface AdminService {
    //    保存用户
    void saveAdmin(Admin admin);

    List<Admin> getAll();
}
