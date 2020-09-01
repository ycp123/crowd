package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.Admin;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }
}
