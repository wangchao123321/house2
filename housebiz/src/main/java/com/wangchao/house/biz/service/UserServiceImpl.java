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

    private final Cache<String,String> registerCache= CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterAccess(15, TimeUnit.MINUTES).removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    userMapper.delete(notification.getValue());
                }
            }).build();

    @Autowired
    private MailService mailService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Value("${domain.name}")
    private String domainName;

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
        registerNotify(account.getEmail());
        return false;
    }

    /**
     * 1 缓存key-email的关系
     * 2 借助spring mail 发送邮件
     * 3 借助异步框架进行异步操作
     * @param email
     */
    @Async
    public void registerNotify(String email) {
        String randomKey= RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey,email);
        String url="http://"+domainName+"/accounts/verify?key="+randomKey;
        mailService.sendMail("房产平台激活邮件",url,email);
    }
}
