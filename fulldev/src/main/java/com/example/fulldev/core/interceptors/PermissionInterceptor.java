package com.example.fulldev.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.example.fulldev.core.LocalUser;
import com.example.fulldev.exception.ForbiddenException;
import com.example.fulldev.exception.UnAuthenticatedException;
import com.example.fulldev.model.User;
import com.example.fulldev.service.UserService;
import com.example.fulldev.util.JwtToken;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    public PermissionInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevel = this.getScopeLevel(handler);
        if (!scopeLevel.isPresent()) {
            return true;
        }
        String bearerToken = request.getHeader("Authorization");
        if (StringUtil.isNullOrEmpty(bearerToken)) {
            throw new UnAuthenticatedException(10004);
        }
        if (!bearerToken.startsWith("Bearer")) {
            throw new UnAuthenticatedException(10004);
        }
        String tokens[] = bearerToken.split(" ");
        if (!(tokens.length == 2)) {
            throw new UnAuthenticatedException(10004);
        }
        String token = tokens[1];
        Optional<Map<String, Claim>> optionalMap = JwtToken.getClaims(token);
        Map<String, Claim> map = optionalMap.orElseThrow(() ->
                new UnAuthenticatedException(10004)
        );
        if (hasPermission(scopeLevel.get(), map)) {
            setThreadLocal(map);
        }
        return hasPermission(scopeLevel.get(), map);
    }

    private void setThreadLocal(Map<String, Claim> map) {
        Long uid = map.get("uid").asLong();
        Integer scope = map.get("scope").asInt();
        User user = userService.getUserById(uid);
        LocalUser.setUser(user, scope);
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set(threadLocal);
    }

    private boolean hasPermission(ScopeLevel scopeLevel, Map<String, Claim> map) {
        Integer level = scopeLevel.value();
        Integer scope = map.get("scope").asInt();
        if (level > scope) {
            throw new ForbiddenException(10005);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LocalUser.clear();
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    private Optional<ScopeLevel> getScopeLevel(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            if (null == scopeLevel) {
                return Optional.empty();
            }
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }
}
