package com.zzyl.framework.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zzyl.common.core.domain.model.LoginUser;
import com.zzyl.common.utils.DateUtils;
import com.zzyl.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 插入数据时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createBy", String.class, String.valueOf(getLoginUserId()));
        this.strictInsertFill(metaObject, "createTime", Date.class, DateUtils.getNowDate());
    }

    // 修改数据时自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateBy", String.valueOf(getLoginUserId()), metaObject);
        this.setFieldValByName("updateTime", DateUtils.getNowDate(), metaObject);
//        this.strictUpdateFill(metaObject, "updateBy", String.class, String.valueOf(getLoginUserId()));
//        this.strictUpdateFill(metaObject, "updateTime", Date.class, DateUtils.getNowDate());
    }

    // 获取当前登录人的id
    public Long getLoginUserId() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (ObjectUtils.isNotEmpty(loginUser)) {
            return loginUser.getUserId();
        }
        return 1L;
    }
}
