package com.wangchao.house.web.controller;

import com.wangchao.house.common.model.User;
import com.wangchao.house.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getUsers")
    public List<User> getUsers() {
        return userService.getUsers();
    }


}
