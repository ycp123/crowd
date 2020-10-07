package com.atguigu.crowd.service.api;

import com.atguigu.crowd.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {
    //    保存用户
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    List<Admin> getAdminByLoginAcct(String loginAcct);

    PageInfo<Admin> getAdminPage(String keyWord, int pageNum, int pageSize);

    void removeAdminByAdminId(Integer adminId);

    String judgeKeyWord(String keyWord);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
