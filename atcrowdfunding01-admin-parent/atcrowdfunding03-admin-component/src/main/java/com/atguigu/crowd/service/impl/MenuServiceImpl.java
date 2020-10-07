package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.Menu;
import com.atguigu.crowd.mapper.MenuMapper;
import com.atguigu.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> getAll() {
        return menuMapper.getAll();
    }

    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }
}
