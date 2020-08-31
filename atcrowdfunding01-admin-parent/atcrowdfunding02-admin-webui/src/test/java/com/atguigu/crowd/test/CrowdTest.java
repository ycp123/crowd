package com.atguigu.crowd.test;

import com.atguigu.crowd.Admin;
import com.atguigu.crowd.mapper.AdminMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
//加载 Spring 配置文件的注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
    @Test
    public void testInsertAdmin(){
        Admin admin = new Admin(null,"tom","123","汤姆","tom@qq.com",null);
        int count = adminMapper.insert(admin);
        System.out.println(count);
    }
}
