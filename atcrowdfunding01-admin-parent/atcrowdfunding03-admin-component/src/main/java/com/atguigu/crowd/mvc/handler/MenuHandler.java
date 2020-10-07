package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.Menu;
import com.atguigu.crowd.service.api.MenuService;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuHandler {
    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTreeNew() {
        //1.获取全部Menu对象
        List<Menu> menuList = menuService.getAll();
        //2.初始化父节点
        Menu root = null;
        //3.将menu信息存储到Map中
        Map<Integer, Menu> menuMap = new HashMap<Integer, Menu>();
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            menuMap.put(id, menu);
        }
        //4.遍历寻找父节点和建立父子关系
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            //寻找父节点
            if (pid == null) {
                root = menu;
                continue;
            }
            //建立父子关系
            Menu parent = menuMap.get(pid);
            parent.getChildren().add(menu);
        }
        //5.返回结果
        return ResultEntity.successWithData(root);
    }

    @ResponseBody
    @RequestMapping("menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu) {
        menuService.saveMenu(menu);
        return ResultEntity.successWithOutData();
    }

    @ResponseBody
    @RequestMapping("menu/update.json")
    public ResultEntity<String> updateMenu(Menu menu) {
        menuService.updateMenu(menu);
        return ResultEntity.successWithOutData();
    }

    @ResponseBody
    @RequestMapping("menu/remove.json")
    public ResultEntity<String> removeMenu(@RequestParam("id") Integer id) {
        menuService.removeMenu(id);
        return ResultEntity.successWithOutData();
    }

}
