package com.zzyl.test;

import com.zzyl.nursing.service.WechatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WechatServiceImplTest {

    @Autowired
    private WechatService wechatService;

    @Test
    public void testGetOpenid() {
        String openid = wechatService.getOpenid("0a1DlRFa1ttGsK0EOHHa19LPpX3DlRFM");
        System.out.println(openid);//o3CsK6_C6f4WP9b0AxXNJOkc6q9Q
    }

    @Test
    public void testGetPhone() {
        String phone = wechatService.getPhone("99e378a8bc00a9651561b3d6d1dce208eb08da08d8e2e0f5b8dafc7b4f7a9e71");
        System.out.println(phone);
    }
}