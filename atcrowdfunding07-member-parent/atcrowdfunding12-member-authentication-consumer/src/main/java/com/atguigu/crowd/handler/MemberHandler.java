package com.atguigu.crowd.handler;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.api.RedisRemoteService;
import com.atguigu.crowd.config.ShortMessageConfig;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.vo.MemberLoginVO;
import com.atguigu.crowd.entity.vo.MemberVO;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberHandler {
    @Autowired
    private ShortMessageConfig shortMessageConfig;
    @Autowired
    private RedisRemoteService redisRemoteService;
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @ResponseBody
    @RequestMapping("auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
        //1.获取验证码发送结果
        ResultEntity<String> resultEntity = CrowdUtil.sendCodeByShortMessage(shortMessageConfig.getHost(), shortMessageConfig.getPath(), shortMessageConfig.getMethod(), shortMessageConfig.getAppCode(), phoneNum);
        //2.判断验证码是否发送成功
        if (resultEntity.getOperationResult().equals(ResultEntity.SUCCESS)) {
            //3.将验证码存储到redis中
            String code = resultEntity.getQueryData();
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(CrowdConstant.REDIS_CODE_PREFIX + code, code, 15, TimeUnit.MINUTES);
            if (saveCodeResultEntity.getOperationResult().equals(resultEntity)) {
                return ResultEntity.successWithOutData();
            } else {
                return saveCodeResultEntity;
            }
        } else {
            return resultEntity;
        }
    }

    @RequestMapping("auth/do/member/register.html")
    public String register(MemberVO memberVO, ModelMap modelMap) {
        //1.获取手机号
        String phoneNum = memberVO.getPhoneNum();
        //2.获取key值
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        //3.从redis中通过key获取value

        ResultEntity<String> redisResultEntity = redisRemoteService.getRedisStringValueByKeyRemote(key);
        if (redisResultEntity.getOperationResult().equals(ResultEntity.FAILED)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, redisResultEntity.getOperationMessage());
            return "member-reg";
        }

        if (ResultEntity.SUCCESS.equals(redisResultEntity.getOperationResult())) {
            String value = redisResultEntity.getQueryData();
            if (value == null) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
                return "member-reg";
            }
            String code = memberVO.getCode();
            //4.将value和code进行比较
            if (value != null) {
                if (value.equals(code)) {
                    //5.匹配成功 移除redis中的验证码
                    ResultEntity<String> removeResultEntity = redisRemoteService.removeRedisKeyRemote(key);
//                if (ResultEntity.SUCCESS.equals(removeResultEntity.getOperationResult())) {
                    //6.对密码进行加密
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String rowPassword = memberVO.getUserpswd();
                    String newPassword = passwordEncoder.encode(rowPassword);
                    memberVO.setUserpswd(newPassword);
                    //7.进行保存操作
                    MemberPO memberPO = new MemberPO();
                    BeanUtils.copyProperties(memberVO, memberPO);
                    ResultEntity<String> saveResultEntity = mySQLRemoteService.saveMember(memberPO);
                    if (saveResultEntity.getOperationResult().equals(ResultEntity.FAILED)) {
                        modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
                        return "member-reg";

                    }
//                }

                } else {
                    modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
                    return "member-reg";

                }

            }
        }
        return "redirect:http://localhost:80/auth/member/to/login/page.html";
    }

    @RequestMapping("auth/member/do/login.html")
    public String login(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd, ModelMap modelMap, HttpSession session) {
        ResultEntity<MemberPO> loginResultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginAcct);
        //判断方法是否调用成功
        if(userPswd.equals("")||userPswd==null||loginAcct.equals("")||loginAcct==null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_STRING_INVALIDATE);
            return "member-login";
        }
        if (loginResultEntity.getOperationResult().equals(ResultEntity.FAILED)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, loginResultEntity.getOperationMessage());
            return "member-login";
        }
        MemberPO memberPO = loginResultEntity.getQueryData();
        //判断登录账号是否存在
        if (memberPO == null) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        String dataBasePswd = memberPO.getUserpswd();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matchesResult = encoder.matches(userPswd, dataBasePswd);
        //判断密码是否正确
        if (!matchesResult) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,memberLoginVO);
        return "redirect:http://localhost:80/auth/member/to/center/page.html";
    }
    @RequestMapping("auth/member/logout.html")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:http://localhost:80/";
    }
}
