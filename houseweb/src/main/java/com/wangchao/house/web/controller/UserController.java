package com.wangchao.house.web.controller;

import com.wangchao.house.common.model.User;
import com.wangchao.house.biz.service.UserService;
import com.wangchao.house.common.result.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册提交:1 注册验证 2 发送邮件 3验证失败重定向到注册页面
     * 注册页获取: 根据account对象为依据判断是否注册页获取请求
     * @param account
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/register")
    public String accontsRegister(User account, ModelMap modelMap){
        if(account == null || account.getName() == null){
            return "/user/accounts/register";
        }
        //用户验证
        ResultMsg resultMsg=UserHelper.validate(account);
        if(resultMsg.isSuccess() && userService.addAccount(account)){
            return "/user/accounts/registerSubmit";
        }else{
            return "redirect:/accounts/register?"+resultMsg.asUrlParams();
        }
    }



}
