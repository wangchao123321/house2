package com.wangchao;

import com.wangchao.house.biz.service.UserService;
import com.wangchao.house.biz.service.UserServiceImpl;
import com.wangchao.house.common.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        System.out.println("123");
        assertTrue( true );
    }

    @Autowired
    private UserService userService=new UserServiceImpl();

    @Test
    public void test(){
        User user = userService.auth("244067166@qq.com","123");
        System.out.println(user);
    }
}
