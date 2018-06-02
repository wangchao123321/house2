package com.wangchao.house.service;

import com.wangchao.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {

    public List<User> getUsers();

}
