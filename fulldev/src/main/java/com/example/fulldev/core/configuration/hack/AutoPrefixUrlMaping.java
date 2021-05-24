package com.example.fulldev.core.configuration.hack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class AutoPrefixUrlMaping extends RequestMappingHandlerMapping {
    @Value("${fulldev.api-package}")
    private String apiPackagePath;

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);

        if (null != mappingInfo) {
            return RequestMappingInfo.paths(getPrefix(handlerType)).build().combine(mappingInfo);
        }
        return mappingInfo;
    }

    private String getPrefix(Class<?> handlerType) {
        String packagename = handlerType.getPackage().getName();
        String DoPath = packagename.replaceAll(this.apiPackagePath, "");
        return DoPath.replace(".", "/");
    }
}
