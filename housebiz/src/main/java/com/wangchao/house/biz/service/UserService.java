package com.wangchao.house.biz.service;

import com.wangchao.house.common.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    boolean addAccount(User account);

}
