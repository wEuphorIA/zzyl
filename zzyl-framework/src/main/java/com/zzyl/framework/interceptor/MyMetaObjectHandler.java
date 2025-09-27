package com.zzyl.framework.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zzyl.common.core.domain.model.LoginUser;
import com.zzyl.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        // 自动填充创建人
        this.strictInsertFill(metaObject, "createBy", String.class, String.valueOf(getLoginUser()));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", String.valueOf(getLoginUser()), metaObject);
//        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
//        this.strictUpdateFill(metaObject, "updateBy", String.class, String.valueOf(getLoginUser()));
    }

    public Long getLoginUser() {
        LoginUser loginUser = SecurityUtils.getLoginUser();

        if (loginUser != null) {
            return loginUser.getUserId();
        }
        return 1L;
    }
}