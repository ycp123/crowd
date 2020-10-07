package com.atguigu.crowd.mvc.config;


import com.atguigu.crowd.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class SecurityAdmin extends User {
    private Admin originAdmin;
    public SecurityAdmin(Admin originAdmin, List<GrantedAuthority> authorities) {
        super(originAdmin.getLoginAcct(), originAdmin.getUserPswd(), authorities);
        this.originAdmin = originAdmin;
        this.originAdmin.setUserPswd(null);
    }

    public Admin getOriginAdmin() {
        return originAdmin;
    }
}
