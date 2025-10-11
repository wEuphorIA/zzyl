package com.zzyl.framework.interceptor;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zzyl.common.exception.base.BaseException;
import com.zzyl.common.utils.StringUtils;
import com.zzyl.common.utils.UserThreadLocal;
import com.zzyl.framework.web.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MemberInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;

    public MemberInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果不是Controller层的请求，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取header中的token
        String token = request.getHeader("authorization");

        // 如果token为空，响应401，重新登录
        if (StringUtils.isEmpty(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new BaseException("认证失败");
        }

        // 解析token
        Claims claims = tokenService.parseToken(token);
        if (ObjectUtil.isEmpty(claims)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new BaseException("认证失败");
        }

        // 解析token中的数据
        Long userId = MapUtil.get(claims, "userId", Long.class);
        if (ObjectUtil.isEmpty(userId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new BaseException("认证失败");
        }

        // 将用户id放入ThreadLocal中
        UserThreadLocal.set(userId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}