package com.zzyl.common.ai;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.core.auth.Auth;
import com.baidubce.qianfan.model.chat.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AIModelInvoker {

    @Autowired
    private BaiduAIProperties baiduAIProperties;

    public String qianfanInvoker(String prompt) {
        System.out.println(prompt);
        Qianfan qianfan = new Qianfan(baiduAIProperties.getAccessKey(), baiduAIProperties.getSecretKey());
        ChatResponse response = qianfan.chatCompletion()
                .model(baiduAIProperties.getQianfanModel())
                .addMessage("user", prompt)
                .temperature(0.7)
                .maxOutputTokens(2000)
                .responseFormat("json_object")
                .execute();
        String result = response.getResult();

        return result;
    }

}