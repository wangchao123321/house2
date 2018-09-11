package com.wangchao.house.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WebMvcConf extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthActionIntercepter authActionIntercepter;

    @Autowired
    private AuthIntercepter authIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authIntercepter).excludePathPatterns("/static").addPathPatterns("/**");
        registry.addInterceptor(authActionIntercepter).addPathPatterns("/accounts/profile");
        super.addInterceptors(registry);
    }
}
