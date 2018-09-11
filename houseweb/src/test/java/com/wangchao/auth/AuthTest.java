package com.wangchao.auth;

import com.wangchao.house.biz.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTest {

    @Autowired
    private UserService userService;

    public void test(){

    }
}
