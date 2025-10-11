package com.zzyl.nursing.service;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/11 下午3:55 */
public interface WechatService {

    /**
     * 获取openid
     * @param code
     * @return
     */
    String getOpenid(String code);

    /**
     * 获取手机号
     * @param phoneCode
     * @return
     */
    String getPhone(String phoneCode);
}
