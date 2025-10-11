package com.zzyl.nursing.service.impl;

import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zzyl.nursing.service.WechatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/11 下午4:35 */
@Service
public class WechatServiceImpl implements WechatService {

    // 登录
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    // 获取token
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    // 获取手机号
    private static final String PHONE_REQUEST_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";


    @Value("${wechat.appId}")
    private String appid;

    @Value("${wechat.appSecret}")
    private String secret;

    /**
     获取openid
     @param code
     @return
     */
    @Override
    public String getOpenid(String code) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("appid",appid);
        hashMap.put("secret",secret);
        hashMap.put("js_code",code);

        String json = HttpUtil.get(REQUEST_URL, hashMap);
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.getIntValue("errcode") != 0){
            throw new RuntimeException(jsonObject.getString("errmsg"));
        }

        return jsonObject.getString("openid");
    }

    /**
     获取手机号
     @param phoneCode
     @return
     */
    @Override
    public String getPhone(String phoneCode) {

        // 获取token
        String token = getToken();
        Map<String,Object> hashMap = new HashMap<>();
        hashMap.put("code",phoneCode);

        String json = HttpUtil.post(PHONE_REQUEST_URL + token, JSON.toJSONString(hashMap));
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.getIntValue("errcode") != 0){
            throw new RuntimeException(jsonObject.getString("errmsg"));
        }

        return jsonObject.getJSONObject("phone_info").getString("phoneNumber");
    }

    private String getToken() {

        Map<String,Object> hashMap = new HashMap<>();
        hashMap.put("appid",appid);
        hashMap.put("secret",secret);
        String json = HttpUtil.get(TOKEN_URL, hashMap);
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.getIntValue("errcode") != 0){
            throw new RuntimeException(jsonObject.getString("errmsg"));
        }

        return jsonObject.getString("access_token");
    }
}
