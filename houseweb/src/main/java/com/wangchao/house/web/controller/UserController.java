package com.wangchao.house.web.controller;

import com.wangchao.house.common.constants.CommonConstants;
import com.wangchao.house.common.model.User;
import com.wangchao.house.biz.service.UserService;
import com.wangchao.house.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
            modelMap.put("email",account.getEmail());
            return "/user/accounts/registerSubmit";
        }else{
            return "redirect:/accounts/register?"+resultMsg.asUrlParams();
        }
    }


    @RequestMapping("accounts/verify")
    public String verify(String key){
        boolean result=userService.enable(key);
        if(result){
            return "redirect:/index?"+ResultMsg.successMsg("激活成功").asUrlParams();
        }else{
            return "redirect:/accounts/register?"+ResultMsg.successMsg("激活失败,请确认连接是否过去").asUrlParams();
        }
    }


    /**
     * 登录接口
     * @param request
     * @return
     */
    @RequestMapping("/accounts/signin")
    public String signin(HttpServletRequest request){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String target=request.getParameter("target");
        if(username == null || password == null){
            request.setAttribute("target",target);
            return "/user/accounts/signin";
        }
        User user=userService.auth(username,password);
        if(user==null){
            return "redirect:/accounts/signin?"+target+"&username="+username+"&"+ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }else{
            HttpSession session = request.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE,user);
            session.setAttribute(CommonConstants.PLAN_USER_ATTRIBUTE,user);
            return StringUtils.isNoneBlank(target)?"redirect:"+target : "redirect:/index";
        }
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request){
        HttpSession session=request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }
}
