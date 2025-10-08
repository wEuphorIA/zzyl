package com.zzyl.common.ai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "baidu")
public class BaiduAIProperties {

    private String accessKey;
    private String secretKey;
    private String qianfanModel;

}