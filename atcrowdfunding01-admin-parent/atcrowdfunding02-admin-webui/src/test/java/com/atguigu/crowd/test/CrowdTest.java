package com.atguigu.crowd.test;

import com.atguigu.crowd.Admin;
import com.atguigu.crowd.Role;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
//加载 Spring 配置文件的注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml","classpath:spring-web-mvc.xml"})
public class CrowdTest {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testBCryptPasswordEncoder(){
        System.out.println(passwordEncoder.encode("123123"));
        System.out.println(passwordEncoder.encode("123123"));
    }
    @Test
    public void insertRole() {
        for (int i = 1; i < 137; i++) {
            Role role = new Role(null, "role" + i);
            roleMapper.insert(role);
        }
    }

    @Test
    public void testLog() {
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.debug("debug lever");
        logger.info("info lever");
        logger.warn("warn lever");
        logger.error("error lever");
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin(null, "tom", "123", "汤姆", "tom@qq.com", null);
        int count = adminMapper.insert(admin);
        System.out.println(count);
    }

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "Je", "123", "杰", "Je@qq.com", null);
        adminService.saveAdmin(admin);

    }

    @Test
    public void testPageHelper() {
        int i = 335;
        Admin admin = new Admin(null, "tom" + i, "123", "tom" + i, "tom@qq.com" + i, null);
        adminMapper.insert(admin);
    }
}
