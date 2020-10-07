package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.Admin;
import com.atguigu.crowd.Role;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.AuthService;
import com.atguigu.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrowdUserDetailsService implements UserDetailsService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AuthService authService;

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 1.根据userName获取admin对象
        List<Admin> adminList = adminService.getAdminByLoginAcct(userName);
        Admin admin = new Admin();
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
        } else {
            throw new RuntimeException();
        }
        Integer adminId = admin.getId();
        // 2.根据adminId获取相关角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 3.根据adminId获取相关权限
        List<String> authNameList = authService.getAssignedAuthNameByAdminId(adminId);
        // 4.遍历角色列表将结果存储
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : assignedRoleList) {
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
        // 5.遍历权限列表将结果存储
        for(String authName: authNameList){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }
        // 6.返回securityAdmin对象
        return new SecurityAdmin(admin,authorities);
    }
}
