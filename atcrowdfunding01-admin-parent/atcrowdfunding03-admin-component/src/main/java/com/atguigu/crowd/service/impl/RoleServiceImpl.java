package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.Role;
import com.atguigu.crowd.RoleExample;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    /**
     * 获取角色信息数据
     *
     * @param pageSize 页面显示信息行数
     * @param pageNum  页面当前页
     * @param keyWord  关键字
     * @return 角色信息
     */
    public PageInfo<Role> getPageInfo(Integer pageSize, Integer pageNum, String keyWord) {
        //1.开启pageHelper实例
        PageHelper.startPage(pageNum, pageSize);
        //2.查询角色信息
        List<Role> roleList = roleMapper.selectRoleByKeyWord(keyWord);
        //3.将roleList封装到pageInfo中
        PageInfo<Role> pageInfo = new PageInfo<Role>(roleList);
        //4.返回角色信息
        return pageInfo;
    }

    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    public void removeRole(List<Integer> roleIdList) {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(roleIdList);
        roleMapper.deleteByExample(example);
    }

    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }

}
