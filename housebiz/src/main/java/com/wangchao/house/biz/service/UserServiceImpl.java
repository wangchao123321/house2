package com.wangchao.house.biz.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.wangchao.house.common.model.User;
import com.wangchao.house.biz.mapper.UserMapper;
import com.wangchao.house.common.util.BeanHelper;
import com.wangchao.house.common.util.HashUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{



    @Autowired
    private MailService mailService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Value("${file.prefix}")
    private String imgPrefix;


    @Override
    public List<User> getUsers() {
        return userMapper.selectUsers();
    }

    /**
     * 1 插入数据库,非激活;密码加盐md5;保存头像到本地
     * 2 生成key,绑定email
     * 3 发送邮件给用户
     * @param account
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
        List<String> imgList=fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
        if(!imgList.isEmpty()){
            account.setAvatar(imgList.get(0));
        }
        BeanHelper.setDefaultProp(account,User.class);
        BeanHelper.onInsert(account);
        account.setEnable(0);
        userMapper.insert(account);
        mailService.registerNotify(account.getEmail());
        return true;
    }

    @Override
    public boolean enable(String key) {
        return mailService.enable(key);
    }

    @Override
    public User auth(String username, String password) {
        User user=new User();
        user.setEmail(username);
        user.setPasswd(HashUtils.encryPassword(password));
        user.setEnable(1);
        List<User> users = selectUsersByQuery(user);
        if(!users.isEmpty()){
            return users.get(0);
        }
        return null;
    }

    private List<User> selectUsersByQuery(User user) {
        List<User> users = userMapper.selectUsersByQuery(user);
        users.forEach(u ->{
            u.setAvatar(imgPrefix+u.getAvatar());
        });
        return users;
    }


}
