package com.zzyl.framework.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zzyl.common.core.domain.model.LoginUser;
import com.zzyl.common.utils.DateUtils;
import com.zzyl.common.utils.SecurityUtils;
import lombok.SneakyThrows;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private HttpServletRequest request;

    @SneakyThrows
    public boolean isExclude() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                String requestURI = request.getRequestURI();
                if(requestURI.startsWith("/member")) {
                    return true;
                }
            }
            return false;
        } catch (IllegalStateException e) {
            // 在非Web请求环境中返回false
            return false;
        }
    }

    // 插入数据时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        // this.strictInsertFill(metaObject, "createBy", String.class, String.valueOf(getLoginUserId()));
        // this.strictInsertFill(metaObject, "createTime", Date.class, DateUtils.getNowDate());
        if(!isExclude()) {
            this.strictInsertFill(metaObject, "createBy", String.class, getLoginUserId() + "");
            this.strictInsertFill(metaObject, "createTime", Date.class, DateUtils.getNowDate());
        }
    }

    // 修改数据时自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
//         this.setFieldValByName("updateBy", String.valueOf(getLoginUserId()), metaObject);
//         this.setFieldValByName("updateTime", DateUtils.getNowDate(), metaObject);
// //        this.strictUpdateFill(metaObject, "updateBy", String.class, String.valueOf(getLoginUserId()));
// //        this.strictUpdateFill(metaObject, "updateTime", Date.class, DateUtils.getNowDate());
        if(!isExclude()) {
            this.setFieldValByName("updateBy", getLoginUserId() + "", metaObject);
            this.strictUpdateFill(metaObject, "updateTime", Date.class, DateUtils.getNowDate());
        }
    }

    // 获取当前登录人的id
    public Long getLoginUserId() {
        //获取当前登录人的id
        try {
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if (ObjectUtils.isNotEmpty(loginUser)) {
                return loginUser.getUserId();
            }
            return 1L;
        } catch (Exception e) {
            return 1L;
        }
    }
}
