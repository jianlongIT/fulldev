package com.example.fulldev.core.configuration;

import com.example.fulldev.core.interceptors.PermissionInterceptor;
import com.example.fulldev.core.interceptors.RoolInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Autowired
    private RoolInterceptor roolInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(this.permissionInterceptor);
        //registry.addInterceptor(this.roolInterceptor);
    }
}
