package com.atguigu.crowd.service.api;

import com.atguigu.crowd.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> getAllAuth();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
