package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.po.MemberPO;

public interface MemberService {
    public MemberPO getMemberPOByLoginAcct(String loginacct);

    void saveMember(MemberPO memberPO);
}
