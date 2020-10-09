package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.Role;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleHandler {
    @Autowired
    private RoleService roleService;
    @PreAuthorize("hasRole('部长')")
    @RequestMapping("role/get/page/info.json")
    @ResponseBody
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                              @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "keyWord",defaultValue = "") String keyWord){
        //1.查询角色信息
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageSize, pageNum, keyWord);
        //2.将结果封装到resultEntity中
        ResultEntity<PageInfo<Role>> pageInfoResultEntity = ResultEntity.successWithData(pageInfo);
        //3.返回resultEntity
        return pageInfoResultEntity;
    }
    @RequestMapping("role/save.json")
    @ResponseBody
    public ResultEntity<String> saveRole(Role role){
        //1.进行保存操作
        roleService.saveRole(role);
        //2.返回结果
        return ResultEntity.successWithOutData();
    }
    @RequestMapping("role/update.json")
    @ResponseBody
    public ResultEntity<String> updateRole(Role role){
        //1.进行修改操作
        roleService.updateRole(role);
        //2.返回结果
        return ResultEntity.successWithOutData();
    }
    @RequestMapping("role/remove/by/role/id/array.json")
    @ResponseBody
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList){
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithOutData();
    }
}
