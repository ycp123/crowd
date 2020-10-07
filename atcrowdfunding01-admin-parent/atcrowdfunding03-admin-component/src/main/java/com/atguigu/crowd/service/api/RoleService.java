package com.atguigu.crowd.service.api;

import com.atguigu.crowd.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageSize,Integer pageNum,String keyWord);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleIdList);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);
}
