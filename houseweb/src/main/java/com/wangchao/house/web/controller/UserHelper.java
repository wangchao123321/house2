package com.wangchao.house.web.controller;

import com.wangchao.house.common.model.User;
import com.wangchao.house.common.result.ResultMsg;
import org.springframework.util.StringUtils;

public class UserHelper {

    public static ResultMsg validate(User account) {
        if (StringUtils.isEmpty(account.getEmail())) {
            return ResultMsg.errorMsg("email 有误");
        }
        if (StringUtils.isEmpty(account.getName())) {
            return ResultMsg.errorMsg("名字有误");
        }
        if (StringUtils.isEmpty(account.getConfirmPasswd()) || StringUtils.isEmpty(account.getPasswd())
                || !account.getPasswd().equals(account.getConfirmPasswd())) {
            return ResultMsg.errorMsg("密码有误");
        }
        if (account.getPasswd().length()<6) {
            return ResultMsg.errorMsg("密码大于6位");
        }
        return ResultMsg.successMsg("");
    }
}
