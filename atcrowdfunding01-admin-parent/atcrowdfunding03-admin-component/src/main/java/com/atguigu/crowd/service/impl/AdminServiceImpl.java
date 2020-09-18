package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.Admin;
import com.atguigu.crowd.AdminExample;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;

    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        //1.通过adminMapper查询admin
        //1.1创建admin对象
        AdminExample adminExample = new AdminExample();
        //1.2创建criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        //1.3给criteria对象封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);
        //1.4查询
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        //2.判断admin是否存在
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        if (adminList.size() > 1) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        int index = 0;
        Admin admin = adminList.get(index);
        //3.不存在则抛出异常
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        //4.存在则进行密码比较
        //4.1将admin中的密码取出
        String userPswdDB = admin.getUserPswd();
        //4.2将userPswd进行md5加密
        String userPswdForm = CrowdUtil.md5(userPswd);
        //5.判断密码是否正确
        if (!userPswdDB.equals(userPswdForm)) {
            //5.1.密码错误则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        //5.2.密码正确则返回admin对象
        return admin;
    }

    public PageInfo<Admin> getAdminPage(String keyWord, int pageNum, int pageSize) {
        //1.通过pageHelper开启分页
        PageHelper.startPage(pageNum, pageSize);
        //2.通过adminMapper查询
        List<Admin> adminList = adminMapper.selectAdminListByKeyWord(keyWord);
        //3.将查询结果封装到pageInfo里
        PageInfo<Admin> pageInfo = new PageInfo<Admin>(adminList);
        //4.返回结果
        return pageInfo;
    }
}
