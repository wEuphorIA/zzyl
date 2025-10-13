package com.zzyl.nursing.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 产品信息响应模型
 *
 * @author itcast
 **/
@Data
@ApiModel("产品信息响应模型")
public class ProductVo {
    /**
     * 产品的ProductKey,物联网平台产品唯一标识
     */
    @ApiModelProperty("产品的ProductKey,物联网平台产品唯一标识")
    @JSONField(name = "product_id")
    private String productId;

    /**
     * 产品名称
     */
    @ApiModelProperty("产品名称")
    private String name;
}