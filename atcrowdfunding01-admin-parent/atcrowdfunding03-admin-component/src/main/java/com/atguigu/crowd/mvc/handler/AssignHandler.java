package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.Auth;
import com.atguigu.crowd.Role;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.AuthService;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AuthService authService;

    @RequestMapping("assign/get/all/auth.json")
    @ResponseBody
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> authList = authService.getAllAuth();
        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId,
                                   ModelMap modelMap) {
        //查询已分配角色数据
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        //查询未分配角色数据
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
        //将数据封装到modelMap中
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_ASSIGNED_ROLE_LIST, assignedRoleList);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_UN_ASSIGNED_ROLE_LIST, unAssignedRoleList);
        return "assign-role";
    }

    @RequestMapping("assign/do/role/assign.html")
    public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("keyWord") String keyWord,
                                            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {
        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyWord=" + keyWord;
    }

    @ResponseBody
    @RequestMapping("assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(@RequestParam("roleId") Integer roleId) {
        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelathinship(@RequestBody Map<String, List<Integer>> map) {
        authService.saveRoleAuthRelationship(map);
        return ResultEntity.successWithOutData();
    }
}
